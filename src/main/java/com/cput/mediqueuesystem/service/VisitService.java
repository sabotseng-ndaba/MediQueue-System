package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.Visit;
import com.cput.mediqueuesystem.factory.VisitFactory;
import com.cput.mediqueuesystem.repository.VisitRepository;

/*
 * VisitService.java
 * Implements the business logic for managing visits.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Service
public class VisitService implements IVisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit create(Visit visit) {
        if (visit == null) {
            return null;
        }
        Visit validated = VisitFactory.createVisit(
                visit.getVisitId(), visit.getPatient(), visit.getAppointment(),
                visit.getVisitDate(), visit.getCheckInTime(), visit.getCheckOutTime(), visit.getStatus());
        if (validated == null) {
            return null;
        }
        return visitRepository.save(validated);
    }

    @Override
    public Visit read(String visitId) {
        return visitRepository.findById(visitId).orElse(null);
    }

    @Override
    public Visit update(Visit visit) {
        if (!visitRepository.existsById(visit.getVisitId())) {
            return null;
        }
        return visitRepository.save(visit);
    }

    @Override
    public void delete(String visitId) {
        visitRepository.deleteById(visitId);
    }

    @Override
    public List<Visit> getAll() {
        return visitRepository.findAll();
    }
}
