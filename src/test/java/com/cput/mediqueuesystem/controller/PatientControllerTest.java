package com.cput.mediqueuesystem.controller;

import com.cput.mediqueuesystem.domain.*;
import com.cput.mediqueuesystem.factory.*;
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
class PatientControllerTest {

    private static Patient patient;
    private static Role role;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/patient";

    @BeforeAll
    public static void setUp() {
        role = RoleFactory.createRole("TR001", "TestPatientRole");
        patient = PatientFactory.createPatient(
                "TP001", "Charmaine", "Dlamini",
                "test.charmaine@gmail.com", "Password123",
                "0731234567", true, LocalDateTime.now(), role,
                "9901011234599", LocalDate.of(1999, 1, 1),
                "Female", "123 Main St", "MED001", "Peanuts");
    }

    @Test
    void a_create() {
        // Create role first
        String roleUrl = "/role/create";
        this.restTemplate.postForObject(roleUrl, role, Role.class);

        String url = BASE_URL + "/create";
        Patient created = this.restTemplate.postForObject(url, patient, Patient.class);
        assertNotNull(created);
        assertEquals(patient.getUserId(), created.getUserId());
        patient = created;
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + patient.getUserId();
        ResponseEntity<Patient> response = restTemplate.getForEntity(url, Patient.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Patient.Builder builder = new Patient.Builder();
        builder.copy(patient);
        builder.setPhoneNumber("073 999 9999");
        builder.setAddress("456 New St");
        Patient updatedPatient = builder.build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedPatient);
        ResponseEntity<Patient> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedPatient.getUserId(), Patient.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + patient.getUserId();
        this.restTemplate.delete(url);
        ResponseEntity<Patient> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + patient.getUserId(), Patient.class);
        assertNull(response.getBody());
        System.out.println("Patient deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Patient[]> response = this.restTemplate.getForEntity(url, Patient[].class);
        Patient[] patients = response.getBody();
        assertNotNull(patients);
        System.out.println("Get All: ");
        for (Patient p : patients) {
            System.out.println(p);
        }
    }
}
