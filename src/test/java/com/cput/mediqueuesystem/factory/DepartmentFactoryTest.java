package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.Department;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentFactoryTest {

    @Test
    void createDepartment() {
        Department department = DepartmentFactory.createDepartment(
                "D001", "Cardiology", "Heart and cardiovascular department");
        assertNotNull(department);
        assertEquals("D001", department.getDepartmentId());
        assertEquals("Cardiology", department.getDepartmentName());
        System.out.println(department);
    }
}
