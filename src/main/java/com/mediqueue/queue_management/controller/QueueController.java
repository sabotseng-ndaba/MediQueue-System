package com.mediqueue.queue_management.controller;

import com.mediqueue.queue_management.model.Queue;
import com.mediqueue.queue_management.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queues")
@CrossOrigin(origins = "*")
public class QueueController {

    @Autowired
    private QueueService queueService;

    // GET all queues
    @GetMapping
    public List<Queue> getAllQueues() {
        return queueService.getAllQueues();
    }

    // GET one queue by ID
    @GetMapping("/{id}")
    public Queue getQueueById(@PathVariable int id) {
        return queueService.getQueueById(id);
    }

    // GET today's queue for a clinic
    @GetMapping("/today/{clinicId}")
    public Queue getTodaysQueue(@PathVariable int clinicId) {
        return queueService.getTodaysQueue(clinicId);
    }

    // POST create a new queue
    @PostMapping
    public Queue createQueue(@RequestBody Queue queue) {
        return queueService.createQueue(queue);
    }

    // PATCH close a queue
    @PatchMapping("/{id}/close")
    public Queue closeQueue(@PathVariable int id) {
        return queueService.closeQueue(id);
    }

    // PATCH reopen a queue
    @PatchMapping("/{id}/reopen")
    public Queue reopenQueue(@PathVariable int id) {
        return queueService.reopenQueue(id);
    }
}
