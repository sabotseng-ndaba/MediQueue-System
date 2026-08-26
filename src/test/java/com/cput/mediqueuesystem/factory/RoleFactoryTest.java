package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoleFactoryTest {

    @Test
    void createRole() {
        Role role = RoleFactory.createRole("R001", "Doctor");
        assertNotNull(role);
        assertEquals("R001", role.getRoleId());
        assertEquals("Doctor", role.getRoleName());
        System.out.println(role);
    }
}
