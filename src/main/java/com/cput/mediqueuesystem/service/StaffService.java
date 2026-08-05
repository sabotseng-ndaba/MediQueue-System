package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.Staff;
import com.cput.mediqueuesystem.repository.StaffRepository;

/*
StaffService.java
StaffService
Author: Charmaine Dlamini
Date: 05 August 2026
 */

@Service
public class StaffService implements IStaffService {

    private final StaffRepository repository;

    @Autowired
    StaffService(StaffRepository repository) {
        this.repository = repository;
    }

    // Saves a new staff member to the database
    @Override
    public Staff create(Staff staff) {
        return this.repository.save(staff);
    }

    // Finds a staff member by their staff ID
    @Override
    public Staff read(String staffId) {
        return this.repository.findById(staffId).orElse(null);
    }

    // Updates an existing staff member in the database
    @Override
    public Staff update(Staff staff) {
        return this.repository.save(staff);
    }

    // Deletes a staff member by their staff ID
    @Override
    public boolean delete(String staffId) {
        this.repository.deleteById(staffId);
        return true;
    }

    // Returns a list of all staff members
    @Override
    public List<Staff> getAll() {
        return this.repository.findAll();
    }
}
