package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.repository.PatientRepository;

/*
PatientService.java
PatientService
Author: Charmaine Dlamini
Date: 05 August 2026
 */

@Service
public class PatientService implements IPatientService {

    private final PatientRepository repository;

    @Autowired
    PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    // Saves a new patient to the database
    @Override
    public Patient create(Patient patient) {
        return this.repository.save(patient);
    }

    // Finds a patient by their patient ID
    @Override
    public Patient read(String patientId) {
        return this.repository.findById(patientId).orElse(null);
    }

    // Updates an existing patient in the database
    @Override
    public Patient update(Patient patient) {
        return this.repository.save(patient);
    }

    // Deletes a patient by their patient ID
    @Override
    public boolean delete(String patientId) {
        this.repository.deleteById(patientId);
        return true;
    }

    // Returns a list of all patients
    @Override
    public List<Patient> getAll() {
        return this.repository.findAll();
    }
}
