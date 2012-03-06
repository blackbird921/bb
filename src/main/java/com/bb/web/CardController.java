package com.bb.web;

import com.bb.domain.Card;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/cards")
@Controller
@RooWebScaffold(path = "cards", formBackingObject = Card.class)
public class CardController {
}
