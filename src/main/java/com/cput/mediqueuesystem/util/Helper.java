package com.cput.mediqueuesystem.util;

/*
 * Helper.java
 * Helper class used to validate input data.
 *
 * Author: Charmaine Dlamini
 * Date: 02 August 2026
 */

public class Helper {

    // Checks if a String is null or empty
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Validates an email address using Regex
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    // Checks if an object is null
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    // Checks if an integer is greater than zero
    public static boolean isValidInt(int value) {
        return value > 0;
    }

    // Checks if a double value is positive
    public static boolean isPositive(double value) {
        return value > 0;
    }

    // Validates a South African ID number (13 digits)
    public static boolean isValidIdNumber(String idNumber) {
        if (isNullOrEmpty(idNumber)) {
            return false;
        }

        return idNumber.matches("\\d{13}");
    }

    // Validates a South African phone number
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return false;
        }

        // Accepts numbers such as 0821234567, 0719876543, 0601234567
        return phoneNumber.matches("^0[6-8][0-9]{8}$");
    }
}