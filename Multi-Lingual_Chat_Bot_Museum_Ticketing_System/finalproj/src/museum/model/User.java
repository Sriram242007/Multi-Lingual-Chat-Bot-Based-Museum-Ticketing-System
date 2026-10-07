package museum.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * User.java
 * Abstract base class for all users (Visitor and Admin)
 *
 * Demonstrates:
 * - Abstract class definition
 * - Encapsulation (private fields, public getters)
 * - Constructors with initialization
 * - Instance variables
 * - Access specifiers (public, private, protected)
 * - String data type
 * - Abstract methods (to be implemented by subclasses)
 * - Concrete methods (shared by all users)
 *
 * @author Your Name
 * @version 1.0
 * @since Java 24
 */
public abstract class User {

    // ==================== PRIVATE FIELDS ====================

    /**
     * Unique user identifier (email serves as ID)
     */
    private String email;

    /**
     * User's full name
     */
    private String name;

    /**
     * Encrypted/hashed password (in real system)
     * For this demo, stored as plain text locally
     */
    private String password;

    /**
     * Account creation timestamp
     */
    private LocalDateTime registrationDate;

    /**
     * User role type (VISITOR, ADMIN)
     */
    private String userType;

    /**
     * Account active status
     */
    private boolean isActive;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor for User (base initialization)
     *
     * @param email user's email (unique identifier)
     * @param name user's full name
     * @param password user's password
     * @param userType type of user (VISITOR or ADMIN)
     */
    protected User(String email, String name, String password, String userType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.userType = userType;
        this.registrationDate = LocalDateTime.now();
        this.isActive = true;
    }

    // ==================== ABSTRACT METHODS ====================

    /**
     * Abstract method: Login process
     * Each user type has different login logic
     */
    public abstract void login();

    /**
     * Abstract method: Logout process
     * Each user type has different logout logic
     */
    public abstract void logout();

    /**
     * Abstract method: Get user role permissions
     * Visitor and Admin have different permissions
     */
    public abstract String getUserRole();

    // ==================== CONCRETE METHODS ====================

    /**
     * Validate password meets minimum requirements
     * Demonstrates encapsulation and validation
     *
     * @param password the password to validate
     * @return true if password is valid
     */
    public boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }

    /**
     * Change user's password
     * Protected method - accessible to subclasses
     *
     * @param oldPassword current password for verification
     * @param newPassword new password to set
     * @return true if password changed successfully
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword) && validatePassword(newPassword)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    /**
     * Verify if provided password matches stored password
     *
     * @param providedPassword password to verify
     * @return true if password matches
     */
    public boolean verifyPassword(String providedPassword) {
        return this.password.equals(providedPassword);
    }

    /**
     * Deactivate user account
     */
    public void deactivateAccount() {
        this.isActive = false;
    }

    /**
     * Check if account is active
     * @return true if account is active
     */
    public boolean isAccountActive() {
        return isActive;
    }

    /**
     * Get formatted registration date
     * @return registration date as formatted string
     */
    public String getFormattedRegistrationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return registrationDate.format(formatter);
    }

    /**
     * Convert user to file-storable format
     * Used for persistence in data files
     *
     * @return comma-separated user data
     */
    public String toFileFormat() {
        return email + "," + name + "," + password + "," + userType + "," +
                registrationDate + "," + isActive;
    }

    /**
     * Override toString for display
     * @return user information string
     */
    @Override
    public String toString() {
        return String.format("User{email='%s', name='%s', userType='%s', registrationDate='%s'}",
                email, name, userType, getFormattedRegistrationDate());
    }

    /**
     * Check equality based on email (unique identifier)
     * @param obj object to compare
     * @return true if emails match
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return email.equals(user.email);
    }

    /**
     * Generate hash code based on email
     * @return hash code
     */
    @Override
    public int hashCode() {
        return email.hashCode();
    }

    // ==================== GETTERS ====================

    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getUserType() { return userType; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }

    // ==================== SETTERS ====================

    protected void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    protected void setUserType(String userType) {
        this.userType = userType;
    }
}