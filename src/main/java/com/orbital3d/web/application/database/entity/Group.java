package com.orbital3d.web.application.database.entity;

import com.orbital3d.web.application.database.entity.annotation.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_group")
public class Group {
    @Id
   	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Identifiable
    private Long id;
    @Identifiable
    @Column(unique=true)
    private String groupName;
    /**
     * For JPA
     */
    public Group() {
    }
    /**
     * @param id
     * @param groupName
     */
    public Group(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public static Group of(Long id, String groupName) {
        return new Group(id, groupName);
    }
    public static Group of(String groupName) {
        return new Group(null, groupName);
    }
}
