package com.cput.mediqueuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cput.mediqueuesystem.domain.Department;

/*
 * DepartmentRepository.java
 * JPA repository for Department entities.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
// Inherits CRUD operations (create, read, update, delete) from JpaRepository
}
