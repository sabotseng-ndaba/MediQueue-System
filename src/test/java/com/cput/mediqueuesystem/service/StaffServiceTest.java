package com.cput.mediqueuesystem.service;

import com.cput.mediqueuesystem.domain.*;
import com.cput.mediqueuesystem.factory.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class StaffServiceTest {

    @Autowired
    private StaffService staffService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    Role role = RoleFactory.createRole("TR002", "TestDoctorRole");
    Department department = DepartmentFactory.createDepartment(
            "TD001", "TestCardiology", "Heart department");

    Staff staff = StaffFactory.createStaff(
            "TS001", "Imaan", "Achmat",
            "test.imaan@gmail.com", "Password123",
            "0731234568", true, LocalDateTime.now(), role,
            department, "Doctor");

    @Test
    void a_create() {
        roleService.create(role);
        departmentService.create(department);
        Staff created = staffService.create(staff);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        Staff read = staffService.read(staff.getUserId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Staff.Builder builder = new Staff.Builder();
        builder.copy(staff);
        builder.setPosition("Senior Doctor");
        Staff updated = builder.build();
        Staff result = staffService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        staffService.delete(staff.getUserId());
        Staff deleted = staffService.read(staff.getUserId());
        assertNull(deleted);
        System.out.println("Staff deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(staffService.getAll());
    }
}
