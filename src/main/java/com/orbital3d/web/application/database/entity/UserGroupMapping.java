package com.orbital3d.web.application.database.entity;

import com.orbital3d.web.application.database.entity.composite.UserGroupMappingId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(UserGroupMappingId.class)
public class UserGroupMapping {
    @Id
    private Long userId;
    @Id
    private Long groupId;
}
