package com.orbital3d.web.application.service;

import java.util.Optional;

/**
 * Interface to extend service layer for aggregates that have a name attributes which can be used for identification.
 * 
 * <T> - Type of the aggregate
 * <U> - Type of the aggregate id
 * <V> - Type of the name field to identify aggregate
 */
public interface NamedServiceCrud<T, U extends Number, V extends String> extends ServiceCrud<T, U> {
    Optional<T> find(V name);
    T rename(U id, V newName);
}
