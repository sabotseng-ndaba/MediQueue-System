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
class QueueEntryControllerTest {

    private static QueueEntry queueEntry;
    private static Patient patient;
    private static Staff doctor;
    private static Clinic clinic;
    private static Queue queue;
    private static Role patientRole;
    private static Role docRole;
    private static Department department;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/mediqueue/queue-entry";

    @BeforeAll
    public static void setUp() {
        patientRole = RoleFactory.createRole("TR001", "TestPatientRole");
        docRole = RoleFactory.createRole("TR002", "TestDoctorRole");
        department = DepartmentFactory.createDepartment("TD001", "TestGeneral", "TestGeneral");
        clinic = ClinicFactory.createClinic("TCL01", "Main Clinic", "Building A", "021 555 0999");

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

        queue = QueueFactory.createQueue("TQ001", clinic, LocalDate.of(2026, 6, 25), 30);

        queueEntry = QueueEntryFactory.createQueueEntry(
                "TQE01", queue, patient, doctor, null,
                1, "High", "waiting", LocalTime.of(9, 0));
    }

    @Test
    void a_create() {
        // Create dependencies first
        this.restTemplate.postForObject("/role/create", patientRole, Role.class);
        this.restTemplate.postForObject("/role/create", docRole, Role.class);
        this.restTemplate.postForObject("/department/create", department, Department.class);
        this.restTemplate.postForObject("/clinic/create", clinic, Clinic.class);
        this.restTemplate.postForObject("/patient/create", patient, Patient.class);
        this.restTemplate.postForObject("/staff/create", doctor, Staff.class);
        this.restTemplate.postForObject("/mediqueue/queue/create", queue, Queue.class);

        String url = BASE_URL + "/create";
        ResponseEntity<QueueEntry> response = this.restTemplate.postForEntity(url, queueEntry, QueueEntry.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        queueEntry = response.getBody();
        System.out.println("Created: " + queueEntry);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + queueEntry.getQueueEntryId();
        ResponseEntity<QueueEntry> response = restTemplate.getForEntity(url, QueueEntry.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        QueueEntry updatedEntry = new QueueEntry.Builder().copy(queueEntry)
                .setStatus("in-progress")
                .build();
        String url = BASE_URL + "/update";
        ResponseEntity<QueueEntry> response = this.restTemplate.postForEntity(url, updatedEntry, QueueEntry.class);
        System.out.println("Updated status: " + response.getStatusCode());
        ResponseEntity<QueueEntry> getResponse = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedEntry.getQueueEntryId(), QueueEntry.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        System.out.println("Updated: " + getResponse.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + queueEntry.getQueueEntryId();
        this.restTemplate.delete(url);
        ResponseEntity<QueueEntry> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + queueEntry.getQueueEntryId(), QueueEntry.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("QueueEntry deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<QueueEntry[]> response = this.restTemplate.getForEntity(url, QueueEntry[].class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Get All: ");
        for (QueueEntry qe : response.getBody()) {
            System.out.println(qe);
        }
    }
}
