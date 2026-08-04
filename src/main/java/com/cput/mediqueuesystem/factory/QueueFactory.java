package com.cput.mediqueuesystem.factory;

import java.time.LocalDate;

import org.apache.commons.validator.GenericValidator;

import com.cput.mediqueuesystem.domain.Clinic;
import com.cput.mediqueuesystem.domain.Queue;

/*
 * QueueFactory.java
 * Validates input and builds Queue objects.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public class QueueFactory {

    public static Queue createQueue(String queueId, Clinic clinic, LocalDate date, int maxCapacity) {

        if (GenericValidator.isBlankOrNull(queueId) || clinic == null || date == null) {
            return null;
        }

        return new Queue.Builder()
                .setQueueId(queueId)
                .setClinic(clinic)
                .setDate(date)
                .setMaxCapacity(maxCapacity)
                .build();
    }
}
