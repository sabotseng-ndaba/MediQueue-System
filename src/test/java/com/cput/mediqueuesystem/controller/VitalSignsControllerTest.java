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
class VitalSignsControllerTest {

    private static VitalSigns vitalSigns;
    private static Visit visit;
    private static Patient patient;
    private static Staff doctor;
    private static Appointment appointment;
    private static Role patientRole;
    private static Role docRole;
    private static Department department;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/mediqueue/vital-signs";

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
                null, "in-progress");

        vitalSigns = VitalSignsFactory.createVitalSigns(
                "TVS01", visit,
                "37.5", "120/80", "72", "68",
                doctor, LocalDateTime.now());
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
        this.restTemplate.postForObject("/mediqueue/visit/create", visit, Visit.class);

        String url = BASE_URL + "/create";
        ResponseEntity<VitalSigns> response = this.restTemplate.postForEntity(url, vitalSigns, VitalSigns.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        vitalSigns = response.getBody();
        System.out.println("Created: " + vitalSigns);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + vitalSigns.getVitalId();
        ResponseEntity<VitalSigns> response = restTemplate.getForEntity(url, VitalSigns.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        VitalSigns updated = new VitalSigns.Builder().copy(vitalSigns)
                .setTemperature("36.8")
                .setBloodPressure("118/76")
                .build();
        String url = BASE_URL + "/update";
        ResponseEntity<VitalSigns> response = this.restTemplate.postForEntity(url, updated, VitalSigns.class);
        System.out.println("Updated status: " + response.getStatusCode());
        ResponseEntity<VitalSigns> getResponse = this.restTemplate.getForEntity(BASE_URL + "/read/" + updated.getVitalId(), VitalSigns.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        System.out.println("Updated: " + getResponse.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + vitalSigns.getVitalId();
        this.restTemplate.delete(url);
        ResponseEntity<VitalSigns> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + vitalSigns.getVitalId(), VitalSigns.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("VitalSigns deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<VitalSigns[]> response = this.restTemplate.getForEntity(url, VitalSigns[].class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Get All: ");
        for (VitalSigns vs : response.getBody()) {
            System.out.println(vs);
        }
    }
}
