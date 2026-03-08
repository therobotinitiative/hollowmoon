package com.orbital3d.web.application.service.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.web.application.database.entity.Group;
import com.orbital3d.web.application.database.repository.GroupRepository;
import com.orbital3d.web.application.service.GroupService;

@Service
public class GroupServiceImpl extends AbstractNamedServiceCrud<Group> implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Optional<Group> find(String name) {
        if(name == null) {
            throw new IllegalArgumentException("namecannot be null");
        }
        return groupRepository.findByGroupName(name);
    }

    @Override
    public Group rename(Long id, String newName) {
        Group g = find(id).orElseThrow(() -> new NoSuchElementException("Group with id:" + id + " not found"));
        g.setGroupName(newName);
        return groupRepository.save(g);
    }

    @Override
    public Group add(Group aggregate) {
        validateNonManagedAggregate(aggregate);
        return groupRepository.save(aggregate);
    }

    @Override
    public Group update(Group aggregate) {
        validateAggregate(aggregate);
        return groupRepository.save(aggregate);
    }

    @Override
    public Optional<Group> find(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Group id cannot be null");
        }
        return groupRepository.findById(id);
    }

    @Override
    public Optional<Group> find(Group aggregate) {
        if (aggregate == null) {
            throw new IllegalArgumentException("Aggregate cannot be null");
        }
        // Return values not yet used. Throws exception if all fields null
        this.validateIdentifiable(aggregate);
        Optional<Group> foundGroup = groupRepository.findById(aggregate.getId());
        // If not found by id, try group name
        if(foundGroup.isEmpty()) {
            foundGroup = groupRepository.findByGroupName(aggregate.getGroupName());
        }
        if (foundGroup.isPresent()) {
            return foundGroup;
        }
        throw new NoSuchElementException("Could not find group");
    }

    @Override
    public Set<Group> findByOwner(Long ownerId) {
        throw new UnsupportedOperationException("Group has no owner");
    }

    @Override
    public Set<Group> all() {
        Set<Group> groups = new HashSet<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    @Override
    public long count(Predicate<Group> predicate) {
        return doCount(predicate, groupRepository.findAll());
    }

    @Override
    public void delete(Group aggregate) {
        if (aggregate == null) {
            throw new IllegalArgumentException("Aggregate cannot be null");
        }
        // Return values not yet used. Throws exception if all fields null
        this.validateIdentifiable(aggregate);
        groupRepository.delete(aggregate);
    }

    @Override
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

}
