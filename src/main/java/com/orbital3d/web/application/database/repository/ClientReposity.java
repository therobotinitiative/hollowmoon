package com.orbital3d.web.application.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.web.application.database.entity.Client;

public interface ClientReposity extends CrudRepository<Client, Long> {
	Optional<Client> findByName(String name);
	Optional<Client> findByClientCode(String clientCode);
}
