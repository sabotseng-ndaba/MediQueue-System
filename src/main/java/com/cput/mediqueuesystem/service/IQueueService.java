package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.Queue;

/*
 * IQueueService.java
 * Service contract for Queue business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IQueueService {

    Queue create(Queue queue);

    Queue read(String queueId);

    Queue update(Queue queue);

    void delete(String queueId);

    List<Queue> getAll();
}
