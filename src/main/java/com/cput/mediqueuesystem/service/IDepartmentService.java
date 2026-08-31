package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.Department;

/*
 * IDepartmentService.java
 * Service contract for Department business logic.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public interface IDepartmentService extends IService<Department, String> {
    List<Department> getAll();
}
