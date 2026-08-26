package com.cput.mediqueuesystem.service;

import com.cput.mediqueuesystem.domain.*;
import com.cput.mediqueuesystem.factory.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private RoleService roleService;

    Role role = RoleFactory.createRole("TR001", "TestPatientRole");

    Patient patient = PatientFactory.createPatient(
            "TP001", "Charmaine", "Dlamini",
            "test.charmaine@gmail.com", "Password123",
            "0731234567", true, LocalDateTime.now(), role,
            "9901011234599", LocalDate.of(1999, 1, 1),
            "Female", "123 Main St", "MED001", "Peanuts");

    @Test
    void a_create() {
        roleService.create(role);
        Patient created = patientService.create(patient);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        Patient read = patientService.read(patient.getUserId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Patient.Builder builder = new Patient.Builder();
        builder.copy(patient);
        builder.setPhoneNumber("073 999 9999");
        builder.setAddress("456 New St");
        Patient updated = builder.build();
        Patient result = patientService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        patientService.delete(patient.getUserId());
        Patient deleted = patientService.read(patient.getUserId());
        assertNull(deleted);
        System.out.println("Patient deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(patientService.getAll());
    }
}
