package com.bb.web;

import com.bb.domain.Customer;
import com.bb.service.AvatarService;
import com.bb.service.ValidationService;
import com.bb.util.AutowiredLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    
    @AutowiredLogger
    Logger logger;

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


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "text/html")
    public String update(@Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        boolean hasError = doValidations(customer, bindingResult, uiModel);

        if (hasError) {
            for (ObjectError e : bindingResult.getAllErrors()) {
                logger.info(e.toString());
            }
            populateEditForm(uiModel, customer);
            return "customers/update";
        }

        uiModel.asMap().clear();
        
        avatarService.uploadAvatar(customer, httpServletRequest);
        customer.merge();
        return "redirect:/customers/" + encodeUrlPathSegment(customer.getId().toString(), httpServletRequest);
    }

}
