package com.bb.web;

import com.bb.domain.Customer;
import com.bb.domain.CustomerProduct;
import com.bb.domain.ProductCommit;
import com.bb.domain.ProductStake;
import com.bb.reference.WeekStatus;
import com.bb.service.CustomerCheckinService;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
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

@RequestMapping("/customerproducts")
@Controller
@RooWebScaffold(path = "customerproducts", formBackingObject = CustomerProduct.class)
public class CustomerProductController {

    @Autowired
    private CustomerCheckinService customerCheckinService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CustomerProduct customerProduct, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customerProduct);
            return "customerproducts/create";
        }
        uiModel.asMap().clear();
        customerProduct.persist();
        return "redirect:/customerproducts/" + encodeUrlPathSegment(customerProduct.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new CustomerProduct());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Customer.countCustomers() == 0) {
            dependencies.add(new String[] { "customer", "customers" });
        }
        if (ProductCommit.countProductCommits() == 0) {
            dependencies.add(new String[] { "productcommit", "productcommits" });
        }
        if (ProductStake.countProductStakes() == 0) {
            dependencies.add(new String[] { "productstake", "productstakes" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "customerproducts/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        CustomerProduct cp = CustomerProduct.findCustomerProduct(id);
        WeekStatus weekStatus = customerCheckinService.getCurrentWeekStatus(id);
        if (cp != null) {
            weekStatus.setDaysToComplete(cp.getProductCommit().getCommits().intValue() - weekStatus.getDaysCompleted());
        }
        uiModel.addAttribute("customerproduct", cp);
        uiModel.addAttribute("weekstatus", weekStatus);
        uiModel.addAttribute("itemId", id);
        return "customerproducts/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("customerproducts", CustomerProduct.findCustomerProductEntries(firstResult, sizeNo));
            float nrOfPages = (float) CustomerProduct.countCustomerProducts() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("customerproducts", CustomerProduct.findAllCustomerProducts());
        }
        addDateTimeFormatPatterns(uiModel);
        return "customerproducts/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CustomerProduct customerProduct, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customerProduct);
            return "customerproducts/update";
        }
        uiModel.asMap().clear();
        customerProduct.merge();
        return "redirect:/customerproducts/" + encodeUrlPathSegment(customerProduct.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, CustomerProduct.findCustomerProduct(id));
        return "customerproducts/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CustomerProduct customerProduct = CustomerProduct.findCustomerProduct(id);
        customerProduct.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/customerproducts";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("customerProduct_startdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("customerProduct_enddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, CustomerProduct customerProduct) {
        uiModel.addAttribute("customerProduct", customerProduct);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customers", Customer.findAllCustomers());
        uiModel.addAttribute("productcommits", ProductCommit.findAllProductCommits());
        uiModel.addAttribute("productstakes", ProductStake.findAllProductStakes());
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
