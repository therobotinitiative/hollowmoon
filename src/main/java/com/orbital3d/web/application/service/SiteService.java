package com.orbital3d.web.application.service;

import java.util.Set;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.entity.Site;

/**
 * Service layer interface to perform operations on {@link Site}.
 */
public interface SiteService extends NamedServiceCrud<Site, Long, String> {
	/**
	 * @param client Owner {@link Client}
	 * @return {@link Set} of {@link Site} owned by {@link Client}
	 */
	Set<Site> find(Client client);

	/**
	 * Deletes all {@link Site}s owned by {@link Client}. All aggregates owned by {@link Site} are deleted.
	 * 
	 * @param client Owner {@link Client}
	 * @throws IllegalArgumentException if client is null
	 * @throws IllegalArgumentException if client id is null
	 * @throws NoSuchElementException if no client with the given id exists
	 */
	void delete(Client client);

}
