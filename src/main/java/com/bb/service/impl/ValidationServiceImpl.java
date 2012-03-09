package com.bb.service.impl;

import com.bb.domain.Customer;
import com.bb.service.ValidationService;
import com.bb.util.AutowiredLogger;
import com.bb.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ValidationServiceImpl implements ValidationService {
    @AutowiredLogger
    Logger logger;

    @Override
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
