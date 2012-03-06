package com.bb.web;

import com.bb.domain.CustomerCheckin;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/customercheckins")
@Controller
@RooWebScaffold(path = "customercheckins", formBackingObject = CustomerCheckin.class)
public class CustomerCheckinController {
}
