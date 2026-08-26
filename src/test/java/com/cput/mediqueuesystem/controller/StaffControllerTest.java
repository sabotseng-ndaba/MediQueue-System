package com.cput.mediqueuesystem.controller;

import com.cput.mediqueuesystem.domain.*;
import com.cput.mediqueuesystem.factory.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class StaffControllerTest {

    private static Staff staff;
    private static Role role;
    private static Department department;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/staff";

    @BeforeAll
    public static void setUp() {
        role = RoleFactory.createRole("TR002", "TestDoctorRole");
        department = DepartmentFactory.createDepartment("TD001", "TestCardiology", "Heart department");
        staff = StaffFactory.createStaff(
                "TS001", "Imaan", "Achmat",
                "test.imaan@gmail.com", "Password123",
                "0731234568", true, LocalDateTime.now(), role,
                department, "Doctor");
    }

    @Test
    void a_create() {
        // Create dependencies first
        String roleUrl = "/role/create";
        this.restTemplate.postForObject(roleUrl, role, Role.class);
        String deptUrl = "/department/create";
        this.restTemplate.postForObject(deptUrl, department, Department.class);

        String url = BASE_URL + "/create";
        Staff created = this.restTemplate.postForObject(url, staff, Staff.class);
        assertNotNull(created);
        assertEquals(staff.getUserId(), created.getUserId());
        staff = created;
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + staff.getUserId();
        ResponseEntity<Staff> response = restTemplate.getForEntity(url, Staff.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Staff.Builder builder = new Staff.Builder();
        builder.copy(staff);
        builder.setPosition("Senior Doctor");
        Staff updatedStaff = builder.build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedStaff);
        ResponseEntity<Staff> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedStaff.getUserId(), Staff.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + staff.getUserId();
        this.restTemplate.delete(url);
        ResponseEntity<Staff> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + staff.getUserId(), Staff.class);
        assertNull(response.getBody());
        System.out.println("Staff deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Staff[]> response = this.restTemplate.getForEntity(url, Staff[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (Staff s : response.getBody()) {
            System.out.println(s);
        }
    }
}
