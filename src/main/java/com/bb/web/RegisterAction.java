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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RegisterAction {
    @AutowiredLogger
    private Logger logger;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/verifyRegistration", method = RequestMethod.POST)
    public ModelAndView verifyRegistration(HttpServletRequest request) {
        this.logger.info("verfiy registration");

        ModelAndView view = new ModelAndView();

        view.addObject("email", request.getParameter("email"));
        view.addObject("password", request.getParameter("password"));

        return view;
    }

    @RequestMapping(value = "/showClause", method = RequestMethod.GET)
    public ModelAndView showClause(HttpServletRequest request) {
        this.logger.info("display clause of registration ");

        ModelAndView view = new ModelAndView();
        return view;
    }

    @RequestMapping(value = "/validateEmail", method = RequestMethod.POST)
    public void validateEmailIsRegistered(HttpServletRequest request, HttpServletResponse response) {
        this.logger.info("validate email registerd start ");

//        String json = commonUtil.validateEmailIsRegistered(request);
        String json = "true";

        this.logger.info("validate email with return Json : " + json);

        try {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(json);

        } catch (IOException e) {

            this.logger.error("Error submitRegistration ", e);
        }
    }


    @RequestMapping(value = "/submitRegistration", method = RequestMethod.POST)
    public void submitRegistration(HttpServletRequest request, HttpServletResponse response) {
        this.logger.info("submit registration");

        String email = request.getParameter("email");
        String url = request.getContextPath() + "/showSendMailSuccessPage?email=" + email;
        Customer customer = new Customer();

        this.logger.info("save customer data");
        customer.setEmail(email);
        customer.setCity(request.getParameter("city"));
        customer.setPassword(request.getParameter("password"));
        customer.setStatus(CustomerStatus.RegNotConfirmed);
        customer.setActivationCode(loginService.generateActivationCode(email));
        customer.persist();

        request.getSession().setAttribute("email", email);
        mailService.sendEmail(email, MailService.SEND_MAIL_AGAIN);
        this.logger.info("submitRegistration success. ");
        response.setContentType("text/html; charset=UTF-8");
    }

    @RequestMapping(value = "/activateRegistration", method = RequestMethod.GET)
    public ModelAndView activateRegistration(HttpServletRequest request) {
        this.logger.info("register active start..");

        ModelAndView view = new ModelAndView();
        String activationCode = request.getParameter("activationCode");
        String email = request.getParameter("email");

        if (StringUtils.isBlank(email)) {
            view.setViewName("verifyRegistration");
            this.logger.error("Error activate registration failed that user unregistered");

            return view;
        }
        Customer customer = null;
        customer = Customer.findCustomersByEmail(email).getSingleResult();

        if (StringUtils.isBlank(customer.getEmail())) {
            view.setViewName("verifyRegistration");
            this.logger.error("Error activate registration failed that user unregistered");

            return view;
        }


        if (StringUtils.isNotBlank(activationCode) && activationCode.equals(customer.getActivationCode())) {
            customer.setStatus(CustomerStatus.RegConfirmed);
            customer.merge();
            request.getSession().setAttribute("loginFlag", "success");
            request.getSession().setAttribute("email", email);
            this.logger.info("user activate success");
        }

        return view;
    }


}
