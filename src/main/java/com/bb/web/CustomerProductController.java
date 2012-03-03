package com.bb.web;

import com.bb.domain.CustomerProduct;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/customerproducts")
@Controller
@RooWebScaffold(path = "customerproducts", formBackingObject = CustomerProduct.class)
public class CustomerProductController {
}
