package com.cput.mediqueuesystem.factory;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.validator.GenericValidator;

import com.cput.mediqueuesystem.domain.Appointment;
import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.domain.Visit;

/*
 * VisitFactory.java
 * Validates input and builds Visit objects.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public class VisitFactory {

    public static Visit createVisit(String visitId, Patient patient, Appointment appointment,
                                     LocalDate visitDate, LocalTime checkInTime,
                                     LocalTime checkOutTime, String status) {

        if (GenericValidator.isBlankOrNull(visitId) || patient == null || visitDate == null) {
            return null;
        }

        return new Visit.Builder()
                .setVisitId(visitId)
                .setPatient(patient)
                .setAppointment(appointment)
                .setVisitDate(visitDate)
                .setCheckInTime(checkInTime)
                .setCheckOutTime(checkOutTime)
                .setStatus(status)
                .build();
    }
}
