package com.orbital3d.web.application.database.entity.composite;

import java.io.Serializable;

public class UserGroupMappingId implements Serializable {
    private Long userId;
    private Long groupId;

    public UserGroupMappingId() {
        // Default constructor for JPA
    }

    public UserGroupMappingId(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupMappingId that = (UserGroupMappingId) o;

        if (!userId.equals(that.userId)) return false;
        return groupId.equals(that.groupId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + groupId.hashCode();
        return result;
    }

}
