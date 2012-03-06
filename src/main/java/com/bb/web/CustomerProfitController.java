package com.bb.web;

import com.bb.domain.CustomerProfit;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/customerprofits")
@Controller
@RooWebScaffold(path = "customerprofits", formBackingObject = CustomerProfit.class)
public class CustomerProfitController {
}
