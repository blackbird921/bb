package com.bb.service;

import com.bb.domain.Customer;
import com.bb.util.AutowiredLogger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    public static final String SEND_MAIL_AGAIN = "activateMail";
    public static final String RESET_PASSWORD = "forgotPwd";
    public static final String WEB_HOST = "http://localhost:8080";
    public static final String REG_MAIL_TPL = "emails/emailContent.vm";
    public static final String REG_MAIL_SUBJECT_TPL = "emails/emailSubject.vm";

    @AutowiredLogger
    private Logger logger;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityService velocityService;
    @Autowired
    private transient SimpleMailMessage templateMessage;

    public void sendEmail(String mailTo, String subject, String message) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo, false));
            msg.setFrom(new InternetAddress(templateMessage.getFrom()));
//            msg.setSubject(StringHelper.parseUtf8String(subject, "windows-1252", "utf-8"));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(message, "text/html;charset=utf-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(msg);
    }

    public boolean sendEmail(String mailTo, String mailType) {
        String email = mailTo;
        String emailContent = "";
        String from = templateMessage.getFrom();
        String subject = "";

        if (StringUtils.isBlank(email)) {
            return false;
        }

        Customer customer = null;
        try {
            customer = Customer.findCustomersByEmail(email).getSingleResult();
//            customer = new Customer();
//            customer.setEmail("sean.zeng@logicsolutions.com");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (customer == null) {
            return false;
        }
//        if (SEND_MAIL_AGAIN.equals(mailType)) {
//            subject = "wheel4 注册激活";
//        } else if (RESET_PASSWORD.equals(mailType)) {
//            subject = "wheel4 密码重置";
//        }

        try {
            subject = this.readEmailSubject(mailType);
            emailContent = this.readEmailContent(customer, mailType);
        } catch (IOException e) {
            this.logger.error("Error send email ", e);
            return false;
        }

        Thread t = new Thread(new SendMailThread(email, from, subject, emailContent));
        t.start();
        return true;
    }


    public String readEmailContent(Customer customer, String mailType) throws IOException {

        StringBuilder url = new StringBuilder();
        String email = customer.getEmail();
        String activationCode = "xfdfdefreieifeiio";

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
        ctx.put("password", customer.getPassword());

        return velocityService.format(REG_MAIL_TPL, ctx);
    }

    public String readEmailSubject(String mailType) throws IOException {
        Map<String, Object> ctx = new HashMap<String, Object>();
        if (SEND_MAIL_AGAIN.equals(mailType)) {
            ctx.put("activateEmail", true);
            ctx.put("resetPwd", false);
        } else if (RESET_PASSWORD.equals(mailType)) {
            ctx.put("activateEmail", false);
            ctx.put("resetPwd", true);
        }
        return velocityService.format(REG_MAIL_SUBJECT_TPL, ctx);
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
        mailService.sendEmail("wheel4_team@126.com", RESET_PASSWORD);
        mailService.sendEmail("wheel4_team@126.com", SEND_MAIL_AGAIN);
    }
}