package com.cput.mediqueuesystem.service;

import com.cput.mediqueuesystem.domain.Department;
import com.cput.mediqueuesystem.factory.DepartmentFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    Department department = DepartmentFactory.createDepartment(
            "TD001", "TestCardiology", "Heart and cardiovascular department");

    @Test
    void a_create() {
        Department created = departmentService.create(department);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        Department read = departmentService.read(department.getDepartmentId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Department updated = new Department.Builder().copy(department)
                .setDescription("Updated description")
                .build();
        Department result = departmentService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        departmentService.delete(department.getDepartmentId());
        Department deleted = departmentService.read(department.getDepartmentId());
        assertNull(deleted);
        System.out.println("Department deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(departmentService.getAll());
    }
}
