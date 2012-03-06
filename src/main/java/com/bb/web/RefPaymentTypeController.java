package com.bb.web;

import com.bb.domain.ref.RefPaymentType;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/refpaymenttypes")
@Controller
@RooWebScaffold(path = "refpaymenttypes", formBackingObject = RefPaymentType.class)
public class RefPaymentTypeController {
}
