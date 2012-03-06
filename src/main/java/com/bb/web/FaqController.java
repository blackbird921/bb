package com.bb.web;

import com.bb.domain.Faq;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/faqs")
@Controller
@RooWebScaffold(path = "faqs", formBackingObject = Faq.class)
public class FaqController {
}
