package com.cput.mediqueuesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cput.mediqueuesystem.domain.Staff;
import com.cput.mediqueuesystem.service.StaffService;

/*
StaffController.java
Staff Controller
Author: Charmaine Dlamini
Date: 05 August 2026
 */
@RestController
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }
    //Sending or storing info
    @PostMapping("/create")
    public Staff create(@RequestBody Staff staff) {
        return staffService.create(staff);
    }
    //Retrieving a specific staff by its ID
    @GetMapping("/read/{staffId}")
    public Staff read(@PathVariable("staffId") String staffId) {
        return staffService.read(staffId);
    }
        
    //Updating an existing staff
    @PutMapping("/update")
    public Staff update(@RequestBody Staff staff) {
        return staffService.update(staff);
    }
    //Deleting a staff by its ID
    @DeleteMapping("/delete/{staffId}")
    public void delete(@PathVariable String staffId) {
        staffService.delete(staffId);
    }
    //Retrieving all staff
    @GetMapping("/getAll")
    public List<Staff> getAll() {
        return staffService.getAll();
    }

}
