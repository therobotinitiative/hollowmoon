package com.orbital3d.web.application.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.web.application.database.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
