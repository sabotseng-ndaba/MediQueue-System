package com.cput.mediqueuesystem.factory;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cput.mediqueuesystem.domain.Patient;
import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.util.Helper;

/*
 * PatientFactory.java
 * Validates input and builds Patient objects.
 * Patient inherits the common User fields validated by UserFactory.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public class PatientFactory extends UserFactory {

    public static Patient createPatient(String userId, String firstName, String lastName,
                                        String email, String password, String phoneNumber,
                                        boolean status, LocalDateTime createdAt, Role role,
                                        String idNumber, LocalDate dateOfBirth, String gender,
                                        String address, String medicalAidNumber, String allergies) {

        if (!isValidUser(userId, firstName, lastName, email, password, phoneNumber, createdAt, role)
                || !Helper.isValidIdNumber(idNumber)
                || Helper.isNull(dateOfBirth)
                || Helper.isNullOrEmpty(gender)
                || Helper.isNullOrEmpty(address)) {
            return null;
        }

        Patient.Builder builder = new Patient.Builder();
        builder.setUserId(userId)
               .setFirstName(firstName)
               .setLastName(lastName)
               .setEmail(email)
               .setPassword(password)
               .setPhoneNumber(phoneNumber)
               .setStatus(status)
               .setCreatedAt(createdAt)
               .setRole(role);
        builder.setIdNumber(idNumber)
               .setDateOfBirth(dateOfBirth).setGender(gender)
               .setAddress(address)
               .setMedicalAidNumber(medicalAidNumber)
               .setAllergies(allergies);
        return builder.build();
    }
}
