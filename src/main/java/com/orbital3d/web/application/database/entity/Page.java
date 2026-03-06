package com.orbital3d.web.application.database.entity;

import java.util.Objects;

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
 * JPA entity class for row in page table.
 * Null name or owner are considered valid, but two pages with null name or owner are considered equal to each other.
 * This is because the name and owner are not considered unique identifiers of a page, but rather the id is the unique identifier.
 */
@Entity
@Table(name = "page")
public class Page implements ParentAggregate, ChildAggregate<Site> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Site owner;

	protected Page() {
		// JPA only
	}
	
	private Page(Long id, String name, Site owner) {
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
	public Site getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Site owner) {
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
	public String toString() {
		return "Page [id=" + id + ", name=" + name + ", owner=" + owner + "]";
	}

	public static Page of(Site owner, String name) {
		return new Page(null, name, owner);
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
        final Page other = (Page) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.owner, other.owner);
    }
}
