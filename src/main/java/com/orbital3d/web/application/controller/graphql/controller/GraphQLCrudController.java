package com.orbital3d.web.application.controller.graphql.controller;

import java.util.List;

/**
 * Interface for GraphQL controllers that provide basic CRUD operations.
 * 
 * @param <U> Type of the aggregate being managed (e.g., Client, Site, Page)
 * @param <V> Type of the aggregate's identifier (e.g., Long)
 */
public interface GraphQLCrudController<U, V> {
   /**
    * Find an aggregate by its identifier.
    */
   U find(V id);
   /**
    * Rename an aggregate.
    */
   U rename(V id, String newName);
   /**
    * Add an aggregate.
    */
   U add(String name, Long ownerId);
   /**
    * Remove an aggregate.
    */
   boolean remove(V id);
   /**
    * Change the owner of an aggregate.
    */
   U changeOwner(V id, Long newOwnerId);
   /**
    * Find aggregates by their owner.
    */
   List<U> findByOwner(Long ownerId);
}
