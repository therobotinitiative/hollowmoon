package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import com.orbital3d.web.application.service.NamedServiceCrud;
import com.orbital3d.web.application.service.type.ParentAggregate;

public abstract class AbstractGraphQLController<T, U extends Number, V extends ParentAggregate> implements GraphQLCrudController<T, U> {
    
    protected abstract NamedServiceCrud<T, U, String> getService();
    protected abstract V getOwner(Long ownerId);
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
        T entity = getService().find(id).get();
        try {
            return getService().update(entity);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Entity with id: " + id + " is not an child aggregate.", e);
        }
    }

    @Override
    public List<T> findByOwner(Long ownerId) {
        return new ArrayList<>(getService().findByOwner(ownerId));
    }
}
