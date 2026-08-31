package com.cput.mediqueuesystem.factory;

import java.time.LocalDateTime;

import com.cput.mediqueuesystem.domain.Department;
import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.domain.Staff;
import com.cput.mediqueuesystem.util.Helper;

/*
 * StaffFactory.java
 * Validates input and builds Staff objects.
 * Staff inherits the common User fields validated by UserFactory.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public class StaffFactory extends UserFactory {

    public static Staff createStaff(String userId, String firstName, String lastName,
                                    String email, String password, String phoneNumber,
                                    boolean status, LocalDateTime createdAt, Role role,
                                    Department department, String position) {

        if (!isValidUser(userId, firstName, lastName, email, password, phoneNumber, createdAt, role)
                || Helper.isNull(department)
                || Helper.isNullOrEmpty(position)) {
            return null;
        }

        Staff.Builder builder = new Staff.Builder();
        builder.setUserId(userId).setFirstName(firstName).setLastName(lastName)
               .setEmail(email).setPassword(password).setPhoneNumber(phoneNumber)
               .setStatus(status).setCreatedAt(createdAt).setRole(role);
        builder.setDepartment(department).setPosition(position);
        return builder.build();
    }
}
