package com.cput.mediqueuesystem.factory;

import java.time.LocalDateTime;

import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.util.Helper;

/*
 * UserFactory.java
 * Validates the common User fields shared by Patient and Staff.
 * PatientFactory and StaffFactory extend this class since both
 * inherit from User.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public abstract class UserFactory {

    // Validates the common fields required by every User
    protected static boolean isValidUser(String userId, String firstName, String lastName,
                                         String email, String password, String phoneNumber,
                                         LocalDateTime createdAt, Role role) {
        return !Helper.isNullOrEmpty(userId)
                && !Helper.isNullOrEmpty(firstName)
                && !Helper.isNullOrEmpty(lastName)
                && Helper.isValidEmail(email)
                && !Helper.isNullOrEmpty(password)
                && Helper.isValidPhoneNumber(phoneNumber)
                && !Helper.isNull(createdAt)
                && !Helper.isNull(role);
    }
}
