package com.bb.web;

import com.bb.domain.Customer;
import com.bb.service.AvatarService;
import com.bb.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RequestMapping( "/customers" )
@Controller
@RooWebScaffold( path = "customers", formBackingObject = Customer.class )
public class CustomerController {

    @Autowired
    private CommonsMultipartResolver multipartResolver;
    @Autowired
    private ValidationService validationService;

    @Autowired
    private AvatarService avatarService;


    @RequestMapping( method = RequestMethod.POST, produces = "text/html" )
    public String create( @Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest ) {
        boolean hasError = false;
        if ( bindingResult.hasErrors() ) {
            hasError = true;
        }
        if ( validationService.existsUniqueValue( Customer.class, "username", customer.getUsername() ) ) {
            uiModel.addAttribute( "usernameUniqueError", Boolean.TRUE );
            hasError = true;
        }
        if ( validationService.existsUniqueValue( Customer.class, "email", customer.getEmail() ) ) {
            uiModel.addAttribute( "emailUniqueError", Boolean.TRUE );
            hasError = true;
        }
        if ( hasError ) {
            populateEditForm( uiModel, customer );
            return "customers/create";
        }

        uiModel.asMap().clear();
        customer.persist();
        avatarService.uploadAvatar(customer, httpServletRequest);
        customer.merge();
        return "redirect:/customers/" + encodeUrlPathSegment( customer.getId().toString(), httpServletRequest );
    }

    @RequestMapping(value = "/updateAvatarForm/{id}", method = RequestMethod.GET, produces = "text/html")
    public String updateAvatarForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Customer.findCustomer(id));
        System.out.println("its here.................................");
        return "customers/updateAvatarForm";
    }


    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            for (ObjectError e : bindingResult.getAllErrors()) {
                System.out.println(e.toString());
            }

            populateEditForm(uiModel, customer);
            return "customers/update";
        }
        uiModel.asMap().clear();
        System.out.println(customer.getHasAvatar());
        avatarService.uploadAvatar(customer, httpServletRequest);
        customer.merge();
        return "redirect:/customers/" + encodeUrlPathSegment(customer.getId().toString(), httpServletRequest);
    }

}
