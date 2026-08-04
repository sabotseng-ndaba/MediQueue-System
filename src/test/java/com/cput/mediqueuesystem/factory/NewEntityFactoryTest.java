package com.cput.mediqueuesystem.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.cput.mediqueuesystem.domain.Appointment;
import com.cput.mediqueuesystem.domain.Clinic;
import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.domain.PatientProfile;
import com.cput.mediqueuesystem.domain.Queue;
import com.cput.mediqueuesystem.domain.QueueEntry;
import com.cput.mediqueuesystem.domain.Staff;
import com.cput.mediqueuesystem.domain.SymptomsAnalysis;
import com.cput.mediqueuesystem.domain.Visit;
import com.cput.mediqueuesystem.domain.VitalSigns;

class NewEntityFactoryTest {

    @Test
    void patientProfileFactoryRejectsMissingRequiredFields() {
        PatientProfile profile = PatientProfileFactory.createPatientProfile(null, null, "Peanuts", LocalDateTime.now());
        assertNull(profile);
    }

    @Test
    void visitFactoryRejectsMissingRequiredFields() {
        Visit visit = VisitFactory.createVisit(null, null, null, LocalDate.now(), LocalTime.now(), null, "pending");
        assertNull(visit);
    }

    @Test
    void queueFactoryRejectsMissingRequiredFields() {
        Queue queue = QueueFactory.createQueue("q1", null, LocalDate.now(), 10);
        assertNull(queue);
    }

    @Test
    void queueEntryFactoryRejectsMissingRequiredFields() {
        QueueEntry entry = QueueEntryFactory.createQueueEntry("e1", null, null, null, null, 1, "normal", "waiting", LocalTime.now());
        assertNull(entry);
    }

    @Test
    void vitalSignsFactoryRejectsMissingRequiredFields() {
        VitalSigns vitalSigns = VitalSignsFactory.createVitalSigns("v1", null, "36.7", "120/80", "72", "65", null, LocalDateTime.now());
        assertNull(vitalSigns);
    }

    @Test
    void symptomsAnalysisFactoryRejectsMissingRequiredFields() {
        SymptomsAnalysis analysis = SymptomsAnalysisFactory.createSymptomsAnalysis("a1", null, null, "flu", "cough", 0.95, LocalDateTime.now());
        assertNull(analysis);
    }

    @Test
    void validFactoriesProduceObjects() {
        Patient.Builder patientBuilder = new Patient.Builder();
        patientBuilder.setUserId("patient-1");
        Patient patient = patientBuilder.build();

        Clinic.Builder clinicBuilder = new Clinic.Builder();
        clinicBuilder.setClinicId("clinic-1");
        Clinic clinic = clinicBuilder.build();

        Appointment.Builder appointmentBuilder = new Appointment.Builder();
        appointmentBuilder.setAppointmentId("apt-1");
        Appointment appointment = appointmentBuilder.build();

        Staff.Builder doctorBuilder = new Staff.Builder();
        doctorBuilder.setUserId("staff-1");
        Staff doctor = doctorBuilder.build();

        PatientProfile profile = PatientProfileFactory.createPatientProfile("p1", patient, "Peanuts", LocalDateTime.now());
        Visit visit = VisitFactory.createVisit("v1", patient, appointment, LocalDate.now(), LocalTime.now(), null, "pending");
        Queue queue = QueueFactory.createQueue("q1", clinic, LocalDate.now(), 10);
        QueueEntry entry = QueueEntryFactory.createQueueEntry("e1", new Queue.Builder().setQueueId("q1").build(), patient, doctor, visit, 1, "normal", "waiting", LocalTime.now());
        VitalSigns vitalSigns = VitalSignsFactory.createVitalSigns("vit-1", visit, "36.7", "120/80", "72", "65", doctor, LocalDateTime.now());
        SymptomsAnalysis analysis = SymptomsAnalysisFactory.createSymptomsAnalysis("a1", patient, "Headache", "Migraine", "Rest", 0.95, LocalDateTime.now());

        assertNotNull(profile);
        assertNotNull(visit);
        assertNotNull(queue);
        assertNotNull(entry);
        assertNotNull(vitalSigns);
        assertNotNull(analysis);
    }
}
