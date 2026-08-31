package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.Staff;

/*
 * IStaffService.java
 * Service contract for Staff business logic.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public interface IStaffService extends IService<Staff, String> {
    List<Staff> getAll();
}
