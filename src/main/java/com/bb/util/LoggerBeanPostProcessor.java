/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bb.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Injects loggers into new bean instances based on reflection.</p>
 * <p/>
 * <p>Default logger factories configured are SLF4J, Apache Commons,
 * Log4J, and JDK 1.4 Logging.</p>
 * <p>This class was extracted from the SpringByExample bundle, as we only wanted this feature.
 * we have authorization from the original author to use this.</p>
 *
 * @author Tim Voet
 * @author David Winterfeldt
 */
public class LoggerBeanPostProcessor implements BeanPostProcessor, Ordered {

    private Map<String, String> hLoggerFactories = new HashMap<String, String>();
    private String methodName = null;

    /**
     * Constructor.
     */
    public LoggerBeanPostProcessor() {
        this.hLoggerFactories.put( "org.slf4j.Logger", "org.slf4j.LoggerFactory.getLogger" );
    }

    /**
     * Sets target method name to set logger.
     *
     * @param methodName String
     */
    public void setMethodName( String methodName ) {
        this.methodName = methodName;
    }

    /**
     * Set logger factory <code>Map</code>.
     *
     * @param loggerFactories String
     */
    public void setLoggerFactoryMap( Map<String, String> loggerFactories ) {
        this.hLoggerFactories = loggerFactories;
    }

    /**
     * This method is used to execute before a bean's initialization callback.
     *
     * @param bean     {@link Object}
     * @param beanName String
     * @return {@link Object}
     * @throws org.springframework.beans.BeansException .
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(Object, String)
     */
    public Object postProcessBeforeInitialization( Object bean, String beanName ) throws BeansException {
        this.processLogger( bean, this.methodName );
        return bean;
    }

    /**
     * This method is used to execute after a bean's initialization callback.
     *
     * @param bean     {@link Object}
     * @param beanName {@link String}
     * @return {@link Object}
     * @throws org.springframework.beans.BeansException .
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(Object, String)
     */
    public Object postProcessAfterInitialization( Object bean, String beanName ) throws BeansException {
        return bean;
    }

    /**
     * Get order for processing.
     *
     * @return int
     * @see org.springframework.core.Ordered#getOrder()
     */
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * Instantiates bean specific loggers and sets them.
     *
     * @param bean       {@link Object}
     * @param methodNameString {@link String}
     */
    protected void processLogger( final Object bean, final String methodNameString ) {
        final Class<?> clazz = bean.getClass();

        ReflectionUtils.doWithMethods( clazz, new ReflectionUtils.MethodCallback() {
            public void doWith( Method method ) {
                if ( method.getName().equals( methodNameString ) ) {
                    try {
                        LoggerBeanPostProcessor.this.injectMethod( bean, method );
                    } catch ( Throwable e ) {
                        throw new FatalBeanException( "Problem injecting logger.  " + e.getMessage(), e );
                    }
                }
            }
        } );
    }

    /**
     * Processes a property descriptor to inject a logger.
     *
     * @param bean   {@link Object}
     * @param method {@link java.lang.reflect.Method}
     */
    public void injectMethod( Object bean, Method method ) {
        PropertyDescriptor pd = BeanUtils.findPropertyForMethod( method );

        if ( pd != null ) {
            String canonicalName = pd.getPropertyType().getCanonicalName();

            Object logger = this.getLogger( bean.getClass().getName(), canonicalName );

            if ( logger != null ) {
                try {
                    pd.getWriteMethod().invoke( bean, new Object[]{logger} );
                } catch ( Throwable e ) {
                    throw new FatalBeanException( "Problem injecting logger.  " + e.getMessage(), e );
                }
            }
        }
    }

    /**
     * Gets logger based on the logger name and type of
     * logger (class name, ex: 'org.slf4j.Logger').
     *
     * @param loggerName {@link String}
     * @param loggerType {@link String}
     * @return {@link Object}
     */
    protected Object getLogger( String loggerName, String loggerType ) {
        Object result = null;

        String staticMethod = this.hLoggerFactories.get( loggerType );

        if ( staticMethod != null ) {
            try {
                MethodInvokingFactoryBean factory = new MethodInvokingFactoryBean();
                factory.setStaticMethod( staticMethod );
                factory.setArguments( new Object[]{loggerName} );
                factory.afterPropertiesSet();

                result = factory.getObject();
            } catch ( Throwable e ) {
                throw new FatalBeanException( "Problem injecting logger.  " + e.getMessage(), e );
            }
        }

        return result;
    }

}
