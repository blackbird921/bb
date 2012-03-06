package com.bb.web;

import com.bb.domain.CustomerCard;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/customercards")
@Controller
@RooWebScaffold(path = "customercards", formBackingObject = CustomerCard.class)
public class CustomerCardController {
}
