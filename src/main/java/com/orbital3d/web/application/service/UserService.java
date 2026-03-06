package com.orbital3d.web.application.service;

import com.orbital3d.web.application.database.entity.User;

/**
 * User service interface to perform operations on {@link User}.
 * T - aggregate type {@link User}
 * U - aggregate id type {@link Long}
 * V - aggregate named id type {@link String}
 */
public interface UserService extends NamedServiceCrud<User, Long, String> {
    // nothing so far...
}
