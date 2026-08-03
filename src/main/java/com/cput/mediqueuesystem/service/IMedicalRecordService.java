package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.MedicalRecord;

/*
 * IMedicalRecordService.java
 * Service contract for MedicalRecord business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IMedicalRecordService {

    MedicalRecord create(MedicalRecord medicalRecord);

    MedicalRecord read(String recordId);

    MedicalRecord update(MedicalRecord medicalRecord);

    void delete(String recordId);

    List<MedicalRecord> getAll();
}
