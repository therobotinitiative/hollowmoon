package com.orbital3d.web.application.service.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orbital3d.web.application.database.entity.Page;
import com.orbital3d.web.application.database.entity.Site;
import com.orbital3d.web.application.database.repository.PageRepository;
import com.orbital3d.web.application.service.PageService;

/**
 * Implementation of {@link PageService}.
 */
@Service
public class PageServiceImpl extends AbstractNamedServiceCrud<Page> implements PageService {
	private static final Logger LOG = LoggerFactory.getLogger(PageServiceImpl.class);

	@Autowired
	private PageRepository pageRepository;

	@Override
	@Transactional
	public Page add(Page aggregate) {
		LOG.trace("Add page {}", aggregate.getName());
		return pageRepository.save(aggregate);
	}

	@Override
	public Optional<Page> find(Long id) {
		LOG.trace("Getting page by id {}", id);
		return pageRepository.findById(id);
	}

	@Override
	@Transactional
	public void deleteByOwner(Site site) {
		Set<Page> pages = findByOwner(site);
		pageRepository.deleteAll(pages);
	}

    @Override
    public void delete(Page page) {
		pageRepository.delete(page);
    }

	@Override
	public Page update(Page page) {
		return pageRepository.save(page);
	}

    @Override
    public Optional<Page> find(String name) {
		return pageRepository.findByName(name);
    }

    @Override
    public Optional<Page> find(Page aggregate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Page> all() {
		Set<Page> allPages = new HashSet<>();
		pageRepository.findAll().forEach(allPages::add);
		return allPages;	
	}

    @Override
    public long count(Predicate predicate) {
        return doCount(predicate, pageRepository.findAll());
    }

    @Override
    public void delete(Long id) {
		pageRepository.deleteById(id);
    }

    @Override
    public Page rename(Long id, String newName) {
		Page page = pageRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Page not found with id: " + id));
		page.setName(newName);
		return pageRepository.save(page);
    }

    @Override
    public Set<Page> findByOwner(Long ownerId) {
		return pageRepository.findByOwnerId(ownerId).orElseThrow(() -> new IllegalArgumentException("Site not found with id: " + ownerId));
    }

    @Override
    public Set<Page> findByOwner(Site site) {
		return pageRepository.findByOwnerId(site.getId()).orElseThrow(() -> new IllegalArgumentException("Site not found with id: " + site.getId()));
    }
}
