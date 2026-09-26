package com.mediqueue.queue_management.service;

import com.mediqueue.queue_management.model.Role;
import com.mediqueue.queue_management.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
//try the interface / create it , should have 2 of these services
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Get one role by ID
    public Role getRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role createRole(Role role) {
        if (roleRepository.findAll().stream().anyMatch(r -> r.getRoleName().equalsIgnoreCase(role.getRoleName()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A role with this name already exists");
        }
        return roleRepository.save(role);
    }

    // Update a role
    public Role updateRole(int id, Role updatedRole) {
        Role existing = roleRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setRoleName(updatedRole.getRoleName());
            return roleRepository.save(existing);
        }
        return null;
    }

    // Delete a role
    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
