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

import com.cput.mediqueuesystem.domain.Visit;
import com.cput.mediqueuesystem.service.IVisitService;

/*
 * VisitController.java
 * REST controller for managing visits.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@RestController
@RequestMapping("/mediqueue/visit")
@CrossOrigin(origins = "*")
public class VisitController {

    private final IVisitService visitService;

    @Autowired
    public VisitController(IVisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("/create")
    public ResponseEntity<Visit> create(@RequestBody Visit visit) {
        Visit created = visitService.create(visit);
        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Visit> read(@PathVariable("id") String visitId) {
        Visit visit = visitService.read(visitId);
        if (visit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Visit> update(@RequestBody Visit visit) {
        Visit updated = visitService.update(visit);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String visitId) {
        visitService.delete(visitId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Visit>> getAll() {
        return new ResponseEntity<>(visitService.getAll(), HttpStatus.OK);
    }
}
