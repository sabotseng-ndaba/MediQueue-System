package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.Appointment;

/*
 * IAppointmentService.java
 * Service contract for Appointment business logic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

public interface IAppointmentService {

    Appointment create(Appointment appointment);

    Appointment read(String appointmentId);

    Appointment update(Appointment appointment);

    void delete(String appointmentId);

    List<Appointment> getAll();
}
