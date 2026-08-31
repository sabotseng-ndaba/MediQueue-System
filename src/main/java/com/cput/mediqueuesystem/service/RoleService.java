package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.repository.RoleRepository;

/*
RoleService.java
RoleService
Author: Charmaine Dlamini
Date: 05 August 2026
 */

@Service
public class RoleService implements IRoleService {

    private final RoleRepository repository;

    @Autowired
    RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    // Saves a new role to the database
    @Override
    public Role create(Role role) {
        return this.repository.save(role);
    }

    // Finds a role by its role ID
    @Override
    public Role read(String roleId) {
        return this.repository.findById(roleId).orElse(null);
    }

    // Updates an existing role in the database
    @Override
    public Role update(Role role) {
        return this.repository.save(role);
    }

    // Deletes a role by its role ID
    @Override
    public boolean delete(String roleId) {
        this.repository.deleteById(roleId);
        return true;
    }

    // Returns a list of all roles
    @Override
    public List<Role> getAll() {
        return this.repository.findAll();
    }
}
