package com.mediqueue.queue_management.service;

import com.mediqueue.queue_management.model.Queue;
import com.mediqueue.queue_management.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    // Get all queues
    public List<Queue> getAllQueues() {
        return queueRepository.findAll();
    }

    // Get one queue by ID
    public Queue getQueueById(int id) {
        return queueRepository.findById(id).orElse(null);
    }

    // Get today's queue for a clinic
    public Queue getTodaysQueue(int clinicId) {
        return queueRepository
                .findByClinicIdAndDate(clinicId, LocalDate.now())
                .orElse(null);
    }

    // Create a new queue
    public Queue createQueue(Queue queue) {
        LocalDate today = LocalDate.now();
        queueRepository.findByClinicIdAndDate(queue.getClinicId(), today).ifPresent(existing -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This clinic already has an active queue for today");
        });
        queue.setDate(today);
        queue.setStatus("active");
        return queueRepository.save(queue);
    }

    // Close a queue
    public Queue closeQueue(int id) {
        Queue queue = queueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Queue not found"));
        queue.setStatus("closed");
        return queueRepository.save(queue);
    }

    // Reopen a queue
    public Queue reopenQueue(int id) {
        Queue queue = queueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Queue not found"));
        queue.setStatus("active");
        return queueRepository.save(queue);
    }
}