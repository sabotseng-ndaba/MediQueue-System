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

import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.service.PatientService;

/*
PatientController.java
Patient Controller
Author: Charmaine Dlamini
Date: 05 August 2026
 */
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    //Sending or storing info
    @PostMapping("/create")
    public Patient create(@RequestBody Patient patient) {
        return patientService.create(patient);
    }
    //Retrieving a specific patient by its ID
    @GetMapping("/read/{patientId}")
    public Patient read(@PathVariable("patientId") String patientId) {
        return patientService.read(patientId);
    }
        
    //Updating an existing patient
    @PutMapping("/update")
    public Patient update(@RequestBody Patient patient) {
        return patientService.update(patient);
    }
    //Deleting a patient by its ID
    @DeleteMapping("/delete/{patientId}")
    public void delete(@PathVariable String patientId) {
        patientService.delete(patientId);
    }
    //Retrieving all patients
    @GetMapping("/getAll")
    public List<Patient> getAll() {
        return patientService.getAll();
    }

}
