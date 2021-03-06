package com.bb.web;

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


@Controller
@RequestMapping("/mail")
public class SendMailAction {

    @AutowiredLogger
    private Logger logger;
    @Autowired
    private MailService mailService;


    @RequestMapping(value="/sendMail",method = RequestMethod.POST)
    public ModelAndView sendMail(HttpServletRequest request, HttpServletResponse response) {
        this.logger.info("send mail start..");
        ModelAndView view = new ModelAndView();

        String email = request.getParameter("email");
        String json = "";
        String mailType = request.getParameter("mailType");
        this.logger.info("mail type : " + mailType);

        if(MailService.SEND_MAIL_AGAIN.equals(mailType)) {
            view.addObject("activateSuccess", true);
            view.addObject("resetPwdSuccess", false);
        }else if(MailService.RESET_PASSWORD.equals(mailType)) {
            view.addObject("activateSuccess", false);
            view.addObject("resetPwdSuccess", true);
        }

        if (StringUtils.isBlank(email)) {
            view.setViewName("/forgetPassword");
        } else {
            request.getSession().setAttribute("email", email);
            view.addObject("sendMailResult", mailService.sendEmail(email, mailType));
            view.setViewName("sendMailResult");
        }

        return view;

    }

    @RequestMapping(value="/showSendMailSuccessPage",method = RequestMethod.GET)
    public ModelAndView showSendMailSuccessPage(HttpServletRequest request, HttpServletResponse response) {
        this.logger.info("show page of send mail successed ");

        ModelAndView view = new ModelAndView();
        return view;
    }

    @RequestMapping(value = "/showSendMailAgain", method = RequestMethod.GET)
    public ModelAndView showSendMailAgain(HttpServletRequest request, HttpServletResponse response) {
        this.logger.info("show page of send mail again ");

        ModelAndView view = new ModelAndView();
        String email = request.getParameter("email");

        view.addObject("email", email);
        view.addObject("mailType", request.getParameter("mailType"));

        return view;
    }

}
