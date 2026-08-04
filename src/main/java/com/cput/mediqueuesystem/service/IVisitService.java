package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.Visit;

/*
 * IVisitService.java
 * Service contract for Visit business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IVisitService {

    Visit create(Visit visit);

    Visit read(String visitId);

    Visit update(Visit visit);

    void delete(String visitId);

    List<Visit> getAll();
}
