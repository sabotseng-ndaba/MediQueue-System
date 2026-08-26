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
class SymptomsAnalysisControllerTest {

    private static SymptomsAnalysis symptomsAnalysis;
    private static Patient patient;
    private static Role role;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/mediqueue/symptoms-analysis";

    @BeforeAll
    public static void setUp() {
        role = RoleFactory.createRole("TR001", "TestPatientRole");
        patient = PatientFactory.createPatient(
                "TP001", "Charmaine", "Dlamini",
                "test.charmaine@gmail.com", "Password123",
                "0731234567", true, LocalDateTime.now(), role,
                "9901011234599", LocalDate.of(1999, 1, 1),
                "Female", "123 Main St", null, null);

        symptomsAnalysis = SymptomsAnalysisFactory.createSymptomsAnalysis(
                "TSA01", patient,
                "Headache and fever for 2 days",
                "Common cold, Flu",
                "Headache, Fever, Fatigue",
                0.85, LocalDateTime.now());
    }

    @Test
    void a_create() {
        // Create dependencies first
        this.restTemplate.postForObject("/role/create", role, Role.class);
        this.restTemplate.postForObject("/patient/create", patient, Patient.class);

        String url = BASE_URL + "/create";
        ResponseEntity<SymptomsAnalysis> response = this.restTemplate.postForEntity(url, symptomsAnalysis, SymptomsAnalysis.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        symptomsAnalysis = response.getBody();
        System.out.println("Created: " + symptomsAnalysis);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + symptomsAnalysis.getAnalysisId();
        ResponseEntity<SymptomsAnalysis> response = restTemplate.getForEntity(url, SymptomsAnalysis.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        SymptomsAnalysis updated = new SymptomsAnalysis.Builder().copy(symptomsAnalysis)
                .setConfidenceScore(0.95)
                .setPredictedConditions("Common cold")
                .build();
        String url = BASE_URL + "/update";
        ResponseEntity<SymptomsAnalysis> response = this.restTemplate.postForEntity(url, updated, SymptomsAnalysis.class);
        System.out.println("Updated status: " + response.getStatusCode());
        ResponseEntity<SymptomsAnalysis> getResponse = this.restTemplate.getForEntity(BASE_URL + "/read/" + updated.getAnalysisId(), SymptomsAnalysis.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        System.out.println("Updated: " + getResponse.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + symptomsAnalysis.getAnalysisId();
        this.restTemplate.delete(url);
        ResponseEntity<SymptomsAnalysis> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + symptomsAnalysis.getAnalysisId(), SymptomsAnalysis.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("SymptomsAnalysis deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<SymptomsAnalysis[]> response = this.restTemplate.getForEntity(url, SymptomsAnalysis[].class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Get All: ");
        for (SymptomsAnalysis sa : response.getBody()) {
            System.out.println(sa);
        }
    }
}
