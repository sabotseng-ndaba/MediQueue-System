package com.mediqueue.queue_management.service;

import com.mediqueue.queue_management.model.Queue;
import com.mediqueue.queue_management.model.QueueEntry;
import com.mediqueue.queue_management.repository.QueueEntryRepository;
import com.mediqueue.queue_management.repository.QueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueueEntryServiceTest {

    @Mock
    private QueueEntryRepository queueEntryRepository;

    @Mock
    private QueueRepository queueRepository;

    @InjectMocks
    private QueueEntryService queueEntryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEntry_succeeds_whenQueueIsActiveAndUnderCapacity() {
        Queue activeQueue = new Queue.Builder().queueId(1).status("active").maxCapacity(40).build();
        QueueEntry newEntry = new QueueEntry.Builder().queueId(1).patientId(106).build();

        when(queueRepository.findById(1)).thenReturn(Optional.of(activeQueue));
        when(queueEntryRepository.countByQueueId(1)).thenReturn(3);
        when(queueEntryRepository.save(any(QueueEntry.class))).thenReturn(newEntry);

        QueueEntry result = queueEntryService.addEntry(newEntry);

        assertNotNull(result);
        verify(queueEntryRepository, times(1)).save(any(QueueEntry.class));
    }

    @Test
    void addEntry_throwsBadRequest_whenQueueIsClosed() {
        Queue closedQueue = new Queue.Builder().queueId(1).status("closed").maxCapacity(40).build();
        QueueEntry newEntry = new QueueEntry.Builder().queueId(1).patientId(106).build();

        when(queueRepository.findById(1)).thenReturn(Optional.of(closedQueue));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> queueEntryService.addEntry(newEntry));

        assertEquals(400, ex.getStatusCode().value());
        verify(queueEntryRepository, never()).save(any());
    }

    @Test
    void addEntry_throwsBadRequest_whenQueueIsAtMaxCapacity() {
        Queue fullQueue = new Queue.Builder().queueId(1).status("active").maxCapacity(5).build();
        QueueEntry newEntry = new QueueEntry.Builder().queueId(1).patientId(106).build();

        when(queueRepository.findById(1)).thenReturn(Optional.of(fullQueue));
        when(queueEntryRepository.countByQueueId(1)).thenReturn(5); // already at capacity

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> queueEntryService.addEntry(newEntry));

        assertEquals(400, ex.getStatusCode().value());
        verify(queueEntryRepository, never()).save(any());
    }

    @Test
    void callIn_succeeds_whenEntryIsWaiting() {
        QueueEntry waitingEntry = new QueueEntry.Builder().queueEntryId(1).status("waiting").build();

        when(queueEntryRepository.findById(1)).thenReturn(Optional.of(waitingEntry));
        when(queueEntryRepository.save(any(QueueEntry.class))).thenReturn(waitingEntry);

        QueueEntry result = queueEntryService.callIn(1);

        assertNotNull(result);
        verify(queueEntryRepository, times(1)).save(any(QueueEntry.class));
    }

    @Test
    void callIn_throwsBadRequest_whenEntryIsNotWaiting() {
        QueueEntry inConsultEntry = new QueueEntry.Builder().queueEntryId(1).status("in_consult").build();

        when(queueEntryRepository.findById(1)).thenReturn(Optional.of(inConsultEntry));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> queueEntryService.callIn(1));

        assertEquals(400, ex.getStatusCode().value());
        verify(queueEntryRepository, never()).save(any());
    }

    @Test
    void markDone_succeeds_whenEntryIsInConsult() {
        QueueEntry inConsultEntry = new QueueEntry.Builder().queueEntryId(1).status("in_consult").build();

        when(queueEntryRepository.findById(1)).thenReturn(Optional.of(inConsultEntry));
        when(queueEntryRepository.save(any(QueueEntry.class))).thenReturn(inConsultEntry);

        QueueEntry result = queueEntryService.markDone(1);

        assertNotNull(result);
        verify(queueEntryRepository, times(1)).save(any(QueueEntry.class));
    }

    @Test
    void markDone_throwsBadRequest_whenEntryIsNotInConsult() {
        QueueEntry waitingEntry = new QueueEntry.Builder().queueEntryId(1).status("waiting").build();

        when(queueEntryRepository.findById(1)).thenReturn(Optional.of(waitingEntry));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> queueEntryService.markDone(1));

        assertEquals(400, ex.getStatusCode().value());
        verify(queueEntryRepository, never()).save(any());
    }
}