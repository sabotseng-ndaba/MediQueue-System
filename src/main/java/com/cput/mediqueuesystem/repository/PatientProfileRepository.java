package com.cput.mediqueuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cput.mediqueuesystem.domain.PatientProfile;

/*
 * PatientProfileRepository.java
 * JPA repository for PatientProfile entities.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, String> {
}
