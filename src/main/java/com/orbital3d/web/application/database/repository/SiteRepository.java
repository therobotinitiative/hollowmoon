package com.orbital3d.web.application.database.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.entity.Site;

public interface SiteRepository extends CrudRepository<Site, Long> {
	Optional<Set<Site>> findByOwner(Client owner);

	Optional<Set<Site>> findByOwnerId(Long ownerId);

	Optional<Site> findByName(String name);

	void deleteByOwnerId(Long ownerId);

	void deleteByOwnerIdIn(List<Long> ids);
}
