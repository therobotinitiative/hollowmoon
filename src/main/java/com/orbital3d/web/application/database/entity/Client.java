package com.orbital3d.web.application.database.entity;

import java.util.Objects;

import com.orbital3d.web.application.database.entity.annotation.Identifiable;
import com.orbital3d.web.application.service.type.ParentAggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * JPA entity class for row in Client table.
 * Null name, client code or id are considered valid, but two clients with null name, client code or id are considered equal to each other.
 * This is because the client code and name are not considered unique identifiers of a client, but rather the id is the unique identifier.
 * The client code and name are just additional information about the client
 */
@Entity
@Table(name = "client")
public class Client implements ParentAggregate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Identifiable
	private Long id;
	@Column(nullable = false)
	@NotNull
	@Identifiable
	private String name;
	@Column(nullable = false, unique = true)
	@NotNull
	@Identifiable
	private String clientCode;

	protected Client() {
		// JPA only
	}

	private Client(Long id, String name, String clientCode) {
		super();
		this.id = id;
		this.name = name;
		this.clientCode = clientCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public static Client of(String name) {
		return new Client(null, name, null);
	}

	public static Client of(Long ownerId) {
		return new Client(ownerId, null, null);
	}

	public static Client of(String name, String clientCode) {
		return new Client(null, name, clientCode);
	}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.clientCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.clientCode, other.clientCode)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }


}
