package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.orbital3d.web.application.database.entity.Page;
import com.orbital3d.web.application.database.entity.Site;
import com.orbital3d.web.application.service.NamedServiceCrud;
import com.orbital3d.web.application.service.PageService;
import com.orbital3d.web.application.service.SiteService;

/**
 * GraphQL controller for {@link Page} operations.
 */
@Controller
public class PageController extends AbstractGraphQLController<Page, Long, Site> {
	private static final Logger LOG = LoggerFactory.getLogger(PageController.class);

	@Autowired
	private PageService pageService;

	@Autowired
	private SiteService siteService;

	@Override
	protected NamedServiceCrud<Page, Long, String> getService() {
		return pageService;
	}

	@Override
	protected Site getOwner(Long ownerId) {
		LOG.debug("***********> Getting owner for {}", ownerId);
		return siteService.find(ownerId).get();
	}

	@Override
	protected Page getAggregate(Long ownerId, String name) {
		Site owner = this.getOwner(ownerId);
		if(owner == null) {
			throw new IllegalArgumentException("owner cannot be null, owner id:"+ownerId);
		}
		return Page.of(owner, name);
	}

	@QueryMapping
	public Page findPage(@Argument Long pageId) {
		return this.find(pageId);
	}

	@QueryMapping
	public List<Page> pagesByOwner(@Argument Long ownerId) {
		return new ArrayList<>(this.findByOwner(ownerId));
	}

	@MutationMapping
	public Page addPage(@Argument String pageName, @Argument Long ownerId) {
		return this.addAggregate(pageName, ownerId);
	}

	@MutationMapping
	public boolean removePage(@Argument Long pageId) {
		this.remove(pageId);
		return true;
	}

	@MutationMapping
	public Page renamePage(@Argument Long pageId, @Argument String pageName) {
		return this.rename(pageId, pageName);
	}

	@MutationMapping
	public Page changePageOwner(@Argument Long pageId, @Argument Long ownerId) {
		return this.changeOwner(pageId, ownerId);
	}
}
