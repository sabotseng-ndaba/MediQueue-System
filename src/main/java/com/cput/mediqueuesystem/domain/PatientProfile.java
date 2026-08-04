package com.cput.mediqueuesystem.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/*
 * PatientProfile.java
 * Represents a patient's profile extension with allergies and
 * profile creation metadata.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Entity
@Table(name = "patient_profile")
public class PatientProfile {

    // Primary Key and Foreign Key to Patient
    @Id
    @Column(name = "patient_id")
    private String patientId;

    // The patient this profile belongs to
    @OneToOne
    @MapsId
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Allergies recorded for the patient
    @Column(name = "allergies")
    private String allergies;

    // Date and time the profile was created
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Default constructor required by JPA
    protected PatientProfile() {
    }

    // Constructor used by Builder
    private PatientProfile(Builder builder) {
        this.patientId = builder.patientId;
        this.patient = builder.patient;
        this.allergies = builder.allergies;
        this.createdAt = builder.createdAt;
    }

    // Getters

    public String getPatientId() {
        return patientId;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getAllergies() {
        return allergies;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Returns the PatientProfile object as a String
    @Override
    public String toString() {
        return "PatientProfile{" +
                "patientId='" + patientId + '\'' +
                ", patient=" + (patient != null ? patient.getUserId() : null) +
                ", allergies='" + allergies + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    /*
     * Builder class for PatientProfile.
     */
    public static class Builder {

        private String patientId;
        private Patient patient;
        private String allergies;
        private LocalDateTime createdAt;

        public Builder setPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder setPatient(Patient patient) {
            this.patient = patient;
            return this;
        }

        public Builder setAllergies(String allergies) {
            this.allergies = allergies;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder copy(PatientProfile patientProfile) {
            this.patientId = patientProfile.patientId;
            this.patient = patientProfile.patient;
            this.allergies = patientProfile.allergies;
            this.createdAt = patientProfile.createdAt;
            return this;
        }

        public PatientProfile build() {
            return new PatientProfile(this);
        }
    }
}
