package com.bb.web;

import com.bb.domain.ProductCommit;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/productcommits")
@Controller
@RooWebScaffold(path = "productcommits", formBackingObject = ProductCommit.class)
public class ProductCommitController {
}
