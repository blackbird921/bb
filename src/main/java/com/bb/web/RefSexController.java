package com.bb.web;

import com.bb.domain.ref.RefSex;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/refsexes")
@Controller
@RooWebScaffold(path = "refsexes", formBackingObject = RefSex.class)
public class RefSexController {
}
