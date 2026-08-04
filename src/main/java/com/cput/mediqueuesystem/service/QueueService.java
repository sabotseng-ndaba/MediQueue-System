package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.Queue;
import com.cput.mediqueuesystem.factory.QueueFactory;
import com.cput.mediqueuesystem.repository.QueueRepository;

/*
 * QueueService.java
 * Implements the business logic for managing queues.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Service
public class QueueService implements IQueueService {

    private final QueueRepository queueRepository;

    @Autowired
    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    @Override
    public Queue create(Queue queue) {
        if (queue == null) {
            return null;
        }
        Queue validated = QueueFactory.createQueue(
                queue.getQueueId(), queue.getClinic(), queue.getDate(), queue.getMaxCapacity());
        if (validated == null) {
            return null;
        }
        return queueRepository.save(validated);
    }

    @Override
    public Queue read(String queueId) {
        return queueRepository.findById(queueId).orElse(null);
    }

    @Override
    public Queue update(Queue queue) {
        if (!queueRepository.existsById(queue.getQueueId())) {
            return null;
        }
        return queueRepository.save(queue);
    }

    @Override
    public void delete(String queueId) {
        queueRepository.deleteById(queueId);
    }

    @Override
    public List<Queue> getAll() {
        return queueRepository.findAll();
    }
}
