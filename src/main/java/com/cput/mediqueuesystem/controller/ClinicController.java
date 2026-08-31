package com.cput.mediqueuesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cput.mediqueuesystem.domain.Clinic;
import com.cput.mediqueuesystem.service.IClinicService;

/*
 * ClinicController.java
 * REST controller for managing clinics.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@RestController
@RequestMapping("/clinic")
@CrossOrigin(origins = "*")
public class ClinicController {

    private final IClinicService clinicService;

    @Autowired
    public ClinicController(IClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping("/create")
    public Clinic create(@RequestBody Clinic clinic) {
        return clinicService.create(clinic);
    }

    @GetMapping("/read/{id}")
    public Clinic read(@PathVariable("id") String clinicId) {
        return clinicService.read(clinicId);
    }

    @PutMapping("/update")
    public Clinic update(@RequestBody Clinic clinic) {
        return clinicService.update(clinic);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") String clinicId) {
        return clinicService.delete(clinicId);
    }

    @GetMapping("/getAll")
    public List<Clinic> getAll() {
        return clinicService.getAll();
    }
}
