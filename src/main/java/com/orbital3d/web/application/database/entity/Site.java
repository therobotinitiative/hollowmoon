package com.orbital3d.web.application.database.entity;


import com.orbital3d.web.application.service.type.ChildAggregate;
import com.orbital3d.web.application.service.type.ParentAggregate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * JPA entity class for row in site table.
 */
@Entity
@Table(name = "site")
public class Site implements ChildAggregate<Client>, ParentAggregate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Client owner;

	protected Site() {
		// JPA only
	}
	
	private Site(Long id, String name, Client owner) {
		this.id = id;
		this.name = name;
		this.owner = owner;
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

	@Override
	public Client getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Client owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Site other = (Site) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", name=" + name + "]";
	}

	public static Site of(Client owner, String name) {
		return new Site(null, name, owner);
	}

	public static Site of(Client owner, Long id) {
		return new Site(id, null, owner);
	}

	public static Site of(Long id) {
		return new Site(id, null, null);
	}

}
