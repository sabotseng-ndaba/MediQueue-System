package com.cput.mediqueuesystem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * Clinic.java
 * Represents a physical clinic location. Staff and queues are
 * associated with a specific clinic.
 *
 * Author: Uya
 * Date: 03 August 2026
 */

@Entity
@Table(name = "clinic")
public class Clinic {

    // Primary Key
    @Id
    @Column(name = "clinic_id")
    private String clinicId;

    // Name of the clinic, e.g. "District Six Clinic"
    @Column(name = "clinic_name", nullable = false)
    private String clinicName;

    // Physical location/address of the clinic
    @Column(name = "location")
    private String location;

    // Contact number for the clinic
    @Column(name = "contact_number")
    private String contactNumber;

    // Default constructor required by JPA
    protected Clinic() {
    }

    // Constructor used by Builder
    private Clinic(Builder builder) {
        this.clinicId = builder.clinicId;
        this.clinicName = builder.clinicName;
        this.location = builder.location;
        this.contactNumber = builder.contactNumber;
    }

    // Getters

    public String getClinicId() {
        return clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getLocation() {
        return location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    // Returns the Clinic object as a String
    @Override
    public String toString() {
        return "Clinic{" +
                "clinicId='" + clinicId + '\'' +
                ", clinicName='" + clinicName + '\'' +
                ", location='" + location + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }

    /*
     * Builder class for Clinic.
     */
    public static class Builder {

        private String clinicId;
        private String clinicName;
        private String location;
        private String contactNumber;

        public Builder setClinicId(String clinicId) {
            this.clinicId = clinicId;
            return this;
        }

        public Builder setClinicName(String clinicName) {
            this.clinicName = clinicName;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
            return this;
        }

        public Builder copy(Clinic clinic) {
            this.clinicId = clinic.clinicId;
            this.clinicName = clinic.clinicName;
            this.location = clinic.location;
            this.contactNumber = clinic.contactNumber;
            return this;
        }

        public Clinic build() {
            return new Clinic(this);
        }
    }
}