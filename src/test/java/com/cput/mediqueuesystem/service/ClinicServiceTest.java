package com.cput.mediqueuesystem.service;

import com.cput.mediqueuesystem.domain.Clinic;
import com.cput.mediqueuesystem.factory.ClinicFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ClinicServiceTest {

    @Autowired
    private ClinicService clinicService;

    Clinic clinic = ClinicFactory.createClinic(
            "TCL01", "Main Clinic", "Building A, Floor 1", "021 555 0999");

    @Test
    void a_create() {
        Clinic created = clinicService.create(clinic);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        Clinic read = clinicService.read(clinic.getClinicId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Clinic updated = new Clinic.Builder().copy(clinic)
                .setLocation("Building B, Floor 2")
                .build();
        Clinic result = clinicService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        clinicService.delete(clinic.getClinicId());
        Clinic deleted = clinicService.read(clinic.getClinicId());
        assertNull(deleted);
        System.out.println("Clinic deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(clinicService.getAll());
    }
}
