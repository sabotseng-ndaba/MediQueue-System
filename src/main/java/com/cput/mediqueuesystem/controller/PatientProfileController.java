package com.cput.mediqueuesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cput.mediqueuesystem.domain.PatientProfile;
import com.cput.mediqueuesystem.service.IPatientProfileService;

/*
 * PatientProfileController.java
 * REST controller for managing patient profiles.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@RestController
@RequestMapping("/mediqueue/patient-profile")
@CrossOrigin(origins = "*")
public class PatientProfileController {

    private final IPatientProfileService patientProfileService;

    @Autowired
    public PatientProfileController(IPatientProfileService patientProfileService) {
        this.patientProfileService = patientProfileService;
    }

    @PostMapping("/create")
    public ResponseEntity<PatientProfile> create(@RequestBody PatientProfile patientProfile) {
        PatientProfile created = patientProfileService.create(patientProfile);
        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<PatientProfile> read(@PathVariable("id") String patientId) {
        PatientProfile patientProfile = patientProfileService.read(patientId);
        if (patientProfile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patientProfile, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PatientProfile> update(@RequestBody PatientProfile patientProfile) {
        PatientProfile updated = patientProfileService.update(patientProfile);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String patientId) {
        patientProfileService.delete(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientProfile>> getAll() {
        return new ResponseEntity<>(patientProfileService.getAll(), HttpStatus.OK);
    }
}
