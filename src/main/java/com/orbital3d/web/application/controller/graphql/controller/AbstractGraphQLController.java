package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import com.orbital3d.web.application.service.NamedServiceCrud;
import com.orbital3d.web.application.service.type.ChildAggregate;

public abstract class AbstractGraphQLController<T, U extends Number> implements GraphQLCrudController<T, U> {
    
    protected abstract NamedServiceCrud<T, U, String> getService();
    protected abstract <V> V getOwner(Long ownerId);

    @Override
    public T find(U id) {
        return getService().find(id).orElseThrow(() -> new IllegalArgumentException("Entity not found with id: " + id));
    }

    @Override
    public T rename(U id, String newName) {
        return getService().rename(id, newName);
    }

    @Override
    public T add(String name, Long ownerId) {
        throw new UnsupportedOperationException("Add operation is not supported.");
    }

    @Override
    public boolean remove(U id) {
        getService().delete(id);
        return true;
    }

    @Override
    public T changeOwner(U id, Long newOwnerId) {
        T entity = getService().find(id).get();
        if (entity instanceof ChildAggregate) {
            ((ChildAggregate) entity).setOwner(getOwner(newOwnerId));
        } else {
            throw new IllegalArgumentException("Entity with id: " + id + " is not an owned aggregate.");
        }
        return getService().update(entity);
    }

    @Override
    public List<T> findByOwner(Long ownerId) {
        return new ArrayList<>(getService().findByOwner(ownerId));
    }
}
