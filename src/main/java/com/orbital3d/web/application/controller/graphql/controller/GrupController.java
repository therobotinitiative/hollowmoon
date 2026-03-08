package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.orbital3d.web.application.database.entity.Group;
import com.orbital3d.web.application.service.GroupService;
import com.orbital3d.web.application.service.NamedServiceCrud;

@Controller
public class GrupController extends AbstractParentGraphQLController<Group, Long> {
    @Autowired
    private GroupService groupService;

    @Override
    protected NamedServiceCrud<Group, Long, String> getService() {
        return groupService;
    }

    @Override
    protected Group getAggregate(Long ownerId, String name) {
        return Group.of(name);
    }

    @QueryMapping
    public List<Group> allGroups() {
        return new ArrayList<>(getService().all());
    }

    @QueryMapping
    public Group groupById(@Argument Long groupId) {
        return this.find(groupId);
    }

    @MutationMapping
    public Group addGroup(@Argument String groupName) {
        return this.addAggregate(groupName, null);
    }

    @MutationMapping
    public Group renameGroup(@Argument Long groupId, @Argument String groupName) {
        return this.rename(groupId, groupName);
    }

    @MutationMapping
    public boolean removeGroup(@Argument Long groupId) {
        this.remove(groupId);
        return true;
    }
}
