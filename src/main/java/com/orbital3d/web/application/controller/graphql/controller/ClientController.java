package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.orbital3d.web.application.controller.graphql.input.ClientOrderBy;
import com.orbital3d.web.application.controller.graphql.input.SortOrder;
import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.service.ClientCodeService;
import com.orbital3d.web.application.service.ClientService;

/**
 * GraphQL controller for {@link Client} related operations.
 */
@Controller
public class ClientController implements GraphQLCrudController<Client, Long> {
	private final ClientService clientService;
	private final ClientCodeService clientCodeService;

	ClientController(final ClientService clientService, final ClientCodeService clientCodeServce) {
		this.clientService = clientService;
		this.clientCodeService = clientCodeServce;
	}

	/**
	 * Query {@link Client} by id.
	 * 
	 * @param clientId {@Client} id
	 * @return {@Client}
	 */
	@Override
	@QueryMapping(value = "clientById")
	public Client find(@Argument Long clientId) {
		return clientService.find(clientId).get();
	}

	/**
	 * Query {@link Client} by its client code.
	 * 
	 * @param clientCode Client code to find
	 * @return {@Client} with client code
	 */
	@QueryMapping(value = "clientByClientCode")
	Client clientByClientCode(@Argument String clientCode) {
		return clientService.findByClientCode(clientCode);
	}

	/**
	 * Add {@link Client}.
	 * 
	 * @param clientName Name of the {@link Client}
	 * @param Client code of the {@link Client}; If null client code will be generated. {@See ClientCodeService#resolveClientCode}.
	 * @return Stored {@link Client} object
	 */
	@MutationMapping(value = "addClient")
	Client add(@Argument String clientName, @Argument String clientCode) {
		if (clientCode == null) {
			clientCode = clientCodeService.resolveClientCode().clientCode();
		}
		return clientService.add(Client.of(clientName, clientCode));
	}

	/**
	 * Remove {@link Client} ny its clientId.
	 * 
	 * @param clientId Client id of the {@Client} to remove
	 */
	@Override
	@MutationMapping(value = "removeClient")
	public boolean remove(@Argument Long clientId) {
		clientService.delete(clientId);
		return true;
	}

	/**
	 * Rename {@Client}.
	 * 
	 * @param clientName New name of {@Client}
	 * @param clientId Client id of the {@Client} to rename
	 * @return Renamed {@Client}
	 */
	@Override
	@MutationMapping(value = "renameClient")
	public Client rename(@Argument Long clientId, @Argument String clientName) {
		Client client = clientService.find(clientId).get();
		client.setName(clientName);
		return clientService.update(client);
	}

	/**
	 * Change client code of {@Client}.
	 * 
	 * @param clientCode New client code
	 * @param clientId Client id of the {@Client}
	 * @return {@Client} with changed client code
	 */
	@MutationMapping
	Client changeClientCode(@Argument String clientCode, @Argument Long clientId) {
		Client client = clientService.find(clientId).get();
		client.setClientCode(clientCode);
		return clientService.update(client);
	}

	@QueryMapping
	public List<Client> allClients(@Argument ClientOrderBy orderBy) {
		List<Client> clients = new ArrayList<>(clientService.all());
		if (orderBy != null && orderBy.name() != null) {
			Comparator<Client> comparator = Comparator.comparing(Client::getName);
			if (orderBy.name() == SortOrder.DESC) {
				comparator = comparator.reversed();
			}
			clients.sort(comparator);
		}
		return clients;
	}

    @Override
    public Client add(String name, Long ownerId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Client changeOwner(Long id, Long newOwnerId) {
        throw new UnsupportedOperationException("Client does not have an owner.");
    }

	@Override
	public List<Client> findByOwner(Long ownerId) {
		return new ArrayList<>(clientService.findByOwner(ownerId));
	}

}
