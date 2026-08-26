package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class QueueEntryFactoryTest {

    @Test
    void createQueueEntry() {
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

        Clinic clinic = ClinicFactory.createClinic(
                "CL001", "Main Clinic", "Building A", "021 555 0100");

        Queue queue = QueueFactory.createQueue(
                "Q001", clinic, LocalDate.of(2026, 6, 25), 30);

        QueueEntry queueEntry = QueueEntryFactory.createQueueEntry(
                "QE001", queue, patient, doctor, null,
                1, "High", "waiting", LocalTime.of(9, 0));
        assertNotNull(queueEntry);
        assertEquals("QE001", queueEntry.getQueueEntryId());
        System.out.println(queueEntry);
    }
}
