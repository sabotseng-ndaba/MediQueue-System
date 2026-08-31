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

import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.service.RoleService;

/*
RoleController.java
Role Controller
Author: Charmaine Dlamini
Date: 05 August 2026
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    //Sending or storing info
    @PostMapping("/create")
    public Role create(@RequestBody Role role) {
        return roleService.create(role);
    }
    //Retrieving a specific role by its ID
    @GetMapping("/read/{roleId}")
    public Role read(@PathVariable("roleId") String roleId) {
        return roleService.read(roleId);
    }
        
    //Updating an existing role
    @PutMapping("/update")
    public Role update(@RequestBody Role role) {
        return roleService.update(role);
    }
    //Deleting a role by its ID
    @DeleteMapping("/delete/{roleId}")
    public void delete(@PathVariable String roleId) {
        roleService.delete(roleId);
    }
    //Retrieving all roles
    @GetMapping("/getAll")
    public List<Role> getAll() {
        return roleService.getAll();
    }

}
