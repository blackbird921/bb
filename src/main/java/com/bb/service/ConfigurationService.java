package com.bb.service;

import com.bb.domain.Configuration;
import com.bb.util.AutowiredLogger;
import com.bb.util.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ConfigurationService {

    @AutowiredLogger
    Logger logger;

    private List<Configuration> configurations;

    @PostConstruct
    public void init() {
        configurations = Configuration.findAllConfigurations();
    }

    public int getInteger(String key) {
        int value = 0;
        for (Configuration configuration : configurations) {
            if (configuration.getName().equalsIgnoreCase(key)) {
                if (StringUtils.isNumeric(configuration.getValue())) {
                    value = Integer.valueOf(configuration.getValue());
                }
            }
        }
        return value;
    }

    public String getValue(String key) {
        String value = "";
        for (Configuration configuration : configurations) {
            if (configuration.getName().equalsIgnoreCase(key)) {
                value = configuration.getValue();
            }
        }
        return value;
    }

    public int getCheckinValidRange() {
        return getInteger("CHECKIN_VALID_RANGE_IN_METER");
    }
}
