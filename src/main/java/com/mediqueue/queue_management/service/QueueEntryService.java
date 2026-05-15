package com.mediqueue.queue_management.service;

import com.mediqueue.queue_management.model.QueueEntry;
import com.mediqueue.queue_management.repository.QueueEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class QueueEntryService {

    @Autowired
    private QueueEntryRepository queueEntryRepository;

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
        int count = queueEntryRepository.countByQueueId(entry.getQueueId());
        entry.setQueueNumber(count + 1);
        entry.setCheckInTime(LocalTime.now());
        entry.setStatus("waiting");
        return queueEntryRepository.save(entry);
    }

    // Call in next patient
    public QueueEntry callIn(int id) {
        QueueEntry entry = queueEntryRepository.findById(id).orElse(null);
        if (entry != null) {
            entry.setStatus("in_consult");
            return queueEntryRepository.save(entry);
        }
        return null;
    }

    // Mark patient as done
    public QueueEntry markDone(int id) {
        QueueEntry entry = queueEntryRepository.findById(id).orElse(null);
        if (entry != null) {
            entry.setStatus("completed");
            return queueEntryRepository.save(entry);
        }
        return null;
    }
}