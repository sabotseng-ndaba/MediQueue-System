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

import com.cput.mediqueuesystem.domain.Department;
import com.cput.mediqueuesystem.service.DepartmentService;

/*
DepartmentController.java
Department Controller
Author: Charmaine Dlamini
Date: 05 August 2026
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    //Sending or storing info
    @PostMapping("/create")
    public Department create(@RequestBody Department department) {
        return departmentService.create(department);
    }
    //Retrieving a specific department by its ID
    @GetMapping("/read/{departmentId}")
    public Department read(@PathVariable("departmentId") String departmentId) {
        return departmentService.read(departmentId);
    }
        
    //Updating an existing department
    @PutMapping("/update")
    public Department update(@RequestBody Department department) {
        return departmentService.update(department);
    }
    //Deleting a department by its ID
    @DeleteMapping("/delete/{departmentId}")
    public void delete(@PathVariable String departmentId) {
        departmentService.delete(departmentId);
    }
    //Retrieving all departments
    @GetMapping("/getAll")
    public List<Department> getAll() {
        return departmentService.getAll();
    }

}
