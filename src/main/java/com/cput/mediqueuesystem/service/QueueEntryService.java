package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.QueueEntry;
import com.cput.mediqueuesystem.factory.QueueEntryFactory;
import com.cput.mediqueuesystem.repository.QueueEntryRepository;

/*
 * QueueEntryService.java
 * Implements the business logic for managing queue entries.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Service
public class QueueEntryService implements IQueueEntryService {

    private final QueueEntryRepository queueEntryRepository;

    @Autowired
    public QueueEntryService(QueueEntryRepository queueEntryRepository) {
        this.queueEntryRepository = queueEntryRepository;
    }

    @Override
    public QueueEntry create(QueueEntry queueEntry) {
        if (queueEntry == null) {
            return null;
        }
        QueueEntry validated = QueueEntryFactory.createQueueEntry(
                queueEntry.getQueueEntryId(), queueEntry.getQueue(), queueEntry.getPatient(),
                queueEntry.getDoctor(), queueEntry.getVisit(), queueEntry.getQueueNumber(),
                queueEntry.getPriorityLevel(), queueEntry.getStatus(), queueEntry.getCheckInTime());
        if (validated == null) {
            return null;
        }
        return queueEntryRepository.save(validated);
    }

    @Override
    public QueueEntry read(String queueEntryId) {
        return queueEntryRepository.findById(queueEntryId).orElse(null);
    }

    @Override
    public QueueEntry update(QueueEntry queueEntry) {
        if (!queueEntryRepository.existsById(queueEntry.getQueueEntryId())) {
            return null;
        }
        return queueEntryRepository.save(queueEntry);
    }

    @Override
    public void delete(String queueEntryId) {
        queueEntryRepository.deleteById(queueEntryId);
    }

    @Override
    public List<QueueEntry> getAll() {
        return queueEntryRepository.findAll();
    }
}
