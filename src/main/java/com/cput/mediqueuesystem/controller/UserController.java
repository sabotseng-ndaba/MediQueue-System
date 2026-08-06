package com.cput.mediqueuesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cput.mediqueuesystem.domain.User;
import com.cput.mediqueuesystem.service.UserService;

/*
UserController.java
User Controller
Author: Charmaine Dlamini
Date: 05 August 2026
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //Sending or storing info
    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userService.create(user);
    }
    //Retrieving a specific user by its ID
    @GetMapping("/read/{userId}")
    public User read(@PathVariable("userId") String userId) {
        return userService.read(userId);
    }
        
    //Updating an existing user
    @PutMapping("/update")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }
    //Deleting a user by its ID
    @DeleteMapping("/delete/{userId}")
    public void delete(@PathVariable String userId) {
        userService.delete(userId);
    }
    //Retrieving all users
    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

}
