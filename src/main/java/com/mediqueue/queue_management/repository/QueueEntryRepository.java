package com.mediqueue.queue_management.repository;

import com.mediqueue.queue_management.model.QueueEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueEntryRepository extends JpaRepository<QueueEntry, Integer> {

    List<QueueEntry> findByQueueId(int queueId);
    List<QueueEntry> findByQueueIdAndStatus(int queueId, String status);
    int countByQueueId(int queueId);
}