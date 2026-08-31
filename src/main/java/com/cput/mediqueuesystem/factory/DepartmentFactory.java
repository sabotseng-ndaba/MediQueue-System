package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.Department;
import com.cput.mediqueuesystem.util.Helper;

/*
 * DepartmentFactory.java
 * Validates input and builds Department objects. Returns null if
 * required fields are missing or invalid, so callers can check
 * for a null result before persisting.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public class DepartmentFactory {

    public static Department createDepartment(String departmentId, String departmentName,
                                               String description) {

        if (Helper.isNullOrEmpty(departmentId) || Helper.isNullOrEmpty(departmentName)) {
            return null;
        }

        return new Department.Builder()
                .setDepartmentId(departmentId)
                .setDepartmentName(departmentName)
                .setDescription(description)
                .build();
    }
}
