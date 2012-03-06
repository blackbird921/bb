// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.web;

import com.bb.domain.Customer;
import com.bb.domain.CustomerProfit;
import com.bb.web.CustomerProfitController;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect CustomerProfitController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String CustomerProfitController.create(@Valid CustomerProfit customerProfit, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customerProfit);
            return "customerprofits/create";
        }
        uiModel.asMap().clear();
        customerProfit.persist();
        return "redirect:/customerprofits/" + encodeUrlPathSegment(customerProfit.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String CustomerProfitController.createForm(Model uiModel) {
        populateEditForm(uiModel, new CustomerProfit());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Customer.countCustomers() == 0) {
            dependencies.add(new String[] { "customer", "customers" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "customerprofits/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String CustomerProfitController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customerprofit", CustomerProfit.findCustomerProfit(id));
        uiModel.addAttribute("itemId", id);
        return "customerprofits/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String CustomerProfitController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("customerprofits", CustomerProfit.findCustomerProfitEntries(firstResult, sizeNo));
            float nrOfPages = (float) CustomerProfit.countCustomerProfits() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("customerprofits", CustomerProfit.findAllCustomerProfits());
        }
        addDateTimeFormatPatterns(uiModel);
        return "customerprofits/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String CustomerProfitController.update(@Valid CustomerProfit customerProfit, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customerProfit);
            return "customerprofits/update";
        }
        uiModel.asMap().clear();
        customerProfit.merge();
        return "redirect:/customerprofits/" + encodeUrlPathSegment(customerProfit.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String CustomerProfitController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, CustomerProfit.findCustomerProfit(id));
        return "customerprofits/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String CustomerProfitController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CustomerProfit customerProfit = CustomerProfit.findCustomerProfit(id);
        customerProfit.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/customerprofits";
    }
    
    void CustomerProfitController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("customerProfit_startdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("customerProfit_enddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void CustomerProfitController.populateEditForm(Model uiModel, CustomerProfit customerProfit) {
        uiModel.addAttribute("customerProfit", customerProfit);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customers", Customer.findAllCustomers());
    }
    
    String CustomerProfitController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
