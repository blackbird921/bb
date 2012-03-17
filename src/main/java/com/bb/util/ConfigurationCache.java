package com.bb.util;

import com.bb.domain.Configuration;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.Map.Entry;


@Component
public class ConfigurationCache {

//    private Map<String, Configuration> configurationCache;
//
//    private static ConfigurationCache instance;
//
//    public static ConfigurationCache getIntance() {
//        return instance;
//    }
//
//    private static void setInstance(ConfigurationCache value) {
//        instance = value;
//    }
//
//    @PostConstruct
//    @ManagedOperation(description = "Initializes the configuration cache, by loading the configurations values from the DB.")
//    public void init() {
//
//        if (this.configurationCache == null) {
//            this.configurationCache = new HashMap<String, Configuration>();
//        }
//
//
//        List<Configuration> list = Configuration.findAllConfigurations();
//        if (list != null) {
//            for (Configuration ce : list) {
//                this.configurationCache.put(ce.getName(), ce);
//            }
//        }
//
//        ConfigurationCache.setInstance(this);
//    }
//
//    public void addConfigurationValue(String parameterName, Configuration entity) {
//        this.configurationCache.put(parameterName, entity);
//    }
//
//    public Configuration getByParameterName(String parameterName) {
//        if (parameterName == null) {
//            return null;
//        }
//
//        Configuration configurationEntity = null;
//
//        if (this.configurationCache.containsKey(parameterName)) {
//            configurationEntity = this.configurationCache.get(parameterName);
//        } else {
////            configurationEntity = Configuration.findConfigurationsByName(parameterName);
//
//            // If we find it in the DB we add it to the cache
//            if (configurationEntity != null) {
//                addConfigurationValue(parameterName, configurationEntity);
//            }
//        }
//
//        return configurationEntity;
//    }
//
//    public Map<String, Configuration> getConfigurationCache() {
//        return this.configurationCache;
//    }
//
//    @ManagedOperation()
//    @ManagedOperationParameters(
//            {
//                    @ManagedOperationParameter(name = "paramName", description = "The configuration parameter name.")
//            }
//    )
//    public String getParameterValue(String paramName) {
//        if (this.configurationCache.containsKey(paramName)) {
////            return this.configurationCache.get(paramName).getParameterValue();
//            return null;
//        } else {
//            return null;
//        }
//    }
//
//
//    @ManagedOperation(description = "Returns the list of the cache values")
//    public List<String> listValues() {
//        List<String> values = new ArrayList<String>();
//        Set<Entry<String, Configuration>> entrySet = this.configurationCache.entrySet();
//        for (Entry<String, Configuration> entry : entrySet) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(entry.getKey());
//            sb.append(":");
////            sb.append(entry.getValue().getParameterValue());
//            sb.append("(");
////            sb.append(entry.getValue().getParameterType());
//            sb.append(")");
//            values.add(sb.toString());
//        }
//        return values;
//    }
}
