package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.domain.SymptomsAnalysis;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SymptomsAnalysisFactoryTest {

    @Test
    void createSymptomsAnalysis() {
        Role role = RoleFactory.createRole("R001", "Patient");

        Patient patient = PatientFactory.createPatient(
                "P001", "Charmaine", "Dlamini",
                "charmaine@gmail.com", "Password123",
                "0731234567", true, LocalDateTime.now(), role,
                "9901011234567", LocalDate.of(1999, 1, 1),
                "Female", "123 Main St", null, null);

        SymptomsAnalysis analysis = SymptomsAnalysisFactory.createSymptomsAnalysis(
                "SA001", patient,
                "Headache and fever for 2 days",
                "Common cold, Flu",
                "Headache, Fever, Fatigue",
                0.85, LocalDateTime.now());
        assertNotNull(analysis);
        assertEquals("SA001", analysis.getAnalysisId());
        System.out.println(analysis);
    }
}
