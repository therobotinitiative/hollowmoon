package com.orbital3d.web.application.database.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.web.application.database.entity.Page;
import com.orbital3d.web.application.database.entity.Site;

public interface PageRepository extends CrudRepository<Page, Long> {
	Optional<Set<Page>> findByOwner(Site site);

	Optional<Set<Page>> findByOwnerId(Long ownerId);

	Optional<Page> findByName(String name);

	void deleteByOwnerId(Long ownerId);
	
	void deleteByOwnerIdIn(List<Long> ids);
}
