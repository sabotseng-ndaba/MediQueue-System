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

import com.cput.mediqueuesystem.domain.VitalSigns;
import com.cput.mediqueuesystem.service.IVitalSignsService;

/*
 * VitalSignsController.java
 * REST controller for managing vital signs.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@RestController
@RequestMapping("/mediqueue/vital-signs")
@CrossOrigin(origins = "*")
public class VitalSignsController {

    private final IVitalSignsService vitalSignsService;

    @Autowired
    public VitalSignsController(IVitalSignsService vitalSignsService) {
        this.vitalSignsService = vitalSignsService;
    }

    @PostMapping("/create")
    public ResponseEntity<VitalSigns> create(@RequestBody VitalSigns vitalSigns) {
        VitalSigns created = vitalSignsService.create(vitalSigns);
        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<VitalSigns> read(@PathVariable("id") String vitalId) {
        VitalSigns vitalSigns = vitalSignsService.read(vitalId);
        if (vitalSigns == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vitalSigns, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<VitalSigns> update(@RequestBody VitalSigns vitalSigns) {
        VitalSigns updated = vitalSignsService.update(vitalSigns);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String vitalId) {
        vitalSignsService.delete(vitalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VitalSigns>> getAll() {
        return new ResponseEntity<>(vitalSignsService.getAll(), HttpStatus.OK);
    }
}
