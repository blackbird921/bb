package com.bb.web;

import com.bb.domain.Customer;
import com.bb.service.AvatarService;
import com.bb.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/customers")
@Controller
@RooWebScaffold(path = "customers", formBackingObject = Customer.class)
public class CustomerController {

    @Autowired
    private CommonsMultipartResolver multipartResolver;
    @Autowired
    private ValidationService validationService;

    @Autowired
    private AvatarService avatarService;


    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {

        boolean hasError = doValidations(customer, bindingResult, uiModel);

        if (hasError) {
            populateEditForm(uiModel, customer);
            return "customers/create";
        }

        uiModel.asMap().clear();
        customer.persist();
        avatarService.uploadAvatar(customer, httpServletRequest);
        customer.merge();
        return "redirect:/customers/" + encodeUrlPathSegment(customer.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST, produces = "text/html")
    public String updateAvatar(Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {

        uiModel.asMap().clear();
        CommonsMultipartFile avatar = customer.getAvatar();
        customer = Customer.findCustomer(customer.getId());
        customer.setAvatar(avatar);

        avatarService.uploadAvatar(customer, httpServletRequest);
        customer.merge();
        System.out.println("updateAvatarResult to return");
        uiModel.addAttribute("customer", customer);
        return "customers/updateAvatarForm";
    }


    private boolean doValidations(Customer customer, BindingResult bindingResult, Model uiModel) {
        boolean hasError = false;
        if (bindingResult.hasErrors()) {
            hasError = true;
        }

        if (validationService.existsUniqueValue(Customer.class, "username", customer.getUsername(), customer.getId())) {
            uiModel.addAttribute("usernameUniqueError", Boolean.TRUE);
            hasError = true;
        }
        if (validationService.existsUniqueValue(Customer.class, "email", customer.getEmail(), customer.getId())) {
            uiModel.addAttribute("emailUniqueError", Boolean.TRUE);
            hasError = true;
        }
        return hasError;
    }

    @RequestMapping(value = "/updateAvatarForm/{id}", method = RequestMethod.GET, produces = "text/html")
    public String updateAvatarForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Customer.findCustomer(id));
        System.out.println("its here.................................");
        return "customers/updateAvatarForm";
    }


    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {

        boolean hasError = doValidations(customer, bindingResult, uiModel);

        if (hasError) {
            System.out.println(111111);
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
