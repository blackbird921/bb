package com.bb.service;

import com.bb.util.AutowiredLogger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Service
public interface ValidationService {

    boolean existsUniqueValue( Class clazz, String fieldName );

}
