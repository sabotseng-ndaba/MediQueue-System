package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordFactoryTest {

    @Test
    void createMedicalRecord() {
        Role role = RoleFactory.createRole("R001", "Patient");
        Role docRole = RoleFactory.createRole("R002", "Doctor");
        Department department = DepartmentFactory.createDepartment("D001", "General", "General");

        Patient patient = PatientFactory.createPatient(
                "P001", "Charmaine", "Dlamini",
                "charmaine@gmail.com", "Password123",
                "0731234567", true, LocalDateTime.now(), role,
                "9901011234567", LocalDate.of(1999, 1, 1),
                "Female", "123 Main St", null, null);

        Staff doctor = StaffFactory.createStaff(
                "S001", "Imaan", "Achmat",
                "imaan@gmail.com", "Password123",
                "0731234568", true, LocalDateTime.now(), docRole,
                department, "Doctor");

        MedicalRecord record = MedicalRecordFactory.createMedicalRecord(
                "MR001", patient, doctor,
                "Common cold", "Rest and fluids",
                LocalDate.of(2026, 6, 25));
        assertNotNull(record);
        assertEquals("MR001", record.getRecordId());
        System.out.println(record);
    }
}
