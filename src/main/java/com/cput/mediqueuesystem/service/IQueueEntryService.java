package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.QueueEntry;

/*
 * IQueueEntryService.java
 * Service contract for QueueEntry business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IQueueEntryService {

    QueueEntry create(QueueEntry queueEntry);

    QueueEntry read(String queueEntryId);

    QueueEntry update(QueueEntry queueEntry);

    void delete(String queueEntryId);

    List<QueueEntry> getAll();
}
