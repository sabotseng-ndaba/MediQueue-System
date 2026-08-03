package com.cput.mediqueuesystem.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * MedicalRecord.java
 * Represents a patient's medical history entry, created by a
 * doctor, containing a diagnosis and clinical notes.
 *
 * Note: this links to the existing Patient and Staff entities
 * rather than the ERD's Patient_Profile/User, since those have
 * not been built yet. Reconcile once Patient_Profile exists.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Entity
@Table(name = "medical_record")
public class MedicalRecord {

    // Primary Key
    @Id
    @Column(name = "record_id")
    private String recordId;

    // The patient this record belongs to
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // The staff member (doctor) who created this record
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private Staff createdBy;

    // Diagnosis given by the doctor
    @Column(name = "diagnosis")
    private String diagnosis;

    // Additional clinical notes
    @Column(name = "notes")
    private String notes;

    // Date the record was created
    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    // Default constructor required by JPA
    protected MedicalRecord() {
    }

    // Constructor used by Builder
    private MedicalRecord(Builder builder) {
        this.recordId = builder.recordId;
        this.patient = builder.patient;
        this.createdBy = builder.createdBy;
        this.diagnosis = builder.diagnosis;
        this.notes = builder.notes;
        this.recordDate = builder.recordDate;
    }

    // Getters

    public String getRecordId() {
        return recordId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Staff getCreatedBy() {
        return createdBy;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    // Returns the MedicalRecord object as a String
    @Override
    public String toString() {
        return "MedicalRecord{" +
                "recordId='" + recordId + '\'' +
                ", patient=" + (patient != null ? patient.getUserId() : null) +
                ", createdBy=" + (createdBy != null ? createdBy.getUserId() : null) +
                ", diagnosis='" + diagnosis + '\'' +
                ", notes='" + notes + '\'' +
                ", recordDate=" + recordDate +
                '}';
    }

    /*
     * Builder class for MedicalRecord.
     */
    public static class Builder {

        private String recordId;
        private Patient patient;
        private Staff createdBy;
        private String diagnosis;
        private String notes;
        private LocalDate recordDate;

        public Builder setRecordId(String recordId) {
            this.recordId = recordId;
            return this;
        }

        public Builder setPatient(Patient patient) {
            this.patient = patient;
            return this;
        }

        public Builder setCreatedBy(Staff createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder setRecordDate(LocalDate recordDate) {
            this.recordDate = recordDate;
            return this;
        }

        public Builder copy(MedicalRecord medicalRecord) {
            this.recordId = medicalRecord.recordId;
            this.patient = medicalRecord.patient;
            this.createdBy = medicalRecord.createdBy;
            this.diagnosis = medicalRecord.diagnosis;
            this.notes = medicalRecord.notes;
            this.recordDate = medicalRecord.recordDate;
            return this;
        }

        public MedicalRecord build() {
            return new MedicalRecord(this);
        }
    }
}