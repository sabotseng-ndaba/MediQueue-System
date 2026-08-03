package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.Clinic;

/*
 * IClinicService.java
 * Service contract for Clinic business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IClinicService {

    Clinic create(Clinic clinic);

    Clinic read(String clinicId);

    Clinic update(Clinic clinic);

    void delete(String clinicId);

    List<Clinic> getAll();
}
