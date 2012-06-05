package com.bb.web;

import com.bb.domain.*;
import com.bb.reference.WeekStatus;
import com.bb.service.*;
import com.bb.util.AutowiredLogger;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequestMapping("/customerproducts")
@Controller
@RooWebScaffold(path = "customerproducts", formBackingObject = CustomerProduct.class)
public class CustomerProductController {

    @AutowiredLogger
    Logger logger;
    @Autowired
    private CustomerCheckinService customerCheckinService;
    @Autowired
    private CustomerProductService customerProductService;
    @Autowired
    private CustomerProfitService customerProfitService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CustomerProduct futurecustomerproduct, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        logger.info("create.....");
        System.out.println(futurecustomerproduct);
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, futurecustomerproduct);
            return "customerproducts/create";
        }
        uiModel.asMap().clear();
        futurecustomerproduct.persist();
        String redirect = "redirect:/customercards/create";
        logger.info("{}", redirect);
        return redirect;
    }

    @RequestMapping(value = "/create", produces = "text/html")
    public String createForm(Model uiModel) {
        logger.info("createForm.......");
        CustomerProduct cp = new CustomerProduct();
        Long id = loginService.getCustomerId();
        cp.setCustomer(Customer.findCustomer(id));
        uiModel.addAttribute("futurecustomerproduct", cp);
        logger.info("{}", cp);
        populateEditForm(uiModel, cp);
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Customer.countCustomers() == 0) {
            dependencies.add(new String[]{"customer", "customers"});
        }
        if (ProductCommit.countProductCommits() == 0) {
            dependencies.add(new String[]{"productcommit", "productcommits"});
        }
        if (ProductStake.countProductStakes() == 0) {
            dependencies.add(new String[]{"productstake", "productstakes"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "customerproducts/create";
    }

    @RequestMapping(value = "/show", produces = "text/html")
    public String show( Model uiModel) {
        Long id = loginService.getCustomerId();
        return show(id, uiModel);
    }
    public String show(Long id, Model uiModel) {
        logger.info("show .....");
        addDateTimeFormatPatterns(uiModel);
        
        CustomerProduct cp = customerProductService.getCurrentProduct(id);
        WeekStatus weekStatus = customerCheckinService.getCurrentWeekStatus(id);
        if (cp != null) {
            weekStatus.setDaysToComplete(cp.getProductCommit().getCommits().intValue() - weekStatus.getDaysCompleted());
            Customer customer = Customer.findCustomer(id);
            cp.setCustomer(customer);
            logger.info("{}", cp.getCustomer().getDisableStartDate());
            logger.info("{}", cp.getCustomer().getDisableEndDate());
            logger.info("{}", Calendar.getInstance());
            if ((cp.getCustomer().getDisableStartDate() != null && cp.getCustomer().getDisableStartDate().before(Calendar.getInstance().getTime()))
                    && (cp.getCustomer().getDisableEndDate() == null || cp.getCustomer().getDisableEndDate().after((Calendar.getInstance().getTime())))) {
                logger.info("is on vacation...");
                uiModel.addAttribute("isOnVacation", true);
            }
            uiModel.addAttribute("customerproduct", cp);
            uiModel.addAttribute("weekstatus", weekStatus);
            uiModel.addAttribute("customerreport", reportService.getCustomerStats(id));
        }else {
            cp = customerProductService.getFutureProduct(id);
            if (cp != null) {
                uiModel.addAttribute("newUserStartDate", cp.getStartDate());
            }else {
                uiModel.addAttribute("isOnVacation", true);
            }
        }

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
    public String update(@Valid CustomerProduct futurecustomerproduct, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println(futurecustomerproduct);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            populateEditForm(uiModel, futurecustomerproduct);
            return "customerproducts/update";
        }
        uiModel.asMap().clear();
        customerProductService.updateFutureProduct(futurecustomerproduct);
        return "redirect:/customerproducts/" + encodeUrlPathSegment(futurecustomerproduct.getCustomer().getId().toString(), httpServletRequest);
    }

    @RequestMapping( params = "form", produces = "text/html")
    public String updateForm( Model uiModel) {
        System.out.println("updateForm.......");
        Long id = loginService.getCustomerId();
        CustomerProduct cp = customerProductService.getCurrentProduct(id);
        if (cp == null) {
            cp = customerProductService.getFutureProduct(id);
            if (cp == null) {
                return "redirect:/customerproducts/create";
            }
        }
        populateEditForm(uiModel, cp);
        uiModel.addAttribute("futurecustomerproduct", customerProductService.getFutureProduct(id));
        return "customerproducts/update";
    }

    @RequestMapping(value = "/hist", method = RequestMethod.GET, produces = "text/html")
    public String history( @RequestParam(required = false) Long week, Model uiModel) {
        Long id = loginService.getCustomerId();
        return history(id, week, uiModel);
    }
    public String history(Long id, Long week, Model uiModel) {
        System.out.println("history.......");

        uiModel.addAttribute("customerproducts", CustomerProduct.findAllByCustomerId(id));
        List<CustomerCheckin> checkins = CustomerCheckin.findCustomerCheckinsByCustomer(id).getResultList();
        uiModel.addAttribute("customercheckins", checkins);

        List<WeekStatus> allWeekStatus = new ArrayList<WeekStatus>();
        int x = 52;
        if (week != null) {
            x = week.intValue();
        }
        for (int i = 1; i <= x; i++) {
            CustomerProduct cp = customerProductService.getPastProduct(id, i);
            if (cp != null) {
                CustomerProfit cProfit = customerProfitService.getPastProfit(id, i);
                WeekStatus weekStatus = customerCheckinService.getPastWeekStatus(id, i);
                weekStatus.setCustomerProduct(cp);
                weekStatus.setCustomerProfit(cProfit);
                allWeekStatus.add(weekStatus);
            }
        }
        uiModel.addAttribute("allWeekStatus", allWeekStatus);

        return "customerproducts/history";
    }


    @RequestMapping(value = "/card", method = RequestMethod.GET, produces = "text/html")
    public String card( Model uiModel) {
        Long id = loginService.getCustomerId();
        return card(id, uiModel);
    }
    public String card(Long id, Model uiModel) {
        System.out.println("card.......");
        

        //TODO: delete line
        uiModel.addAttribute("customerproducts", CustomerProduct.findAllByCustomerId(id));
        uiModel.addAttribute("customerId", id);
        uiModel.addAttribute("issuedDate", new Date());

        List<CustomerCard> customerCards = CustomerCard.findAllByCustomerId(id);
        uiModel.addAttribute("customercards", customerCards);

        List<Card> cards = Card.findAllCards();
        List<Card> cardsToAdd = new ArrayList<Card>();
        cardsToAdd.addAll(cards);
        System.out.println(cardsToAdd);

        for (Card card : cards) {
            for (CustomerCard cc : customerCards) {
                System.out.println(cc.getCard().getId() + " " + card.getId());
                if (cc.getCard().getId().equals(card.getId())) {
                    cardsToAdd.remove(card);
                }
            }
        }

        System.out.println(cardsToAdd);
        uiModel.addAttribute("cards", cardsToAdd);

        return "customerproducts/card";
    }


    @RequestMapping( method = RequestMethod.DELETE, produces = "text/html")
    public String delete( @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Long id = loginService.getCustomerId();
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
        uiModel.addAttribute("customerproduct", customerProduct);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("customer", customerProduct.getCustomer());
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
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
}
