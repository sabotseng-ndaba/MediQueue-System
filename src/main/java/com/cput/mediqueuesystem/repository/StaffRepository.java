package com.cput.mediqueuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cput.mediqueuesystem.domain.Staff;

/*
 * StaffRepository.java
 * JPA repository for Staff entities.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
// Inherits CRUD operations (create, read, update, delete) from JpaRepository
}
