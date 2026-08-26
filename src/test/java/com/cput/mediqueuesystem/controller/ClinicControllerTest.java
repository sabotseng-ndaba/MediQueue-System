package com.cput.mediqueuesystem.controller;

import com.cput.mediqueuesystem.domain.Clinic;
import com.cput.mediqueuesystem.factory.ClinicFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ClinicControllerTest {

    private static Clinic clinic;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/clinic";

    @BeforeAll
    public static void setUp() {
        clinic = ClinicFactory.createClinic(
                "TCL01", "Main Clinic", "Building A, Floor 1", "021 555 0999");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        Clinic created = this.restTemplate.postForObject(url, clinic, Clinic.class);
        assertNotNull(created);
        assertEquals(clinic.getClinicId(), created.getClinicId());
        clinic = created;
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + clinic.getClinicId();
        ResponseEntity<Clinic> response = restTemplate.getForEntity(url, Clinic.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Clinic updatedClinic = new Clinic.Builder().copy(clinic)
                .setLocation("Building B, Floor 2")
                .build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedClinic);
        ResponseEntity<Clinic> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedClinic.getClinicId(), Clinic.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + clinic.getClinicId();
        this.restTemplate.delete(url);
        ResponseEntity<Clinic> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + clinic.getClinicId(), Clinic.class);
        assertNull(response.getBody());
        System.out.println("Clinic deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Clinic[]> response = this.restTemplate.getForEntity(url, Clinic[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (Clinic c : response.getBody()) {
            System.out.println(c);
        }
    }
}
