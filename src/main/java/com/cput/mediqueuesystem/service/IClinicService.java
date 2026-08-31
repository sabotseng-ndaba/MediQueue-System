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

public interface IClinicService extends IService<Clinic, String> {

    List<Clinic> getAll();
}
