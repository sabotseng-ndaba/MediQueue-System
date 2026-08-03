package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.Prescription;

/*
 * IPrescriptionService.java
 * Service contract for Prescription business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IPrescriptionService {

    Prescription create(Prescription prescription);

    Prescription read(String prescriptionId);

    Prescription update(Prescription prescription);

    void delete(String prescriptionId);

    List<Prescription> getAll();
}
