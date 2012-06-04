package com.bb.web;

import com.bb.domain.Customer;
import com.bb.domain.CustomerPayment;
import com.bb.domain.ref.RefPaymentType;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/customerpayments")
@Controller
@RooWebScaffold(path = "customerpayments", formBackingObject = CustomerPayment.class)
public class CustomerPaymentController {
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CustomerPayment customerPayment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customerPayment);
            return "customerpayments/create";
        }
        uiModel.asMap().clear();
        customerPayment.persist();
        return "redirect:/customerpayments/" + encodeUrlPathSegment(customerPayment.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new CustomerPayment());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Customer.countCustomers() == 0) {
            dependencies.add(new String[] { "customer", "customers" });
        }
        if (RefPaymentType.countRefPaymentTypes() == 0) {
            dependencies.add(new String[] { "refpaymenttype", "refpaymenttypes" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "customerpayments/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customerpayment", CustomerPayment.findCustomerPayment(id));
        uiModel.addAttribute("itemId", id);
        return "customerpayments/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("customerpayments", CustomerPayment.findCustomerPaymentEntries(firstResult, sizeNo));
            float nrOfPages = (float) CustomerPayment.countCustomerPayments() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("customerpayments", CustomerPayment.findAllCustomerPayments());
        }
        addDateTimeFormatPatterns(uiModel);
        return "customerpayments/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CustomerPayment customerPayment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customerPayment);
            return "customerpayments/update";
        }
        uiModel.asMap().clear();
        customerPayment.merge();
        return "redirect:/customerpayments/" + encodeUrlPathSegment(customerPayment.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, CustomerPayment.findCustomerPayment(id));
        return "customerpayments/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CustomerPayment customerPayment = CustomerPayment.findCustomerPayment(id);
        customerPayment.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/customerpayments";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("customerPayment_startdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("customerPayment_enddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, CustomerPayment customerPayment) {
        uiModel.addAttribute("customerPayment", customerPayment);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customers", Customer.findAllCustomers());
        uiModel.addAttribute("refpaymenttypes", RefPaymentType.findAllRefPaymentTypes());
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

}
