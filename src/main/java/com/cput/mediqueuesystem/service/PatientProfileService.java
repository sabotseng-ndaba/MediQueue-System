package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.PatientProfile;
import com.cput.mediqueuesystem.factory.PatientProfileFactory;
import com.cput.mediqueuesystem.repository.PatientProfileRepository;

/*
 * PatientProfileService.java
 * Implements the business logic for managing patient profiles.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Service
public class PatientProfileService implements IPatientProfileService {

    private final PatientProfileRepository patientProfileRepository;

    @Autowired
    public PatientProfileService(PatientProfileRepository patientProfileRepository) {
        this.patientProfileRepository = patientProfileRepository;
    }

    @Override
    public PatientProfile create(PatientProfile patientProfile) {
        if (patientProfile == null) {
            return null;
        }
        PatientProfile validated = PatientProfileFactory.createPatientProfile(
                patientProfile.getPatientId(), patientProfile.getPatient(),
                patientProfile.getAllergies(), patientProfile.getCreatedAt());
        if (validated == null) {
            return null;
        }
        return patientProfileRepository.save(validated);
    }

    @Override
    public PatientProfile read(String patientId) {
        return patientProfileRepository.findById(patientId).orElse(null);
    }

    @Override
    public PatientProfile update(PatientProfile patientProfile) {
        if (!patientProfileRepository.existsById(patientProfile.getPatientId())) {
            return null;
        }
        return patientProfileRepository.save(patientProfile);
    }

    @Override
    public void delete(String patientId) {
        patientProfileRepository.deleteById(patientId);
    }

    @Override
    public List<PatientProfile> getAll() {
        return patientProfileRepository.findAll();
    }
}
