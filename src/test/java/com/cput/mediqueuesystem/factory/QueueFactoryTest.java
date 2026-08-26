package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.Clinic;
import com.cput.mediqueuesystem.domain.Queue;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class QueueFactoryTest {

    @Test
    void createQueue() {
        Clinic clinic = ClinicFactory.createClinic(
                "CL001", "Main Clinic", "Building A", "021 555 0100");

        Queue queue = QueueFactory.createQueue(
                "Q001", clinic, LocalDate.of(2026, 6, 25), 30);
        assertNotNull(queue);
        assertEquals("Q001", queue.getQueueId());
        assertEquals(30, queue.getMaxCapacity());
        System.out.println(queue);
    }
}
