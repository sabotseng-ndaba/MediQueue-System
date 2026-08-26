package com.cput.mediqueuesystem.service;

import com.cput.mediqueuesystem.domain.Clinic;
import com.cput.mediqueuesystem.domain.Queue;
import com.cput.mediqueuesystem.factory.ClinicFactory;
import com.cput.mediqueuesystem.factory.QueueFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class QueueServiceTest {

    @Autowired
    private QueueService queueService;

    @Autowired
    private ClinicService clinicService;

    Clinic clinic = ClinicFactory.createClinic(
            "TCL01", "Main Clinic", "Building A", "021 555 0999");

    Queue queue = QueueFactory.createQueue(
            "TQ001", clinic, LocalDate.of(2026, 6, 25), 30);

    @Test
    void a_create() {
        clinicService.create(clinic);
        Queue created = queueService.create(queue);
        assertNotNull(created);
        System.out.println(created);
    }

    @Test
    void b_read() {
        Queue read = queueService.read(queue.getQueueId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        Queue updated = new Queue.Builder().copy(queue)
                .setMaxCapacity(50)
                .build();
        Queue result = queueService.update(updated);
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    @Disabled
    void e_delete() {
        queueService.delete(queue.getQueueId());
        Queue deleted = queueService.read(queue.getQueueId());
        assertNull(deleted);
        System.out.println("Queue deleted successfully");
    }

    @Test
    void d_getAll() {
        System.out.println(queueService.getAll());
    }
}
