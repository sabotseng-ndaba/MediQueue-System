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
class QueueEntryServiceTest {

    @Autowired
    private QueueEntryService queueEntryService;

    @Autowired
    private QueueService queueService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private StaffService staffService;

    Role patientRole = RoleFactory.createRole("TR001", "TestPatientRole");
    Role docRole = RoleFactory.createRole("TR002", "TestDoctorRole");
    Department department = DepartmentFactory.createDepartment("TD001", "General", "General");
    Clinic clinic = ClinicFactory.createClinic("TCL01", "Main Clinic", "Building A", "021 555 0999");

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

    Queue queue = QueueFactory.createQueue("TQ001", clinic, LocalDate.of(2026, 6, 25), 30);

    QueueEntry queueEntry = QueueEntryFactory.createQueueEntry(
            "TQE01", queue, patient, doctor, null,
            1, "High", "waiting", LocalTime.of(9, 0));

    @Test
    void a_create() {
        roleService.create(patientRole);
        roleService.create(docRole);
        departmentService.create(department);
        clinicService.create(clinic);
        patientService.create(patient);
        staffService.create(doctor);
        queueService.create(queue);
        QueueEntry created = queueEntryService.create(queueEntry);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        QueueEntry read = queueEntryService.read(queueEntry.getQueueEntryId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        QueueEntry updated = new QueueEntry.Builder().copy(queueEntry)
                .setStatus("in-progress")
                .build();
        QueueEntry result = queueEntryService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        queueEntryService.delete(queueEntry.getQueueEntryId());
        QueueEntry deleted = queueEntryService.read(queueEntry.getQueueEntryId());
        assertNull(deleted);
        System.out.println("QueueEntry deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(queueEntryService.getAll());
    }
}
