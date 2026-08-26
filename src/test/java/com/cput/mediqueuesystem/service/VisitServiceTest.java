package com.cput.mediqueuesystem.service;

import com.cput.mediqueuesystem.domain.*;
import com.cput.mediqueuesystem.factory.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private AppointmentService appointmentService;

    Role patientRole = RoleFactory.createRole("TR001", "TestPatientRole");
    Role docRole = RoleFactory.createRole("TR002", "TestDoctorRole");
    Department department = DepartmentFactory.createDepartment("TD001", "General", "General");

    Patient patient = PatientFactory.createPatient(
            "TP001", "Charmaine", "Dlamini",
            "test.charmaine@gmail.com", "Password123",
            "0731234567", true, LocalDateTime.now(), patientRole,
            "9901011234599", LocalDate.of(1999, 1, 1),
            "Female", "123 Main St", null, null);

    Staff doctor = StaffFactory.createStaff(
            "TS001", "Imaan", "Achmat",
            "test.imaan@gmail.com", "Password123",
            "0731234568", true, LocalDateTime.now(), docRole,
            department, "Doctor");

    Appointment appointment = AppointmentFactory.createAppointment(
            "TA001", patient, doctor,
            LocalDate.of(2026, 6, 25), LocalTime.of(10, 30),
            "walk-in", "pending", doctor);

    Visit visit = VisitFactory.createVisit(
            "TV001", patient, appointment,
            LocalDate.of(2026, 6, 25), LocalTime.of(10, 30),
            LocalTime.of(11, 30), "completed");

    @Test
    void a_create() {
        roleService.create(patientRole);
        roleService.create(docRole);
        departmentService.create(department);
        patientService.create(patient);
        staffService.create(doctor);
        appointmentService.create(appointment);
        Visit created = visitService.create(visit);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        Visit read = visitService.read(visit.getVisitId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Visit updated = new Visit.Builder().copy(visit)
                .setStatus("in-progress")
                .build();
        Visit result = visitService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        visitService.delete(visit.getVisitId());
        Visit deleted = visitService.read(visit.getVisitId());
        assertNull(deleted);
        System.out.println("Visit deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(visitService.getAll());
    }
}
