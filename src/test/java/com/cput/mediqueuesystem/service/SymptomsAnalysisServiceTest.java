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
class SymptomsAnalysisServiceTest {

    @Autowired
    private SymptomsAnalysisService symptomsAnalysisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PatientService patientService;

    Role role = RoleFactory.createRole("TR001", "TestPatientRole");

    Patient patient = PatientFactory.createPatient(
            "TP001", "Charmaine", "Dlamini",
            "test.charmaine@gmail.com", "Password123",
            "0731234567", true, LocalDateTime.now(), role,
            "9901011234599", LocalDate.of(1999, 1, 1),
            "Female", "123 Main St", null, null);

    SymptomsAnalysis symptomsAnalysis = SymptomsAnalysisFactory.createSymptomsAnalysis(
            "TSA01", patient,
            "Headache and fever for 2 days",
            "Common cold, Flu",
            "Headache, Fever, Fatigue",
            0.85, LocalDateTime.now());

    @Test
    void a_create() {
        roleService.create(role);
        patientService.create(patient);
        SymptomsAnalysis created = symptomsAnalysisService.create(symptomsAnalysis);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        SymptomsAnalysis read = symptomsAnalysisService.read(symptomsAnalysis.getAnalysisId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        SymptomsAnalysis updated = new SymptomsAnalysis.Builder().copy(symptomsAnalysis)
                .setConfidenceScore(0.95)
                .setPredictedConditions("Common cold")
                .build();
        SymptomsAnalysis result = symptomsAnalysisService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        symptomsAnalysisService.delete(symptomsAnalysis.getAnalysisId());
        SymptomsAnalysis deleted = symptomsAnalysisService.read(symptomsAnalysis.getAnalysisId());
        assertNull(deleted);
        System.out.println("SymptomsAnalysis deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(symptomsAnalysisService.getAll());
    }
}
