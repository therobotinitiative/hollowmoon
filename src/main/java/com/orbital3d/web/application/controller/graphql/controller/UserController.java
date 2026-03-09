package com.orbital3d.web.application.controller.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.orbital3d.web.application.database.entity.User;
import com.orbital3d.web.application.service.UserService;
import com.orbital3d.web.security.CryptoTool;

@Controller
public class UserController implements GraphQLCrudController<User, Long> {

    @Autowired
    private UserService userService;

    @Override
    @QueryMapping(value = "userById")
    public User find(Long id) {
        return userService.find(id).get();
    }

    @QueryMapping
    public List<User> allUsers() {
        return new ArrayList<>(userService.all());
    }

    @Override
    @MutationMapping(value = "renameUser")
    public User rename(@Argument Long userId, @Argument String userName) {
        return userService.rename(userId, userName);
    }

    @MutationMapping
    public User addUser(@Argument String userName) {
        User user = User.of(userName);
        user.setPassword(CryptoTool.generateSalt());
        user.setSalt(CryptoTool.generateSalt());
        return userService.add(user);
    }

    @Override
    public User addAggregate(String name, Long ownerId) {
        return addUser(name);
    }

    @Override
    @MutationMapping(value = "removeUser")
    public boolean remove(@Argument Long userId) {
        userService.delete(userId);
        return true;
    }

    @Override
    public User changeOwner(Long id, Long newOwnerId) {
        throw new UnsupportedOperationException("Unimplemented method 'changeOwner'");
    }

    @Override
    public List<User> findByOwner(Long ownerId) {
        throw new UnsupportedOperationException("Unimplemented method 'findByOwner'");
    }
}
