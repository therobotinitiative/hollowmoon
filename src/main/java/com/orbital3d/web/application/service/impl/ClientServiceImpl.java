package com.orbital3d.web.application.service.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.repository.ClientReposity;
import com.orbital3d.web.application.service.ClientService;
import com.orbital3d.web.application.service.SiteService;

/**
 * JPA implementation of {@link ClientService}.
 */
@Service
public class ClientServiceImpl extends AbstractNamedServiceCrud<Client> implements ClientService {
	private static final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientReposity clientRepository;

	// will be used later
	@SuppressWarnings("unused")
	@Autowired
	private SiteService siteService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Client add(Client aggregate) {
		if (aggregate == null || aggregate.getClientCode() == null || aggregate.getName() == null) {
			throw new IllegalArgumentException("aggregate cannot be null");
		}
		return clientRepository.save(aggregate);
	}

	@Override
	public Set<Client> all() {
		Set<Client> cset = new HashSet<>();
		clientRepository.findAll().forEach(cset::add);
		return cset;
	}

	@Override
	public Optional<Client> find(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		Optional<Client> c = clientRepository.findById(id);
		if (c.isEmpty()){
			throw new NoSuchElementException("Client with id " + id + " not found");
		}
		LOG.trace("aggregate by id {}, found: {}", id, c.orElse(null));
		return c;
	}

	@Override
	public Optional<Client> find(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		Optional<Client> c = clientRepository.findByName(name);
		if (!c.isPresent()) {
			throw new NoSuchElementException("aggregate with name " + name + " not found");
		}
		LOG.trace("aggregate by id {}, found: {}", name, c.get());
		return c;
	}

	@Override
	public Client findByClientCode(String clientCode) {
		if (clientCode == null) {
			throw new IllegalArgumentException("client code cannot be null");
		}
		Optional<Client> c = clientRepository.findByClientCode(clientCode);
		if (!c.isPresent()) {
			throw new NoSuchElementException("aggregate with client code " + clientCode + " not found");
		}
		return c.get();
	}

    @Override
    @Transactional
    public void delete(Long id) {
		LOG.info("Removing aggregate with id {}", id);
		Client c = find(id).orElseThrow(() -> new NoSuchElementException("aggregate with id " + id + " not found"));
		//siteService.removeSites(c);
		clientRepository.deleteById(id);
    }

	@Override
	@Transactional
	public Client update(Client aggregate) {
		if (aggregate == null || aggregate.getClientCode() == null || aggregate.getName() == null) {
			throw new IllegalArgumentException("aggregate cannot be null");
		}
		clientRepository.findById(aggregate.getId()).orElseThrow(() -> new NoSuchElementException("aggregate with id " + aggregate.getId() + " not found"));
		LOG.info("Updating aggregate {}", aggregate);
		return clientRepository.save(aggregate);
	}

    @Override
    public Optional<Client> find(Client aggregate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long count(Predicate<Client> predicate) {
		return doCount(predicate, clientRepository.findAll());
    }

    @Override
    public void delete(Client aggregate) {
		if (aggregate == null || aggregate.getId() == null) {
			throw new IllegalArgumentException();
		}
		clientRepository.delete(aggregate);
    }

    @Override
    public Client rename(Long id, String newName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Client> findByOwner(Long ownerId) {
		return StreamSupport.stream(clientRepository.findAll().spliterator(), false)
				.filter(c -> true) // Clients do not have an owner, so we return all clients
				.collect(Collectors.toSet());
    }
}
