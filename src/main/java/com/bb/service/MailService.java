package com.bb.service;

import com.bb.domain.Customer;
import com.bb.util.AutowiredLogger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    public static final String SEND_MAIL_AGAIN = "activateMail";
    public static final String RESET_PASSWORD = "forgotPwd";
    public static final String WEB_HOST = "http://localhost:8080";
    public static final String REG_MAIL_TPL = "emails/emailContent.vm";

    @AutowiredLogger
    private Logger logger;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private VelocityService velocityService;
    @Autowired
    private transient SimpleMailMessage templateMessage;

    public void sendEmail(String mailTo, String subject, String message) {
        org.springframework.mail.SimpleMailMessage mailMessage = new org.springframework.mail.SimpleMailMessage(templateMessage);
        mailMessage.setTo(mailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void sendEmail(String mailTo, String mailType) {
        String email = mailTo;
        String emailContent = "";
        String from = templateMessage.getFrom();
        String subject = "";

        if (StringUtils.isBlank(email)) {
            return;
        }

        Customer customer = null;
        try {
            customer = Customer.findCustomersByEmail(email).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (customer == null) {
            return;
        }
        if (SEND_MAIL_AGAIN.equals(mailType)) {
            subject = "wheel4 注册激活";
        } else if (RESET_PASSWORD.equals(mailType)) {
            subject = "[wheel4]找回您的帐户密码";
        }

        try {
            emailContent = this.readEmailContent(customer, mailType);
        } catch (IOException e) {
            this.logger.error("Error send email ", e);
        }

        Thread t = new Thread(new SendMailThread(email, from, subject, emailContent));
        t.start();

    }


    public String readEmailContent(Customer customer, String mailType) throws IOException {

        StringBuilder url = new StringBuilder();
        String email = customer.getEmail();
        String activationCode = "";

//        activationCode = customer.getActivationCode();

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("email", email);
        if (SEND_MAIL_AGAIN.equals(mailType)) {
            url.append(WEB_HOST + "/activateRegistration.do?");
            ctx.put("activateEmail", true);
            ctx.put("resetPwd", false);
        } else if (RESET_PASSWORD.equals(mailType)) {
            url.append(WEB_HOST + "/showResetPassword.do?");
            ctx.put("activateEmail", false);
            ctx.put("resetPwd", true);
        }

        url.append("email=").append(email);
        url.append("&activationCode=").append(activationCode);
        ctx.put("link", url.toString());

        return velocityService.format(REG_MAIL_TPL, ctx);
    }


    private class SendMailThread implements Runnable {
        String email;
        String from;
        String subject;
        String emailContent;


        SendMailThread(String email, String from, String subject, String emailContent) {
            this.email = email;
            this.from = from;
            this.subject = subject;
            this.emailContent = emailContent;
        }

        @Override
        public void run() {
            logger.info("send mail to " + this.email + " start ");
            sendMail();
            logger.info("send mail to " + this.email + " finish ");
        }

        private void sendMail() {
            sendEmail(email, subject, emailContent);
        }
    }


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
        MailService mailService = (MailService) context.getBean("mailService");
        mailService.sendEmail("zq72@yahoo.com", SEND_MAIL_AGAIN);
    }
}