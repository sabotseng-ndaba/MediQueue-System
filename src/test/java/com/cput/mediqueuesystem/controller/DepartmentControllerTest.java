package com.cput.mediqueuesystem.controller;

import com.cput.mediqueuesystem.domain.Department;
import com.cput.mediqueuesystem.factory.DepartmentFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class DepartmentControllerTest {

    private static Department department;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/department";

    @BeforeAll
    public static void setUp() {
        department = DepartmentFactory.createDepartment(
                "TD001", "TestCardiology", "Test heart department");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        Department created = this.restTemplate.postForObject(url, department, Department.class);
        assertNotNull(created);
        assertEquals(department.getDepartmentId(), created.getDepartmentId());
        department = created;
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + department.getDepartmentId();
        ResponseEntity<Department> response = restTemplate.getForEntity(url, Department.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Department updatedDept = new Department.Builder().copy(department)
                .setDescription("Updated cardiology description")
                .build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedDept);
        ResponseEntity<Department> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedDept.getDepartmentId(), Department.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + department.getDepartmentId();
        this.restTemplate.delete(url);
        ResponseEntity<Department> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + department.getDepartmentId(), Department.class);
        assertNull(response.getBody());
        System.out.println("Department deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Department[]> response = this.restTemplate.getForEntity(url, Department[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (Department d : response.getBody()) {
            System.out.println(d);
        }
    }
}
