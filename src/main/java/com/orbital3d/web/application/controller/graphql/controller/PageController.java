package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.orbital3d.web.application.database.entity.Page;
import com.orbital3d.web.application.database.entity.Site;
import com.orbital3d.web.application.service.PageService;

/**
 * GraphQL controller for {@link Page} operations.
 */
@Controller
public class PageController implements GraphQLCrudController<Page, Long> {
	private final PageService pageService;

    protected PageController(PageService pageService) {
        this.pageService = pageService;
    }

	/**
	 * @return {@link Page} object with matching id.
	 */
	@Override
	@QueryMapping(value = "findPage")
	public Page find(@Argument Long pageId) {
		return pageService.find(pageId).get();
	}

	/**
	 * @return {@link Set} of {@link Page} objects based on the owner id.
	 */
	@Override
	@QueryMapping(value = "pagesByOwner")
	public List<Page> findByOwner(@Argument Long ownerId) {
		return new  ArrayList<>( pageService.findByOwner(Site.of(ownerId)) );
	}

	/**
	 * Store new {@link Page}
	 * @param pageName Name of the {@link Page} to store
	 * @param ownerId Owner ID of the {@Page} object to store
	 */
	@Override
	@MutationMapping(value = "addPage")
	public Page add(@Argument String pageName, @Argument Long ownerId) {
		return pageService.add(Page.of(Site.of(ownerId), pageName));
	}

	/**
	 * Remove {@link Page} with matching id.
	 * @param pageId ID of the {@link Page} to delete
	 * @return true if deletion is successful, false otherwise
	 */
	@Override
	@MutationMapping(value = "removePage")
	@Transactional
	public boolean remove(@Argument Long pageId) {
		pageService.delete( pageId );
		return true;
	}

	@Override
	@MutationMapping(value = "renamePage")
	@Transactional
	public Page rename(@Argument Long pageId, @Argument String pageName) {
		Page page = pageService.find(pageId).get();
		page.setName(pageName);
		return pageService.update(page);
	}

	@Override
	@MutationMapping(value = "changePageOwner")
	@Transactional
	public Page changeOwner(@Argument Long pageId, @Argument Long ownerId) {
		Page page = pageService.find(pageId).get();
		page.setOwner(Site.of(ownerId));
		return pageService.update(page);
	}
}
