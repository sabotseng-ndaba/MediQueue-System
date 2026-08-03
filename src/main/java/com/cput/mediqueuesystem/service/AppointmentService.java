package com.cput.mediqueuesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cput.mediqueuesystem.domain.Appointment;
import com.cput.mediqueuesystem.factory.AppointmentFactory;
import com.cput.mediqueuesystem.repository.AppointmentRepository;

/*
 * AppointmentServiceImpl.java
 * Implements the business logic for managing appointments.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Service
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment create(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        Appointment validated = AppointmentFactory.createAppointment(
                appointment.getAppointmentId(), appointment.getPatient(), appointment.getDoctor(),
                appointment.getScheduledDate(), appointment.getScheduledTime(),
                appointment.getAppointmentType(), appointment.getStatus(), appointment.getCreatedBy());
        if (validated == null) {
            return null;
        }
        return appointmentRepository.save(validated);
    }

    @Override
    public Appointment read(String appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    @Override
    public Appointment update(Appointment appointment) {
        if (!appointmentRepository.existsById(appointment.getAppointmentId())) {
            return null;
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public void delete(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }
}