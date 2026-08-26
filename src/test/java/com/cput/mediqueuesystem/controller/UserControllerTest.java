package com.cput.mediqueuesystem.controller;

import com.cput.mediqueuesystem.domain.User;
import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.factory.RoleFactory;
import com.cput.mediqueuesystem.factory.PatientFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserControllerTest {

    private static Patient patient;
    private static Role role;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/user";

    @BeforeAll
    public static void setUp() {
        role = RoleFactory.createRole("TR001", "TestPatientRole");
        patient = PatientFactory.createPatient(
                "TP001", "Charmaine", "Dlamini",
                "test.charmaine@gmail.com", "Password123",
                "0731234567", true, LocalDateTime.now(), role,
                "9901011234599", LocalDate.of(1999, 1, 1),
                "Female", "123 Main St", null, null);
    }

    @Test
    void a_create() {
        // Create role first
        String roleUrl = "/role/create";
        this.restTemplate.postForObject(roleUrl, role, Role.class);

        String url = BASE_URL + "/create";
        User created = this.restTemplate.postForObject(url, patient, User.class);
        assertNotNull(created);
        assertEquals(patient.getUserId(), created.getUserId());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + patient.getUserId();
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Patient.Builder builder = new Patient.Builder();
        builder.copy(patient);
        builder.setPhoneNumber("073 999 9999");
        Patient updatedPatient = builder.build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedPatient);
        ResponseEntity<User> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedPatient.getUserId(), User.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + patient.getUserId();
        this.restTemplate.delete(url);
        ResponseEntity<User> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + patient.getUserId(), User.class);
        assertNull(response.getBody());
        System.out.println("User deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<User[]> response = this.restTemplate.getForEntity(url, User[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (User u : response.getBody()) {
            System.out.println(u);
        }
    }
}
