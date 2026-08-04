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

import com.cput.mediqueuesystem.domain.QueueEntry;
import com.cput.mediqueuesystem.service.IQueueEntryService;

/*
 * QueueEntryController.java
 * REST controller for managing queue entries.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@RestController
@RequestMapping("/mediqueue/queue-entry")
@CrossOrigin(origins = "*")
public class QueueEntryController {

    private final IQueueEntryService queueEntryService;

    @Autowired
    public QueueEntryController(IQueueEntryService queueEntryService) {
        this.queueEntryService = queueEntryService;
    }

    @PostMapping("/create")
    public ResponseEntity<QueueEntry> create(@RequestBody QueueEntry queueEntry) {
        QueueEntry created = queueEntryService.create(queueEntry);
        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<QueueEntry> read(@PathVariable("id") String queueEntryId) {
        QueueEntry queueEntry = queueEntryService.read(queueEntryId);
        if (queueEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(queueEntry, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<QueueEntry> update(@RequestBody QueueEntry queueEntry) {
        QueueEntry updated = queueEntryService.update(queueEntry);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String queueEntryId) {
        queueEntryService.delete(queueEntryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QueueEntry>> getAll() {
        return new ResponseEntity<>(queueEntryService.getAll(), HttpStatus.OK);
    }
}
