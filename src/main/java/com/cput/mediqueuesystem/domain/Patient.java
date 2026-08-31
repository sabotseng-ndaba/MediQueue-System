package com.cput.mediqueuesystem.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/*
 * Patient.java
 * Patient entity that extends the User entity.
 * Stores patient-specific information.
 *
 * Author: Charmaine Dlamini
 * Date: 28 July 2026
 */

@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User {

    // South African ID number
    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;

    // Patient's date of birth
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    // Patient gender
    @Column(name = "gender", nullable = false)
    private String gender;

    // Residential address
    @Column(name = "address", nullable = false)
    private String address;

    // Medical aid number (optional)
    @Column(name = "medical_aid_number")
    private String medicalAidNumber;

    // Allergies recorded for the patient
    @Column(name = "allergies")
    private String allergies;

    // Default constructor required by JPA
    protected Patient() {
    }

    // Constructor used by Builder
    private Patient(Builder builder) {
        super(builder);
        this.idNumber = builder.idNumber;
        this.dateOfBirth = builder.dateOfBirth;
        this.gender = builder.gender;
        this.address = builder.address;
        this.medicalAidNumber = builder.medicalAidNumber;
        this.allergies = builder.allergies;
    }

    // Getters
  
    public String getIdNumber() {
        return idNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getMedicalAidNumber() {
        return medicalAidNumber;
    }

    public String getAllergies() {
        return allergies;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "userId='" + getUserId() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", medicalAidNumber='" + medicalAidNumber + '\'' +
                ", allergies='" + allergies + '\'' +
                '}';
    }

    /*
     * Builder class for Patient.
     */
    public static class Builder extends User.Builder {

        private String idNumber;
        private LocalDate dateOfBirth;
        private String gender;
        private String address;
        private String medicalAidNumber;
        private String allergies;

        public Builder setIdNumber(String idNumber) {
            this.idNumber = idNumber;
            return this;
        }

        public Builder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setMedicalAidNumber(String medicalAidNumber) {
            this.medicalAidNumber = medicalAidNumber;
            return this;
        }

        public Builder setAllergies(String allergies) {
            this.allergies = allergies;
            return this;
        }

        public Builder copy(Patient patient) {
            super.copy(patient);
            this.idNumber = patient.idNumber;
            this.dateOfBirth = patient.dateOfBirth;
            this.gender = patient.gender;
            this.address = patient.address;
            this.medicalAidNumber = patient.medicalAidNumber;
            this.allergies = patient.allergies;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }
}
