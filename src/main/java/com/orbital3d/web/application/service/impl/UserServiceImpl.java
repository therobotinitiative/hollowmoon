package com.orbital3d.web.application.service.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.web.application.database.entity.User;
import com.orbital3d.web.application.database.repository.UserRepository;
import com.orbital3d.web.application.service.UserService;

@Service
public class UserServiceImpl extends AbstractNamedServiceCrud<User> implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> find(String name) {
        return userRepository.findByUserName(name);
  }

    @Override
    public User rename(Long id, String newName) {
        User u = find(id).orElseThrow(() -> new NoSuchElementException("Could not find User by id"));
        u.setUserName(newName);
        return userRepository.save(u);
    }

    @Override
    public User add(User aggregate) {
        if (aggregate == null) {
            throw new IllegalArgumentException("Aggretagate must not be null");
        }
        if (aggregate.getUserName()== null) {
            throw new IllegalArgumentException("User namecannot be null");
        }
        return userRepository.save(aggregate);
    }

    @Override
    public User update(User aggregate) {
        if (aggregate == null) {
            throw new IllegalArgumentException("Aggretagate must not be null");
        }
        if (aggregate.getUserName() == null) {
            throw new IllegalArgumentException("User namecannot be null");
        }
        if (aggregate.getId() == null) {
            throw new IllegalArgumentException("User must have id when updating");
        }
        return userRepository.save(aggregate);
    }

    @Override
    public Optional<User> find(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> find(User aggregate) {
        if (aggregate == null) {
            throw new IllegalArgumentException("Aggretagate must not be null");
        }
        if (aggregate.getUserName() == null) {
            throw new IllegalArgumentException("User namecannot be null");
        }
        if (aggregate.getId() == null) {
            throw new IllegalArgumentException("User must have id whn finding");
        }
        return userRepository.findById(aggregate.getId());
    }

    @Override
    public Set<User> all() {
        Set<User> users = new  HashSet<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public long count(Predicate<User> predicate) {
        return doCount(predicate, userRepository.findAll());
    }

    @Override
    public void delete(User aggregate) {
        if (aggregate == null) {
            throw new IllegalArgumentException("Aggretagate must not be null");
        }
        if (aggregate.getId() == null) {
            throw new IllegalArgumentException("User must have id when deleting");
        }
        delete(aggregate.getId());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Set<User> findByOwner(Long ownerId) {
        throw new UnsupportedOperationException("User does not have wner");
    }

}
