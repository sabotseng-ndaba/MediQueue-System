package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.PatientProfile;

/*
 * IPatientProfileService.java
 * Service contract for PatientProfile business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IPatientProfileService {

    PatientProfile create(PatientProfile patientProfile);

    PatientProfile read(String patientId);

    PatientProfile update(PatientProfile patientProfile);

    void delete(String patientId);

    List<PatientProfile> getAll();
}
