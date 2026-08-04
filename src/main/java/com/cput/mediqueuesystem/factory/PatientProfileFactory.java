package com.cput.mediqueuesystem.factory;

import java.time.LocalDateTime;

import org.apache.commons.validator.GenericValidator;

import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.domain.PatientProfile;

/*
 * PatientProfileFactory.java
 * Validates input and builds PatientProfile objects.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public class PatientProfileFactory {

    public static PatientProfile createPatientProfile(String patientId, Patient patient,
                                                       String allergies, LocalDateTime createdAt) {

        if (GenericValidator.isBlankOrNull(patientId) || patient == null || createdAt == null) {
            return null;
        }

        return new PatientProfile.Builder()
                .setPatientId(patientId)
                .setPatient(patient)
                .setAllergies(allergies)
                .setCreatedAt(createdAt)
                .build();
    }
}
