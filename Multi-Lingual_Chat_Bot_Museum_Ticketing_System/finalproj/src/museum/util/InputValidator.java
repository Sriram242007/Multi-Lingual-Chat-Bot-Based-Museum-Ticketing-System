package museum.util;

import museum.config.ApplicationConfig;

/**
 * InputValidator.java
 * Input validation utility with fixed validation rules
 *
 * @author Sriram S
 * @version 1.0
 */
public class InputValidator {

    /**
     * Validate email format (Fixed - less strict)
     */
    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Simple email validation
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Validate password (Fixed - minimum 4 characters instead of 6)
     */
    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }

        // Minimum 6 characters
        return password.length() >= ApplicationConfig.MIN_PASSWORD_LENGTH;
    }

    /**
     * Validate name (Fixed - minimum 2 characters)
     */
    public static boolean validateName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        // Minimum 2 characters
        return name.length() >= 2;
    }

    /**
     * Validate phone (Fixed - allow empty for registration)
     */
    public static boolean validatePhone(String phone) {
        // Phone is optional for registration
        if (phone == null || phone.isEmpty()) {
            return true; // Optional field
        }

        // If provided, must be 10 digits
        return phone.matches("\\d{10}");
    }

    /**
     * Validate quantity (1-100)
     */
    public static boolean validateQuantity(int quantity) {
        return quantity > 0 && quantity <= 100;
    }

    /**
     * Validate slot index
     */
    public static boolean validateSlotIndex(int index, int maxIndex) {
        return index >= 0 && index < maxIndex;
    }

    /**
     * Get validation error message
     */
    public static String getErrorMessage(String fieldName) {
        return switch (fieldName) {
            case "email" -> "Invalid email. Use format: username@domain.com";
            case "password" -> "Password must be at least 6 characters";
            case "name" -> "Name must be at least 2 characters";
            case "phone" -> "Phone must be 10 digits (or leave empty)";
            case "quantity" -> "Quantity must be between 1 and 100";
            default -> "Invalid input";
        };
    }
}