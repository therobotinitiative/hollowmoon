package com.orbital3d.web.application.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private byte [] password;
    @Column(nullable = false, unique = true)
    private byte [] salt;

    public User() {
        // Default constructor for JPA
    }

    public User(String userName, byte[] password, byte[] salt) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }

    /**
     * Factory method to create a new User instance.
     * @throws IllegalArgumentException if userName is null
     */
    public static User of(String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("User name cannot be null");
        }
        return new User(userName, null, null);
    }
}