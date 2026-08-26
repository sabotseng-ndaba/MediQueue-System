package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class VisitFactoryTest {

    @Test
    void createVisit() {
        Role role = RoleFactory.createRole("R001", "Patient");
        Department department = DepartmentFactory.createDepartment("D001", "General", "General");
        Role docRole = RoleFactory.createRole("R002", "Doctor");

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

        Appointment appointment = AppointmentFactory.createAppointment(
                "A001", patient, doctor,
                LocalDate.of(2026, 6, 25), LocalTime.of(10, 30),
                "walk-in", "pending", doctor);

        Visit visit = VisitFactory.createVisit(
                "V001", patient, appointment,
                LocalDate.of(2026, 6, 25), LocalTime.of(10, 30),
                LocalTime.of(11, 30), "completed");
        assertNotNull(visit);
        assertEquals("V001", visit.getVisitId());
        System.out.println(visit);
    }
}
