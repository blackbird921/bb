package com.bb.service.impl;

import com.bb.service.ValidationService;
import com.bb.util.AutowiredLogger;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ValidationServiceImpl implements ValidationService{
    @AutowiredLogger
    Logger logger;

    @Override
    public boolean existsUniqueValue( Class clazz, String fieldName ) {
        return false;
    }
}
