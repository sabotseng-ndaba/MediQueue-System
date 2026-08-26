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
class AppointmentControllerTest {

    private static Appointment appointment;
    private static Patient patient;
    private static Staff doctor;
    private static Role patientRole;
    private static Role docRole;
    private static Department department;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/appointment";

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
    }

    @Test
    void a_create() {
        // Create dependencies first
        this.restTemplate.postForObject("/role/create", patientRole, Role.class);
        this.restTemplate.postForObject("/role/create", docRole, Role.class);
        this.restTemplate.postForObject("/department/create", department, Department.class);
        this.restTemplate.postForObject("/patient/create", patient, Patient.class);
        this.restTemplate.postForObject("/staff/create", doctor, Staff.class);

        String url = BASE_URL + "/create";
        Appointment created = this.restTemplate.postForObject(url, appointment, Appointment.class);
        assertNotNull(created);
        assertEquals(appointment.getAppointmentId(), created.getAppointmentId());
        appointment = created;
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + appointment.getAppointmentId();
        ResponseEntity<Appointment> response = restTemplate.getForEntity(url, Appointment.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Appointment updatedAppt = new Appointment.Builder().copy(appointment)
                .setStatus("confirmed")
                .build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedAppt);
        ResponseEntity<Appointment> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedAppt.getAppointmentId(), Appointment.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + appointment.getAppointmentId();
        this.restTemplate.delete(url);
        ResponseEntity<Appointment> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + appointment.getAppointmentId(), Appointment.class);
        assertNull(response.getBody());
        System.out.println("Appointment deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Appointment[]> response = this.restTemplate.getForEntity(url, Appointment[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (Appointment a : response.getBody()) {
            System.out.println(a);
        }
    }
}
