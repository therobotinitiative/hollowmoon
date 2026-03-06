package com.orbital3d.web.application.service;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Interface to define common CRUD operations for aggregates. Service layer is to be used and not to use database layer
 * directly to perform operations for aggregates.
 */
public interface ServiceCrud<T, U extends Number> {
    /**
     * Store new aggregate.
     * @param aggregate aggregate to add
     * @return Stored aggregate with id. This instance must be used for subsequent service operations
     * @throws IllegalArgumentException If aggregate is null or any of it's required attributes are null. If the stored aggregate
     * violates any of the documented restrictions.
     */
    T add(T aggregate);

    /**
     * Update stored aggregate.
     * @param aggregate Existing aggregate to update.
     * @throws IllegalArgumentException If aggregate is null or violates any of the documented restrictions
     * @return Updated aggregate
     */
    T update(T aggregate);

    /**
     * Find aggregate by id.
     * @return Found aggregate wrapped in {@link Optional}
     */
    Optional<T> find(U id);
    Optional<T> find(T aggregate);

    /**
     * Find aggregates by owner.
     * @param ownerId Owner ID to filter by
     * @return {@link Set} of found aggregates
     */
    Set<T> findByOwner(Long ownerId);

    /**
     * @return  {@link Set} of all stored aggregates of type <T>.
     */
    Set<T> all();

    /**
     * @return Number of stored aggregatets matching the {@link Predicate}
     */
    long count(Predicate predicate);

    /**
     * Delete stored aggregate.
     * @param aggregate to delete
     * @throws IllegalArgumentException If aggregate cannot be resolved
     */
    void delete(T aggregate);
    
    /**
     * Delete stored aggregate by id.
     * @throws IllegalArgumentException if id is not matched to any stored aggregate
     */
    void delete(U id);
}
