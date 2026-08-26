package com.cput.mediqueuesystem.service;

import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.factory.RoleFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    Role role = RoleFactory.createRole("TR001", "TestRoleServiceRole");

    @Test
    void a_create() {
        Role created = roleService.create(role);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        Role read = roleService.read(role.getRoleId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Role updated = new Role.Builder().copy(role)
                .setRoleName("Senior Doctor")
                .build();
        Role result = roleService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        roleService.delete(role.getRoleId());
        Role deleted = roleService.read(role.getRoleId());
        assertNull(deleted);
        System.out.println("Role deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(roleService.getAll());
    }
}
