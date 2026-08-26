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
class MedicalRecordControllerTest {

    private static MedicalRecord medicalRecord;
    private static Patient patient;
    private static Staff doctor;
    private static Role patientRole;
    private static Role docRole;
    private static Department department;

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "/medical-record";

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

        medicalRecord = MedicalRecordFactory.createMedicalRecord(
                "TMR01", patient, doctor,
                "Common cold", "Rest and fluids",
                LocalDate.of(2026, 6, 25));
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
        MedicalRecord created = this.restTemplate.postForObject(url, medicalRecord, MedicalRecord.class);
        assertNotNull(created);
        assertEquals(medicalRecord.getRecordId(), created.getRecordId());
        medicalRecord = created;
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + medicalRecord.getRecordId();
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(url, MedicalRecord.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        MedicalRecord updatedRecord = new MedicalRecord.Builder().copy(medicalRecord)
                .setDiagnosis("Flu")
                .setNotes("Prescribed medication")
                .build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedRecord);
        ResponseEntity<MedicalRecord> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedRecord.getRecordId(), MedicalRecord.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = BASE_URL + "/delete/" + medicalRecord.getRecordId();
        this.restTemplate.delete(url);
        ResponseEntity<MedicalRecord> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + medicalRecord.getRecordId(), MedicalRecord.class);
        assertNull(response.getBody());
        System.out.println("MedicalRecord deleted: true");
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<MedicalRecord[]> response = this.restTemplate.getForEntity(url, MedicalRecord[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: ");
        for (MedicalRecord mr : response.getBody()) {
            System.out.println(mr);
        }
    }
}
