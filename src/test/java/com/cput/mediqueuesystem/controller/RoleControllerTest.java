package com.cput.mediqueuesystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.factory.RoleFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class RoleControllerTest {

    private static Role role;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/role";

    @BeforeAll
    public static void setUp() {
        role = RoleFactory.createRole("TR001", "TestControllerRole");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        Role created = this.restTemplate.postForObject(url, role, Role.class);
        assertNotNull(created);
        assertEquals(role.getRoleId(), created.getRoleId());
        role = created;
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + role.getRoleId();
        ResponseEntity<Role> response = restTemplate.getForEntity(url, Role.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Role updatedRole = new Role.Builder().copy(role)
                .setRoleName("Senior Doctor")
                .build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedRole);
        ResponseEntity<Role> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedRole.getRoleId(), Role.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + role.getRoleId();
        this.restTemplate.delete(url);
        ResponseEntity<Role> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + role.getRoleId(), Role.class);
        assertNull(response.getBody());
        System.out.println("Role deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Role[]> response = this.restTemplate.getForEntity(url, Role[].class);
        Role[] roles = response.getBody();
        assertNotNull(roles);
        System.out.println("Get All: ");
        for (Role r : roles) {
            System.out.println(r);
        }
    }
}
