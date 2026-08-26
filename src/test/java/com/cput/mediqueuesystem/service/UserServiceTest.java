package com.cput.mediqueuesystem.service;

import com.cput.mediqueuesystem.domain.User;
import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.factory.RoleFactory;
import com.cput.mediqueuesystem.factory.PatientFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    Role role = RoleFactory.createRole("TR001", "TestPatientRole");

    Patient patient = PatientFactory.createPatient(
            "TP001", "Charmaine", "Dlamini",
            "test.charmaine@gmail.com", "Password123",
            "0731234567", true, LocalDateTime.now(), role,
            "9901011234599", LocalDate.of(1999, 1, 1),
            "Female", "123 Main St", null, null);

    @Test
    void a_create() {
        roleService.create(role);
        User created = userService.create(patient);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        User read = userService.read(patient.getUserId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Patient.Builder builder = new Patient.Builder();
        builder.copy(patient);
        builder.setPhoneNumber("073 999 9999");
        User updated = builder.build();
        User result = userService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        userService.delete(patient.getUserId());
        User deleted = userService.read(patient.getUserId());
        assertNull(deleted);
        System.out.println("User deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(userService.getAll());
    }
}
