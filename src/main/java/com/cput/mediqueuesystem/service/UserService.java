package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.User;
import com.cput.mediqueuesystem.repository.UserRepository;

/*
UserService.java
UserService
Author: Charmaine Dlamini
Date: 05 August 2026
 */

@Service
public class UserService implements IUserService {

    private final UserRepository repository;

    @Autowired
    UserService(UserRepository repository) {
        this.repository = repository;
    }

    // Saves a new user to the database
    @Override
    public User create(User user) {
        return this.repository.save(user);
    }

    // Finds a user by their user ID
    @Override
    public User read(String userId) {
        return this.repository.findById(userId).orElse(null);
    }

    // Updates an existing user in the database
    @Override
    public User update(User user) {
        return this.repository.save(user);
    }

    // Deletes a user by their user ID
    @Override
    public boolean delete(String userId) {
        this.repository.deleteById(userId);
        return true;
    }

    // Returns a list of all users
    @Override
    public List<User> getAll() {
        return this.repository.findAll();
    }
}
