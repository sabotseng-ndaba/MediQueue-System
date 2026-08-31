package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.Clinic;
import com.cput.mediqueuesystem.factory.ClinicFactory;
import com.cput.mediqueuesystem.repository.ClinicRepository;

/*
 * ClinicServiceImpl.java
 * Implements the business logic for managing clinics.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Service
public class ClinicService implements IClinicService {

    private final ClinicRepository clinicRepository;

    @Autowired
    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public Clinic create(Clinic clinic) {
        if (clinic == null) {
            return null;
        }
        Clinic validated = ClinicFactory.createClinic(
                clinic.getClinicId(), clinic.getClinicName(),
                clinic.getLocation(), clinic.getContactNumber());
        if (validated == null) {
            return null;
        }
        return clinicRepository.save(validated);
    }

    @Override
    public Clinic read(String clinicId) {
        return clinicRepository.findById(clinicId).orElse(null);
    }

    @Override
    public Clinic update(Clinic clinic) {
        if (!clinicRepository.existsById(clinic.getClinicId())) {
            return null;
        }
        return clinicRepository.save(clinic);
    }

    @Override
    public boolean delete(String clinicId) {
        if (!clinicRepository.existsById(clinicId)) {
            return false;
        }
        clinicRepository.deleteById(clinicId);
        return true;
    }

    @Override
    public List<Clinic> getAll() {
        return clinicRepository.findAll();
    }
}
