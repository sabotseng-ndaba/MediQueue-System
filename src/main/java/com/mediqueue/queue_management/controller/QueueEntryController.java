package com.mediqueue.queue_management.controller;

import com.mediqueue.queue_management.model.QueueEntry;
import com.mediqueue.queue_management.service.QueueEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/queue-entries")
@CrossOrigin(origins = "*")
public class QueueEntryController {

    @Autowired
    private QueueEntryService queueEntryService;

    // GET all entries for a queue
    @GetMapping("/queue/{queueId}")
    public List<QueueEntry> getEntriesByQueue(@PathVariable int queueId) {
        return queueEntryService.getEntriesByQueue(queueId);
    }

    // GET entries filtered by status
    @GetMapping("/queue/{queueId}/status/{status}")
    public List<QueueEntry> getEntriesByStatus(
            @PathVariable int queueId,
            @PathVariable String status) {
        return queueEntryService.getEntriesByStatus(queueId, status);
    }

    // POST add patient to queue
    @PostMapping
    public QueueEntry addEntry(@RequestBody QueueEntry entry) {
        return queueEntryService.addEntry(entry);
    }

    // PATCH call in next patient
    @PatchMapping("/{id}/call-in")
    public QueueEntry callIn(@PathVariable int id) {
        return queueEntryService.callIn(id);
    }

    // PATCH mark patient as done
    @PatchMapping("/{id}/complete")
    public QueueEntry markDone(@PathVariable int id) {
        return queueEntryService.markDone(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = queueEntryService.deleteEntry(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}