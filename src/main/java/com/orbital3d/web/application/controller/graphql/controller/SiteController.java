package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.entity.Site;
import com.orbital3d.web.application.service.ClientService;
import com.orbital3d.web.application.service.NamedServiceCrud;
import com.orbital3d.web.application.service.SiteService;

/**
 * GraphQL controller for {@Site} operations.
 */
@Controller
public class SiteController extends AbstractGraphQLController<Site, Long, Client> {
	@Autowired
	private SiteService siteService;

	@Autowired
	private ClientService clientService;

	@Override
	protected NamedServiceCrud<Site, Long, String> getService() {
		return siteService;
	}

	@Override
	protected Client getOwner(Long ownerId) {
		return clientService.find(ownerId).orElseThrow(() -> new IllegalArgumentException("Could not find Client with id:" + ownerId));
	}

	@Override
	protected Site getAggregate(Long ownerId, String name) {
		return Site.of(getOwner(ownerId), name);
	}

	@QueryMapping
	public List<Site> sitesByOwner(@Argument Long ownerId) {
		return new ArrayList<>(siteService.findByOwner(ownerId));
	}

	@QueryMapping
	public Site siteById(@Argument Long siteId) {
		return this.find(siteId);
	}

	@MutationMapping
	public Site addSite(@Argument String siteName, @Argument Long ownerId) {
		return this.addAggregate(siteName, ownerId);
	}

	@MutationMapping
	public boolean removeSite(@Argument Long siteId) {
		this.remove(siteId);
		return true;
	}

	@MutationMapping
	public Site renameSite(@Argument String siteName, @Argument Long siteId) {
		return this.rename(siteId, siteName);
	}

	@MutationMapping
	public Site changeSiteOwner(@Argument Long ownerId, @Argument Long siteId) {
		return this.changeOwner(siteId, ownerId);
	}
}
