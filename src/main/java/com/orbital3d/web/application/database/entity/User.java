package com.orbital3d.web.application.database.entity;

import com.orbital3d.web.application.service.type.ParentAggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User implements ParentAggregate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private byte [] password;
    @Column(nullable = false)
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
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * @return the salt
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(byte[] salt) {
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