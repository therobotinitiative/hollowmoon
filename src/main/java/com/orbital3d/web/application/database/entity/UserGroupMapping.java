package com.orbital3d.web.application.database.entity;

import java.util.Objects;

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

    /**
     * JPA
     */
    public UserGroupMapping() {
    }

    /**
     * @param userId
     * @param groupId
     */
    public UserGroupMapping(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserGroupMapping{");
        sb.append("userId=").append(userId);
        sb.append(", groupId=").append(groupId);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.userId);
        hash = 89 * hash + Objects.hashCode(this.groupId);
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
        final UserGroupMapping other = (UserGroupMapping) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return Objects.equals(this.groupId, other.groupId);
    }


}
