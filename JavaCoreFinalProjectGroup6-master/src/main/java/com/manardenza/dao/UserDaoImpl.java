package com.manardenza.dao;

import com.manardenza.entity.User;

import java.io.File;

public class UserDaoImpl extends AbstractDao<User> {

    public UserDaoImpl(File databaseFile) {
        super(databaseFile);
    }

    public User getUserByName(String firstName, String lastName) {
        return getAll().stream()
                .filter(user -> user.getFirstName().equals(firstName))
                .filter(user -> user.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }
}
