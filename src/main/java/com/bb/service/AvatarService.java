package com.bb.service;

import com.bb.domain.Customer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface AvatarService {
    String FILE_SUFFIX = ".png";

    String uploadAvatar(Customer customer, HttpServletRequest request);

}
