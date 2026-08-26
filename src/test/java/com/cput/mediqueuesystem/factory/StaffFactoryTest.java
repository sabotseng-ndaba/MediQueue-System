package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.Department;
import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.domain.Staff;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StaffFactoryTest {

    @Test
    void createStaff() {
        Role role = RoleFactory.createRole("R002", "Doctor");
        Department department = DepartmentFactory.createDepartment(
                "D001", "Cardiology", "Heart department");

        Staff staff = StaffFactory.createStaff(
                "S001", "Imaan", "Achmat",
                "imaan@gmail.com", "Password123",
                "0731234568", true, LocalDateTime.now(), role,
                department, "Doctor");
        assertNotNull(staff);
        assertEquals("S001", staff.getUserId());
        assertEquals("Imaan", staff.getFirstName());
        System.out.println(staff);
    }
}
