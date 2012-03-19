package com.bb.web;

import com.bb.domain.Customer;
import com.bb.domain.ref.RefSex;
import com.bb.reference.CustomerRole;
import com.bb.reference.CustomerStatus;
import com.bb.service.AvatarService;
import com.bb.service.ValidationService;
import com.bb.util.AutowiredLogger;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;

@RequestMapping("/customers")
@Controller
@RooWebScaffold(path = "customers", formBackingObject = Customer.class)
public class CustomerController {

    @AutowiredLogger
    private Logger logger;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AvatarService avatarService;


    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("create...");
        customer.setStatus(CustomerStatus.Trial);
        customer.setRegistrationDate(Calendar.getInstance().getTime());

        boolean hasError = doValidations(customer, bindingResult, uiModel);
        if (hasError) {
            logger.info("hasError......{}", bindingResult);
            populateEditForm(uiModel, customer);
            return "customers/create";
        }

        uiModel.asMap().clear();
        customer.persist();
        logger.info("customer save:{}", customer);
//        avatarService.uploadAvatar(customer, httpServletRequest.getSession().getServletContext().getRealPath("/images/upload"));
//        customer.merge();
        String redirect = "redirect:/customerproducts/" + customer.getId().toString() + "/create";
        logger.info("{}", redirect);
        return redirect;
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

        avatarService.uploadAvatar(customer, httpServletRequest.getSession().getServletContext().getRealPath("/images/upload"));
        customer.merge();
        return "redirect:/customers/" + encodeUrlPathSegment(customer.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Customer());
        System.out.println("it's ok here");
        return "customers/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customer", Customer.findCustomer(id));
        uiModel.addAttribute("itemId", id);
        return "customers/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("customers", Customer.findCustomerEntries(firstResult, sizeNo));
            float nrOfPages = (float) Customer.countCustomers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("customers", Customer.findAllCustomers());
        }
        addDateTimeFormatPatterns(uiModel);
        return "customers/list";
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Customer.findCustomer(id));
        return "customers/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Customer customer = Customer.findCustomer(id);
        customer.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/customers";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("customer_registrationdate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("customer_birthday_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("customer_disablestartdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("customer_disableenddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, Customer customer) {
        uiModel.addAttribute("customer", customer);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("refsexes", RefSex.findAllRefSexes());
        uiModel.addAttribute("customerroles", Arrays.asList(CustomerRole.values()));
        uiModel.addAttribute("customerstatuses", Arrays.asList(CustomerStatus.values()));
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }


}
