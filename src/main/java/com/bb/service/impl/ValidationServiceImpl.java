package com.bb.service.impl;

import com.bb.service.ValidationService;
import com.bb.util.AutowiredLogger;
import com.bb.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.logging.Logger;

@Service
public class ValidationServiceImpl implements ValidationService {
    @AutowiredLogger
    Logger logger;

    @Override
    public boolean existsUniqueValue( Class clazz, String fieldName, String value ) {
        Object result = ReflectionUtils.invokeByMethodName( clazz, "find" + clazz.getSimpleName() + "sBy" + fieldName, value );
        if ( result != null ) {
            TypedQuery tq = (TypedQuery) result;
            return tq.getResultList() != null && tq.getResultList().size() > 0;
        }
        return false;
    }
}
