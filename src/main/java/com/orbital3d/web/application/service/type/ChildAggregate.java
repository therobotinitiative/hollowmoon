package com.orbital3d.web.application.service.type;

/**
 * Marker interface for child aggregates.
 * 
 * <T> - Owner type, must be type {@link ParentAggregate}.
 */
public interface ChildAggregate<T extends ParentAggregate> {
    /**
     * Get the owner of this child aggregate.
     * @return Owner of this child aggregate.
     */
    T getOwner();

    /**
     * Set the owner of this child aggregate.
     * @param owner Owner to set for this child aggregate.
     */
    void setOwner(T owner);
}
