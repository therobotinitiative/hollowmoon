package com.orbital3d.web.application.service;

import com.orbital3d.web.application.database.entity.Client;

/**
 * Client Service interface. Operations to {@link Client}.
 * Client code field can also be used for identification.
 * 
 * T - Aggregate type {@link Client}
 * U - Id type {@link Long}
 * V - Name type for {@link Client} name field for identification
 */
public interface ClientService extends NamedServiceCrud<Client, Long, String> {
	/**
	 * @param clientCode 
	 * @return {@link Client} matching client code
	 * @throws NoSuchElementException if no client with the given client code exists
	 * @throws IllegalArgumentException if client code is null
	 */
	Client findByClientCode(String clientCode);
}
