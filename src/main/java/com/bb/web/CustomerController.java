package com.bb.web;

import com.bb.domain.Customer;
import com.bb.domain.ref.RefSex;
import com.bb.reference.AccountInfo;
import com.bb.reference.CustomerLogin;
import com.bb.reference.CustomerRole;
import com.bb.reference.CustomerStatus;
import com.bb.service.AvatarService;
import com.bb.service.CustomerAccountService;
import com.bb.service.LoginService;
import com.bb.service.ValidationService;
import com.bb.util.AutowiredLogger;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private LoginService loginService;


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
            logger.info("usernameUniqueError");
            hasError = true;
        }
        if (validationService.existsUniqueValue(Customer.class, "email", customer.getEmail(), customer.getId())) {
            uiModel.addAttribute("emailUniqueError", Boolean.TRUE);
            logger.info("emailUniqueError");
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
        logger.info("disableStartDate:{}", customer.getDisableStartDate());
        avatarService.uploadAvatar(customer, httpServletRequest.getSession().getServletContext().getRealPath("/images/upload"));
        customer.merge();
        return show(uiModel);
    }

    @RequestMapping(params = "create", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Customer());
        System.out.println("it's ok here");
        return "customers/create";
    }

    @RequestMapping(value = "/show", produces = "text/html")
    public String show(Model uiModel) {

        logger.info("show.....");
        Long id = loginService.getCustomerId();
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customer", Customer.findCustomer(id));
        return "customers/show";
    }

    @RequestMapping(value = "/account", produces = "text/html")
    public String account(Model uiModel) {
        logger.info("account.....");
        addDateTimeFormatPatterns(uiModel);
        Long id = loginService.getCustomerId();
        AccountInfo accountInfo = customerAccountService.getAccountInfo(id);
        uiModel.addAttribute("accountInfo", accountInfo);
        return "customers/account";
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

    @RequestMapping( params = "form", produces = "text/html")
    public String updateForm(Model uiModel) {
        logger.info("update form...");
        Long id = loginService.getCustomerId();
        populateEditForm(uiModel, Customer.findCustomer(id));
        return "customers/update";
    }

    @RequestMapping( params = "vacationform", produces = "text/html")
    public String updateFormVacation( Model uiModel) {
        logger.info("update updateFormVacation...");
        Long id = loginService.getCustomerId();
        populateEditForm(uiModel, Customer.findCustomer(id));
        return "customers/updatevacation";
    }


    @RequestMapping(method = RequestMethod.DELETE, produces = "text/html")
    public String delete( @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Long id = loginService.getCustomerId();
        Customer customer = Customer.findCustomer(id);
        customer.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/customers";
    }


    @RequestMapping(value = "/avatarUpload", method = RequestMethod.POST, produces = "text/html")
    public String avatarUpload(Customer customer, Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("avatarUpload..........");
        logger.info("" + customer.getId());
        logger.info("" + customer.getAvatar().getSize());
        Customer foundCustomer = Customer.findCustomer(customer.getId());
        foundCustomer.setAvatar(customer.getAvatar());

        uiModel.asMap().clear();
        avatarService.uploadAvatar(foundCustomer, httpServletRequest.getSession().getServletContext().getRealPath("/images/upload"));
        foundCustomer.merge();
        uiModel.addAttribute("avatarCut", true);
        return show(uiModel);
    }

    @RequestMapping(value = "/avatarSave", method = RequestMethod.POST, produces = "text/html")
    public String avatarSave(Customer customer, Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("avatarSave..........");
        logger.info("" + customer.getId());
        System.out.println("x: " + httpServletRequest.getParameter("x") + "," + httpServletRequest.getParameter("y") + ","
                + httpServletRequest.getParameter("w") + "," + httpServletRequest.getParameter("h"));

        Integer x = Double.valueOf(httpServletRequest.getParameter("x")).intValue();
        Integer y = Double.valueOf(httpServletRequest.getParameter("y")).intValue();
        Integer w = Double.valueOf(httpServletRequest.getParameter("w")).intValue();
        Integer h = Double.valueOf(httpServletRequest.getParameter("h")).intValue();

        avatarService.cropAvatar(customer, httpServletRequest.getSession().getServletContext().getRealPath("/images/upload"), x, y, w, h);

        uiModel.asMap().clear();
        uiModel.addAttribute("avatarCut", false);

        return show(uiModel);
    }


    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("customer_registrationdate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("customer_birthdVacationay_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
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
