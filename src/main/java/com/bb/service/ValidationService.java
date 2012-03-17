package com.bb.service;

import com.bb.util.AutowiredLogger;
import com.bb.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;

@Service
public class ValidationService {

    @AutowiredLogger
    Logger logger;

    public boolean existsUniqueValue(Class clazz, String fieldName, String value, Long id) {
        Object result = null;
        if (id == null) {
            result = ReflectionUtils.invokeByMethodName(clazz, "find" + clazz.getSimpleName() + "sBy" + fieldName, value);
        } else {
            result = ReflectionUtils.invokeByMethodName(clazz, "find" + clazz.getSimpleName() + "sByFieldExcludeById", fieldName, value, id);
        }
        if (result != null) {
            TypedQuery tq = (TypedQuery) result;
            if (tq.getResultList() != null && tq.getResultList().size() > 0) {
                return true;
            }
        }
        return false;
    }
}
