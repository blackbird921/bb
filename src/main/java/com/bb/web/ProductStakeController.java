package com.bb.web;

import com.bb.domain.ProductStake;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/productstakes")
@Controller
@RooWebScaffold(path = "productstakes", formBackingObject = ProductStake.class)
public class ProductStakeController {
}
