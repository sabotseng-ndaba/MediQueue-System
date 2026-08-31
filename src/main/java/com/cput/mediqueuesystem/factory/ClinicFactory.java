package com.cput.mediqueuesystem.factory;

import org.apache.commons.validator.GenericValidator;

import com.cput.mediqueuesystem.domain.Clinic;

/*
 * ClinicFactory.java
 * Validates input and builds Clinic objects. Returns null if
 * required fields are missing or invalid, so callers can check
 * for a null result before persisting.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public class ClinicFactory {

    public static Clinic createClinic(String clinicId, String clinicName,
                                       String location, String contactNumber) {

        if (GenericValidator.isBlankOrNull(clinicId)
                || GenericValidator.isBlankOrNull(clinicName)) {
            return null;
        }

        return new Clinic.Builder()
                .setClinicId(clinicId)
                .setClinicName(clinicName)
                .setLocation(location)
                .setContactNumber(contactNumber)
                .build();
    }
}