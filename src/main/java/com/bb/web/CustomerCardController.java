package com.bb.web;

import com.bb.domain.Card;
import com.bb.domain.Customer;
import com.bb.domain.CustomerCard;
import com.bb.domain.CustomerProduct;
import com.bb.util.AutowiredLogger;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
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

@RequestMapping("/customercards")
@Controller
@RooWebScaffold(path = "customercards", formBackingObject = CustomerCard.class)
public class CustomerCardController {
    @AutowiredLogger
    Logger logger;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CustomerCard customerCard, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("create.....");
        if (bindingResult.hasErrors()) {
            logger.error("{}", bindingResult);
            populateEditForm(uiModel, customerCard);
            return "customercards/create";
        }
        uiModel.asMap().clear();
        customerCard.persist();
        logger.info("persisted:{}", customerCard);
        return "redirect:/customerproducts/" + customerCard.getCustomer().getId().toString();
    }

    @RequestMapping(value = "/{id}/create", produces = "text/html")
    public String createForm(@PathVariable("id") Long id, Model uiModel) {
        logger.info("createForm.......");
        CustomerCard cp = new CustomerCard();
        cp.setCustomer(Customer.findCustomer(id));
        logger.info("{}", cp.getCustomer());
        populateEditForm(uiModel, cp);
        uiModel.addAttribute("description", Card.findCard(1L).getDescription());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Customer.countCustomers() == 0) {
            dependencies.add(new String[]{"customer", "customers"});
        }
        if (Card.countCards() == 0) {
            dependencies.add(new String[]{"card", "cards"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "customercards/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customercard", CustomerCard.findCustomerCard(id));
        uiModel.addAttribute("itemId", id);
        return "customercards/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("customercards", CustomerCard.findCustomerCardEntries(firstResult, sizeNo));
            float nrOfPages = (float) CustomerCard.countCustomerCards() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("customercards", CustomerCard.findAllCustomerCards());
        }
        addDateTimeFormatPatterns(uiModel);
        return "customercards/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CustomerCard customerCard, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customerCard);
            return "customercards/update";
        }
        uiModel.asMap().clear();
        customerCard.merge();
        return "redirect:/customercards/" + encodeUrlPathSegment(customerCard.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, CustomerCard.findCustomerCard(id));
        return "customercards/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CustomerCard customerCard = CustomerCard.findCustomerCard(id);
        customerCard.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/customercards";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("customerCard_useddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, CustomerCard customerCard) {
        uiModel.addAttribute("customerCard", customerCard);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("cards", Card.findAllCards());
        uiModel.addAttribute("customer", customerCard.getCustomer());
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
