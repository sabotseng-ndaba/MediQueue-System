package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.VitalSigns;

/*
 * IVitalSignsService.java
 * Service contract for VitalSigns business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IVitalSignsService {

    VitalSigns create(VitalSigns vitalSigns);

    VitalSigns read(String vitalId);

    VitalSigns update(VitalSigns vitalSigns);

    void delete(String vitalId);

    List<VitalSigns> getAll();
}
