package com.orbital3d.web.application.service.impl;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import com.orbital3d.web.application.database.entity.annotation.Identifiable;
import com.orbital3d.web.application.service.type.AggregateTool;

public abstract class AbstractNamedServiceCrud<T> {
    protected void validateAggregate(T aggregate) {

    }

    protected void validateManagedAggregate(T aggregate) {
        AggregateTool.checkManged(aggregate);
    }

    protected void validateNonManagedAggregate(T aggregate) {
        AggregateTool.verifyIdNull(aggregate);
    }

    protected Long doCount(Predicate<T> predicate, Iterable<T> ite) {
                return StreamSupport.stream(ite.spliterator(), true).filter(predicate).count();
    }

    protected Field[] validateIdentifiable(T aggregate) {
        Set<Field> identifiableFields = new HashSet<>();
        Field [] fields = aggregate.getClass().getDeclaredFields();
        boolean found = false;
        for (Field f : fields) {
            if (f.isAnnotationPresent(Identifiable.class)) {
                identifiableFields.add(f);
                try {
                    if(f.get(aggregate)!=null) {
                        found = true;
                    }
                } catch(IllegalAccessException ex) {
                    // think of something
                }
            }
        }
        if (!found) {
            throw new IllegalArgumentException("All identifiable attributes are null");
        }
        return identifiableFields.toArray(Field[]::new);
    }
}
