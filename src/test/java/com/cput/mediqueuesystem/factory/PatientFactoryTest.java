package com.cput.mediqueuesystem.factory;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.domain.Role;

class PatientFactoryTest {

    @Test
    void createPatient() {
        Role role = RoleFactory.createRole("R001", "Patient");

        Patient patient = PatientFactory.createPatient(
                "P001", "Charmaine", "Dlamini",
                "charmaine@gmail.com", "Password123",
                "0731234567", true, LocalDateTime.now(), role,
                "9901011234567", LocalDate.of(1999, 1, 1),
                "Female", "123 Main St", "MED001", "Peanuts");
        assertNotNull(patient);
        assertEquals("P001", patient.getUserId());
        assertEquals("Charmaine", patient.getFirstName());
        System.out.println(patient);
    }
}
