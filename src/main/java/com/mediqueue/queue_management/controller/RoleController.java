package com.mediqueue.queue_management.controller;

import com.mediqueue.queue_management.model.Role;
import com.mediqueue.queue_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // GET all roles
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // GET one role by ID
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable int id) {
        return roleService.getRoleById(id);
    }

    // POST create a new role
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    // PUT update a role
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable int id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    // DELETE a role
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
    }
}