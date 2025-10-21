package org.example.eventregapp.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    // Name validation pattern (letters, spaces, hyphens, apostrophes)
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[a-zA-Z\\s\\-']{2,50}$");

    /**
     * Validates email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validates full name format
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validates password strength
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        // Check for at least one letter and one number
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasNumber = password.matches(".*\\d.*");
        return hasLetter && hasNumber;
    }

    /**
     * Validates that a string is not empty and has minimum length
     */
    public static boolean isValidString(String str, int minLength) {
        return str != null && str.trim().length() >= minLength;
    }

    /**
     * Validates that a number is positive
     */
    public static boolean isValidPositiveNumber(String numberStr) {
        try {
            int number = Integer.parseInt(numberStr.trim());
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates date format (YYYY-MM-DD)
     */
    public static boolean isValidDateFormat(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        try {
            java.time.LocalDate.parse(dateStr.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validates that a date is in the future
     */
    public static boolean isValidFutureDate(String dateStr) {
        if (!isValidDateFormat(dateStr)) {
            return false;
        }
        try {
            java.time.LocalDate date = java.time.LocalDate.parse(dateStr.trim());
            return date.isAfter(java.time.LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets validation error message for email
     */
    public static String getEmailErrorMessage(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email is required";
        }
        if (!isValidEmail(email)) {
            return "Please enter a valid email address";
        }
        return null;
    }

    /**
     * Gets validation error message for name
     */
    public static String getNameErrorMessage(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Name is required";
        }
        if (!isValidName(name)) {
            return "Name must be 2-50 characters and contain only letters, spaces, hyphens, and apostrophes";
        }
        return null;
    }

    /**
     * Gets validation error message for password
     */
    public static String getPasswordErrorMessage(String password) {
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters long";
        }
        if (!isValidPassword(password)) {
            return "Password must contain at least one letter and one number";
        }
        return null;
    }
}
