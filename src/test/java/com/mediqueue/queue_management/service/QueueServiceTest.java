package com.mediqueue.queue_management.service;

import com.mediqueue.queue_management.model.Queue;
import com.mediqueue.queue_management.repository.QueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueueServiceTest {

    @Mock
    private QueueRepository queueRepository;

    @InjectMocks
    private QueueService queueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createQueue_succeeds_whenNoQueueExistsForClinicToday() {
        Queue newQueue = new Queue.Builder().clinicId(1).maxCapacity(40).build();
        when(queueRepository.findByClinicIdAndDate(1, LocalDate.now())).thenReturn(Optional.empty());
        when(queueRepository.save(any(Queue.class))).thenReturn(newQueue);

        Queue result = queueService.createQueue(newQueue);

        assertNotNull(result);
        verify(queueRepository, times(1)).save(any(Queue.class));
    }

    @Test
    void createQueue_throwsConflict_whenQueueAlreadyExistsForClinicToday() {
        Queue existing = new Queue.Builder().clinicId(1).build();
        Queue newQueue = new Queue.Builder().clinicId(1).maxCapacity(40).build();
        when(queueRepository.findByClinicIdAndDate(1, LocalDate.now())).thenReturn(Optional.of(existing));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> queueService.createQueue(newQueue));

        assertEquals(409, ex.getStatusCode().value());
        verify(queueRepository, never()).save(any());
    }

    @Test
    void closeQueue_throwsNotFound_whenQueueDoesNotExist() {
        when(queueRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> queueService.closeQueue(999));
    }
}