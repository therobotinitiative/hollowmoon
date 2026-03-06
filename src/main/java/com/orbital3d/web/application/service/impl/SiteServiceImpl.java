package com.orbital3d.web.application.service.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.entity.Site;
import com.orbital3d.web.application.database.repository.SiteRepository;
import com.orbital3d.web.application.service.PageService;
import com.orbital3d.web.application.service.SiteService;

/**
 * JPA implemwntation of {@link SiteService}.
 */
@Service
public class SiteServiceImpl extends AbstractNamedServiceCrud<Site> implements SiteService {

	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private PageService ps;

	@Override
	public Set<Site> find(Client client) {
		if (client == null) {
			HashSet<Site> allSites = new HashSet<>();
			siteRepository.findAll().forEach(allSites::add);
			return allSites;
		}
		return siteRepository.findByOwnerId(client.getId()).get();
	}

	@Override
	@Transactional
	public Site add(Site site) {
		if (site == null || site.getOwner() == null || site.getName() == null) {
			throw new IllegalArgumentException("Site cannot be null");
		}
		return siteRepository.save(site);
	}

/* 	@Override
	@Transactional
	public void remove(Client client) {
		find(client).forEach((site) -> {
			ps.deleteByOwner(site);
		});
		for (Site site : sites) {
			ps.deleteByOwner(site);
		}
		siteRepository.deleteAll(sites);
	} */

    @Override
    public Optional<Site> find(Long id) {
		return siteRepository.findById(id);
    }

    @Override
	@Transactional
    public Site update(Site aggregate) {
		return siteRepository.save(aggregate);
    }

    @Override
    public Optional<Site> find(String name) {
		return siteRepository.findByName(name);
    }

    @Override
    public Optional<Site> find(Site aggregate) {
		if (aggregate == null) {
			throw new IllegalArgumentException("Aggregate cannot be null");
		}
		if (aggregate.getId() != null) {
			return siteRepository.findById(aggregate.getId());
		}
		else if (aggregate.getName() != null) {
			return siteRepository.findByName(aggregate.getName());
		}
        throw new NoSuchElementException("could not find site, not enough information to resolve Site to find");
    }

    @Override
    public Set<Site> all() {
		Set<Site> siteSet = new HashSet<>();
		siteRepository.findAll().forEach(siteSet::add);
		return siteSet;
    }

    @Override
    public long count(Predicate predicate) {
		return doCount(predicate, siteRepository.findAll());
    }

    @Override
	@Transactional
    public void delete(Site aggregate) {
		if (aggregate == null || aggregate.getId() == null) {
			throw new IllegalArgumentException();
		}
		ps.deleteByOwner(aggregate);
		siteRepository.delete(siteRepository.findById(aggregate.getId()).orElseThrow(() -> new NoSuchElementException()));
    }

    @Override
	@Transactional
    public void delete(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("id cannot be null");
		}
		siteRepository.deleteById(id);
    }

    @Override
	@Transactional
    public void delete(Client client) {
		if (client == null || client.getId() == null) {
			throw new IllegalArgumentException("owner id must not be null");
		}
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
	@Transactional
    public Site rename(Long id, String newName) {
        Site site = siteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Site with id: " + id + " not found"));
        site.setName(newName);
        return siteRepository.save(site);
    }

    @Override
    public Set<Site> findByOwner(Long ownerId) {
		return siteRepository.findByOwnerId(ownerId).get();
    }

}
