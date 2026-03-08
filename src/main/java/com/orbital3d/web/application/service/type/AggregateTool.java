package com.orbital3d.web.application.service.type;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jakarta.persistence.Id;

/**
 * utility class for aggregate check operations.
 */
public final class AggregateTool {
    /**
     * Verify field annotated with {@link Id} is not null.
     * @param <T> Aggregate type
     * @param aggregate Aggregate instance
     * @throws IllegalArgumentException If id field is not null or cannot beaccessed
     */
    public static <T> void verifyIdNotNull(T aggregate) {
        Field [] declaredFields = aggregate.getClass().getDeclaredFields();
        Exception e = null;
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Id.class)) {
                try
                {
                    if (field.get(aggregate) != null) {
                        // non-null id found
                        return;
                    }
                } catch (IllegalAccessException ex) {
                    e = ex;
                }
            }
        }
        throw new IllegalArgumentException("No field annotated as id (not found or not accessible)", e);
    }

    /**
     * Verify field annotated with {@link Id} is null.
     * @param <T> Aggregate type
     * @param aggregate Aggregate instance
     * @throws IllegalArgumentException If id is not null orcannot be accessed
     */
    public static <T> void verifyIdNull(T aggregate) {
        Field [] declaredFields = aggregate.getClass().getDeclaredFields();
        Exception e = null;
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Id.class)) {
                try {
                    if(AggregateTool.isNull(field, aggregate)) {
                        // null identifiable found
                        return;
                    }
                } catch(IllegalAccessException | InvocationTargetException ex) {
                    e = ex;
                }
            }
        }
        throw new IllegalArgumentException("No field annotated as id (not found or not accessible)", e);
    }

    private static <T> boolean isNull(Field field, T ai) throws IllegalAccessException, InvocationTargetException {
        // Find getter
        for(Method m : ai.getClass().getDeclaredMethods()) {
            String fieldName = field.getName();
            if(m.getName().equalsIgnoreCase("get" + fieldName)) {
                if (m.invoke(ai, (Object[]) null) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> void checkManged(T aggregate) {
        AggregateTool.verifyIdNotNull(aggregate);
    }
}
