package com.bb.web;

import com.bb.domain.ref.RefPaymentTxType;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/refpaymenttxtypes")
@Controller
@RooWebScaffold(path = "refpaymenttxtypes", formBackingObject = RefPaymentTxType.class)
public class RefPaymentTxTypeController {
}
