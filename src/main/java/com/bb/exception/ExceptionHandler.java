package com.bb.exception;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandler implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        System.out.println(" -- intoResolve Ex --");
        if (ex instanceof MaxUploadSizeExceededException) {
            System.out.println(" -- File Size Exceeds --");
        }
        return null;
    }

}
