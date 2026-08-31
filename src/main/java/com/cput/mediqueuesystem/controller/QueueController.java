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

import com.cput.mediqueuesystem.domain.Queue;
import com.cput.mediqueuesystem.service.IQueueService;

/*
 * QueueController.java
 * REST controller for managing queues.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@RestController
@RequestMapping("/mediqueue/queue")
@CrossOrigin(origins = "*")
public class QueueController {

    private final IQueueService queueService;

    @Autowired
    public QueueController(IQueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("/create")
    public ResponseEntity<Queue> create(@RequestBody Queue queue) {
        Queue created = queueService.create(queue);
        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Queue> read(@PathVariable("id") String queueId) {
        Queue queue = queueService.read(queueId);
        if (queue == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(queue, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Queue> update(@RequestBody Queue queue) {
        Queue updated = queueService.update(queue);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String queueId) {
        queueService.delete(queueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Queue>> getAll() {
        return new ResponseEntity<>(queueService.getAll(), HttpStatus.OK);
    }
}
