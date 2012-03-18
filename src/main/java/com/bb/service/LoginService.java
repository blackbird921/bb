package com.bb.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public String generateActivationCode(String email) {

        String code = (email.length()>=6?email.substring(0, 6):email) + System.currentTimeMillis();
        code = Base64.encodeBase64String(code.getBytes());
        return code.substring(0, 16);
    }

}
