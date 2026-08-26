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
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class VisitControllerTest {

    private static Visit visit;
    private static Patient patient;
    private static Staff doctor;
    private static Appointment appointment;
    private static Role patientRole;
    private static Role docRole;
    private static Department department;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/mediqueue/visit";

    @BeforeAll
    public static void setUp() {
        patientRole = RoleFactory.createRole("TR001", "TestPatientRole");
        docRole = RoleFactory.createRole("TR002", "TestDoctorRole");
        department = DepartmentFactory.createDepartment("TD001", "TestGeneral", "TestGeneral");

        patient = PatientFactory.createPatient(
                "TP001", "Charmaine", "Dlamini",
                "test.charmaine@gmail.com", "Password123",
                "0731234567", true, LocalDateTime.now(), patientRole,
                "9901011234599", LocalDate.of(1999, 1, 1),
                "Female", "123 Main St", null, null);

        doctor = StaffFactory.createStaff(
                "TS001", "Imaan", "Achmat",
                "test.imaan@gmail.com", "Password123",
                "0731234568", true, LocalDateTime.now(), docRole,
                department, "Doctor");

        appointment = AppointmentFactory.createAppointment(
                "TA001", patient, doctor,
                LocalDate.of(2026, 6, 25), LocalTime.of(10, 30),
                "walk-in", "pending", doctor);

        visit = VisitFactory.createVisit(
                "TV001", patient, appointment,
                LocalDate.of(2026, 6, 25), LocalTime.of(10, 30),
                LocalTime.of(11, 30), "completed");
    }

    @Test
    void a_create() {
        // Create dependencies first
        this.restTemplate.postForObject("/role/create", patientRole, Role.class);
        this.restTemplate.postForObject("/role/create", docRole, Role.class);
        this.restTemplate.postForObject("/department/create", department, Department.class);
        this.restTemplate.postForObject("/patient/create", patient, Patient.class);
        this.restTemplate.postForObject("/staff/create", doctor, Staff.class);
        this.restTemplate.postForObject("/appointment/create", appointment, Appointment.class);

        String url = BASE_URL + "/create";
        ResponseEntity<Visit> response = this.restTemplate.postForEntity(url, visit, Visit.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        visit = response.getBody();
        System.out.println("Created: " + visit);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + visit.getVisitId();
        ResponseEntity<Visit> response = restTemplate.getForEntity(url, Visit.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Visit updatedVisit = new Visit.Builder().copy(visit)
                .setStatus("in-progress")
                .build();
        String url = BASE_URL + "/update";
        ResponseEntity<Visit> response = this.restTemplate.postForEntity(url, updatedVisit, Visit.class);
        System.out.println("Updated status: " + response.getStatusCode());
        ResponseEntity<Visit> getResponse = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedVisit.getVisitId(), Visit.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        System.out.println("Updated: " + getResponse.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + visit.getVisitId();
        this.restTemplate.delete(url);
        ResponseEntity<Visit> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + visit.getVisitId(), Visit.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Visit deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Visit[]> response = this.restTemplate.getForEntity(url, Visit[].class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Get All: ");
        for (Visit v : response.getBody()) {
            System.out.println(v);
        }
    }
}
