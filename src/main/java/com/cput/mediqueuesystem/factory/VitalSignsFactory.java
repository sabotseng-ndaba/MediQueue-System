package com.cput.mediqueuesystem.factory;

import java.time.LocalDateTime;

import org.apache.commons.validator.GenericValidator;

import com.cput.mediqueuesystem.domain.Staff;
import com.cput.mediqueuesystem.domain.VitalSigns;
import com.cput.mediqueuesystem.domain.Visit;

/*
 * VitalSignsFactory.java
 * Validates input and builds VitalSigns objects.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public class VitalSignsFactory {

    public static VitalSigns createVitalSigns(String vitalId, Visit visit, String temperature,
                                               String bloodPressure, String heartRate, String weight,
                                               Staff recordedBy, LocalDateTime recordedAt) {

        if (GenericValidator.isBlankOrNull(vitalId) || visit == null) {
            return null;
        }

        return new VitalSigns.Builder()
                .setVitalId(vitalId)
                .setVisit(visit)
                .setTemperature(temperature)
                .setBloodPressure(bloodPressure)
                .setHeartRate(heartRate)
                .setWeight(weight)
                .setRecordedBy(recordedBy)
                .setRecordedAt(recordedAt)
                .build();
    }
}
