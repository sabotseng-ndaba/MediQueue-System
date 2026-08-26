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
class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService medicalRecordService;

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

    MedicalRecord medicalRecord = MedicalRecordFactory.createMedicalRecord(
            "TMR01", patient, doctor,
            "Common cold", "Rest and fluids",
            LocalDate.of(2026, 6, 25));

    @Test
    void a_create() {
        roleService.create(patientRole);
        roleService.create(docRole);
        departmentService.create(department);
        patientService.create(patient);
        staffService.create(doctor);
        MedicalRecord created = medicalRecordService.create(medicalRecord);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        MedicalRecord read = medicalRecordService.read(medicalRecord.getRecordId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        MedicalRecord updated = new MedicalRecord.Builder().copy(medicalRecord)
                .setDiagnosis("Flu")
                .setNotes("Prescribed medication")
                .build();
        MedicalRecord result = medicalRecordService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        medicalRecordService.delete(medicalRecord.getRecordId());
        MedicalRecord deleted = medicalRecordService.read(medicalRecord.getRecordId());
        assertNull(deleted);
        System.out.println("MedicalRecord deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(medicalRecordService.getAll());
    }
}
