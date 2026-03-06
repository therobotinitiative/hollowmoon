package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.entity.Site;
import com.orbital3d.web.application.service.SiteService;

/**
 * GraphQL controller for {@Site} operations.
 */
@Controller
public class SiteController implements GraphQLCrudController<Site, Long> {
	private final SiteService siteService;

	protected SiteController(final SiteService siteService) {
		this.siteService = siteService;
	}

	/**
	 * @param Owner id
	 * @return {@List} of {@Site}s owned by owner id
	 */
	@Override
	@QueryMapping(value = "sitesByOwner")
	public List<Site> findByOwner(@Argument Long ownerId) {
		return new ArrayList<>(siteService.find(Client.of(ownerId)));
	}

	/**
	 * Remove {@Site} based on the id.
	 * 
	 * @param siteId Site id to remove
	 * @return Boolean true if removed successfully
	 */
	@Override
	@MutationMapping(value = "removeSite")
	@Transactional
	public boolean remove(@Argument Long siteId) {
		siteService.delete(siteId);
		return true;
	}

	/**
	 * Rename {@Site} based on the id.
	 * 
	 * @param siteName {@Site} objects new name
	 * @param siteId {@Site} id
	 * @return Renamed @Site} object
	 */
	@Override
	@MutationMapping(value = "renameSite")
	@Transactional
	public Site rename(@Argument Long siteId, @Argument String siteName) {
		Site site = siteService.find(siteId).get();
		site.setName(siteName);
		return siteService.update(site);
	}

	/**
	 * Change owner of {@Site}.
	 * 
	 * @param ownerId Id of the {@Client} owner.
	 * @param siteId {@Site} id
	 * @return {@Site} with changed owner
	 */
	@Override
	@MutationMapping(value = "changeSiteOwner")
	@Transactional
	public Site changeOwner(@Argument Long ownerId, @Argument Long siteId) {
		Site site = siteService.find(siteId).get();
		site.setOwner(Client.of(ownerId));
		return siteService.update(site);
	}

    @Override
	@QueryMapping(value = "siteById")
    public Site find(@Argument Long id) {
		return siteService.find(id).orElseThrow(() -> new NoSuchElementException("Site not found with id: " + id));
    }

	/**
	 * Store new {@Site}.
	 * 
	 * @param siteName Name of the {@Site}
	 * @param ownerId Owned ID of the {@Site}
	 * @return Stored {@Site} aggregate
	 */
    @Override
	@MutationMapping(value = "addSite")
    public Site add(String name, Long ownerId) {
		return siteService.add(Site.of(Client.of(ownerId), name));
    }

}
