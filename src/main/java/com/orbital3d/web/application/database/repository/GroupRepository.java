package com.orbital3d.web.application.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.web.application.database.entity.Group;

public interface GroupRepository extends CrudRepository<Group, Long>{
    Optional<Group> findByGroupName(String groupName);
}
