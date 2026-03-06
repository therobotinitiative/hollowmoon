package com.orbital3d.web.application.service;

import java.util.Set;

import com.orbital3d.web.application.database.entity.Page;
import com.orbital3d.web.application.database.entity.Site;

/**
 * Page service interface to perform operations on {@link Page}.
 * 
 * T - Aggregate type {@link Page}
 * U - Aggregate id type {@link Long}
 * V - Aggregate name type {@link String}
 */
public interface PageService extends NamedServiceCrud<Page, Long, String> {
	/**
	 * @return {@link Set} of {@link Page} objects owned by {@link Site}
	 */
	Set<Page> findByOwner(Site site);

	/**
	 * Delete all {@link Page} objects owned by {@link Site}
	 */
	void deleteByOwner(Site site);

}
