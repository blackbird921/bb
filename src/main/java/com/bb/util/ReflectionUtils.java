package com.bb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This utility class has several methods that are useful when doing reflection.
 * @author qzeng
 */
public class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger( ReflectionUtils.class );

    /**
     * Returns the name of the method that called this one.
     * It is useful when trying to determine a call stack.
     * @return The name of the method that was called.
     */
    public static String getCallingMethodName() {

        RuntimeException t = new RuntimeException();
        StackTraceElement[] elements = t.getStackTrace();
        String methodName = null;
        if ( elements.length >= 2 ) {
            methodName = elements[1].getMethodName();
        }
        return methodName;
    }

    /**
     * Returns the method signature of the method that called this utility.
     * @return The method was was just called
     */
    @SuppressWarnings( "unchecked" )
    public Method getCallingMethod() {
        RuntimeException t = new RuntimeException();
        StackTraceElement[] elements = t.getStackTrace();
        String methodName = null;
        Method method = null;
        if ( elements.length > 2 ) {
            methodName = elements[1].getMethodName();
        }

        if ( methodName != null ) {
            Class[] params = new Class[]{};
            try {
                Class executingClass = Class.forName( elements[1].getClassName() );
                method = executingClass.getDeclaredMethod( elements[1].getMethodName(), params );
            } catch ( SecurityException e ) {
                LOGGER.error( "Error retrieving calling method", e );
            } catch ( ClassNotFoundException e ) {
                LOGGER.error( "Error retrieving calling method", e );
            } catch ( NoSuchMethodException e ) {
                LOGGER.error( "Error retrieving calling method", e );
            }
        }
        return method;
    }

    public static Method getGetterByPropertyName( Class clazz, String propertyName ) {

        Method method = null;
        Method[] methods = clazz.getDeclaredMethods();
        for ( Method m : methods ) {
            if ( m.getName().equalsIgnoreCase( "get" + propertyName ) || m.getName().equalsIgnoreCase( "is" + propertyName ) ) {
                method = m;
                break;
            }
        }

        return method;
    }

    public static Method getSetterByPropertyName( Class clazz, String propertyName ) {
        // get an instance
        Method method = null;
        Method[] methods = clazz.getDeclaredMethods();
        for ( Method m : methods ) {
            if ( m.getName().equalsIgnoreCase( "set" + propertyName ) ) {
                method = m;
                break;
            }
        }

        return method;
    }

    public static void invokeSetterByPropertyName( Object obj, String propertyName, Object value ) {
        // get an instance
        for ( Method m : obj.getClass().getDeclaredMethods() ) {
            if ( m.getName().equalsIgnoreCase( "set" + propertyName ) ) {
                try {
                    m.invoke( obj, value );
                } catch ( IllegalAccessException e ) {
                    LOGGER.error( "Error accessing Property Setter", e );
                } catch ( InvocationTargetException e ) {
                    LOGGER.error( "Error Invoking Property Setter", e );
                }
                break;
            }
        }
    }

    public static Object invokeGetterByPropertyName( Object obj, String propertyName ) {
        // get an instance
        for ( Method m : obj.getClass().getDeclaredMethods() ) {
            if ( propertyName != null && m.getName().equalsIgnoreCase( "get" + propertyName ) ) {
                try {
                    return m.invoke( obj );
                } catch ( IllegalAccessException e ) {
                    LOGGER.error( "Error accessing Property Getter", e );
                } catch ( InvocationTargetException e ) {
                    LOGGER.error( "Error Invoking Property Getter", e );
                }
                break;
            }
        }
        return null;
    }

    public static Object invokeByMethodName( Class clazz, String methodName, String parameter ) {
        // get an instance
        try {
            Object obj = clazz.newInstance();
            for ( Method m : obj.getClass().getDeclaredMethods() ) {
                if ( methodName != null && m.getName().equalsIgnoreCase( methodName ) ) {
                    return m.invoke( obj, parameter );
                }
            }
        } catch ( Exception e ) {
            LOGGER.error( "Error accessing Property Getter", e );
        }

        return null;
    }

    public static Object invokeByMethodName( Class clazz, String methodName, String parameter1, String parameter2) {
        // get an instance
        try {
            Object obj = clazz.newInstance();
            for ( Method m : obj.getClass().getDeclaredMethods() ) {
                if ( methodName != null && m.getName().equalsIgnoreCase( methodName ) ) {
                    return m.invoke( obj, parameter1, parameter2);
                }
            }
        } catch ( Exception e ) {
            LOGGER.error( "Error accessing Property Getter", e );
        }

        return null;
    }

    public static Object invokeByMethodName( Class clazz, String methodName, Object parameter1, Object parameter2, Object parameter3) {
        // get an instance
        try {
            Object obj = clazz.newInstance();
            for ( Method m : obj.getClass().getDeclaredMethods() ) {
                if ( methodName != null && m.getName().equalsIgnoreCase( methodName ) ) {
                    return m.invoke( obj, parameter1, parameter2, parameter3);
                }
            }
        } catch ( Exception e ) {
            LOGGER.error( "Error accessing Property Getter", e );
        }

        return null;
    }

}
