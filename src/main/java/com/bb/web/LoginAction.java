package com.bb.web;

import com.bb.domain.Customer;
import com.bb.reference.CustomerStatus;
import com.bb.service.LoginService;
import com.bb.service.MailService;
import com.bb.util.AutowiredLogger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class LoginAction {
    @AutowiredLogger
    private Logger logger;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MailService mailService;


    @RequestMapping(method = RequestMethod.GET)
    public String loginForm( Model uiModel, HttpServletRequest request) {
        this.logger.info("loginForm...........");


        return "login";
    }


}
