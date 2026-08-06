package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.Patient;

/*
 * IPatientService.java
 * Service contract for Patient business logic.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public interface IPatientService extends IService<Patient, String> {
    List<Patient> getAll();
}
