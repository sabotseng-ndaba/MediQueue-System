package com.mediqueue.queue_management.service;

import com.mediqueue.queue_management.model.Queue;
import com.mediqueue.queue_management.model.QueueEntry;
import com.mediqueue.queue_management.repository.QueueEntryRepository;
import com.mediqueue.queue_management.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Service
public class QueueEntryService {

    @Autowired
    private QueueEntryRepository queueEntryRepository;

    @Autowired
    private QueueRepository queueRepository;

    // Get all entries for a queue
    public List<QueueEntry> getEntriesByQueue(int queueId) {
        return queueEntryRepository.findByQueueId(queueId);
    }

    // Get entries filtered by status
    public List<QueueEntry> getEntriesByStatus(int queueId, String status) {
        return queueEntryRepository.findByQueueIdAndStatus(queueId, status);
    }

    // Add a patient to the queue
    public QueueEntry addEntry(QueueEntry entry) {
        Queue queue = queueRepository.findById(entry.getQueueId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Queue not found"));

        if (!"active".equals(queue.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This queue is closed and cannot accept new patients");
        }

        int count = queueEntryRepository.countByQueueId(entry.getQueueId());
        if (count >= queue.getMaxCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Queue has reached its maximum capacity");
        }

        entry.setQueueNumber(count + 1);
        entry.setCheckInTime(LocalTime.now());
        entry.setStatus("waiting");
        if (entry.getPriorityLevel()== null || entry.getPriorityLevel().isBlank()) {
            entry.setPriorityLevel("normal");
        }
        return queueEntryRepository.save(entry);
    }

    // Call in next patient
    public QueueEntry callIn(int id) {
        QueueEntry entry = queueEntryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found"));
        if (!"waiting".equals(entry.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only waiting patients can be called in");
        }
        entry.setStatus("in_consult");
        return queueEntryRepository.save(entry);
    }

    // Mark patient as done
    public QueueEntry markDone(int id) {
        QueueEntry entry = queueEntryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found"));
        if (!"in_consult".equals(entry.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only patients currently in consultation can be marked complete");
        }
        entry.setStatus("completed");
        return queueEntryRepository.save(entry);
    }

    public boolean deleteEntry(int id) {
        if (!queueEntryRepository.existsById(id)) {
            return false;
        }
        queueEntryRepository.deleteById(id);
        return true;
    }
}