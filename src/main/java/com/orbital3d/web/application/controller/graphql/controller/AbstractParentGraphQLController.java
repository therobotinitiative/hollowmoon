package com.orbital3d.web.application.controller.graphql.controller;

import java.util.Collections;
import java.util.List;

import com.orbital3d.web.application.service.NamedServiceCrud;

public abstract class AbstractParentGraphQLController<T, U extends Number> implements GraphQLCrudController<T, U> {
    
    protected abstract NamedServiceCrud<T, U, String> getService();
    protected abstract T getAggregate(Long ownerId, String name);

    @Override
    public T find(U id) {
        return getService().find(id).orElseThrow(() -> new IllegalArgumentException("Entity not found with id: " + id));
    }

    @Override
    public T rename(U id, String newName) {
        return getService().rename(id, newName);
    }

    @Override
    public T addAggregate(String name, Long ownerId) {
        return getService().add(getAggregate(ownerId, name));
    }

    @Override
    public boolean remove(U id) {
        getService().delete(id);
        return true;
    }

    @Override
    public T changeOwner(U id, Long newOwnerId) {
        throw new UnsupportedOperationException("Aggregate has no parent");
    }

    @Override
    public List<T> findByOwner(Long ownerId) {
        return Collections.emptyList();
    }
}
