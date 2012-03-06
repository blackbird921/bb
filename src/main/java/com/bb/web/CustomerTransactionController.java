package com.bb.web;

import com.bb.domain.CustomerTransaction;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/customertransactions")
@Controller
@RooWebScaffold(path = "customertransactions", formBackingObject = CustomerTransaction.class)
public class CustomerTransactionController {
}
