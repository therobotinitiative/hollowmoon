package com.orbital3d.web.application.service.impl;

import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public abstract class AbstractNamedServiceCrud<T> {
    protected Long doCount(Predicate<T> predicate, Iterable<T> ite) {
                return StreamSupport.stream(ite.spliterator(), true).filter(predicate).count();
    }
}
