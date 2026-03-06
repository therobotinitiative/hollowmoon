package com.orbital3d.web.application.database.entity;

import com.orbital3d.web.application.service.type.ChildAggregate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * JPA entity class for row in pageelement table.
 */
@Entity
@Table(name = "pageelement")
public class PageElement implements ChildAggregate<Page> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Page owner;
	private String elements;

	protected PageElement() {
		// JPA only
	}
	
	private PageElement(Long id, Page owner, String elements) {
		this.id = id;
		this.owner = owner;
		this.elements = elements;
	}

	public Long getId() {
		return id;
	}

	public String getElements() {
		return elements;
	}

    @Override
    public void setOwner(Page owner) {
        this.owner = owner;
    }

    @Override
    public Page getOwner() {
        return owner;
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
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
		PageElement other = (PageElement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PageElement [id=" + id + ", owner=" + owner + ", elements=" + elements + "]";
	}

	public static PageElement of(Page owner) {
		return new PageElement(null, owner, null);
	}

}
