package museum.service;

import museum.model.Visitor;
import museum.model.Admin;
import museum.model.User;
import museum.repository.UserRepository;
import museum.exception.InvalidLoginException;
import museum.exception.InvalidInputException;
import museum.util.InputValidator;

/**
 * AuthenticationService.java
 * Service for user authentication (registration and login)
 *
 * Demonstrates:
 * - Service-oriented architecture
 * - Business logic separation from data access
 * - Exception handling and throwing
 * - Input validation
 * - Repository usage
 *
 * Unit 1: Methods, String operations
 * Unit 3: Exception handling
 *
 * @author Your Name
 * @version 1.0
 */
public class AuthenticationService {

    /**
     * User repository for data access
     */
    private UserRepository userRepository;

    /**
     * Constructor
     * @param userRepository the user repository
     */
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new visitor
     *
     * Demonstrates:
     * - Input validation with exception throwing
     * - Business logic for duplicate checking
     * - Creating and saving entities
     *
     * @param email visitor's email
     * @param name visitor's name
     * @param password visitor's password
     * @param phoneNumber visitor's phone
     * @param address visitor's address
     * @return the created visitor
     * @throws InvalidInputException if input is invalid
     */
    public Visitor registerVisitor(String email, String name, String password,
                                   String phoneNumber, String address)
            throws InvalidInputException {

        // Validate all inputs
        if (!InputValidator.validateEmail(email)) {
            throw new InvalidInputException(InputValidator.getErrorMessage("email"));
        }
        if (!InputValidator.validateName(name)) {
            throw new InvalidInputException(InputValidator.getErrorMessage("name"));
        }
        if (!InputValidator.validatePassword(password)) {
            throw new InvalidInputException(InputValidator.getErrorMessage("password"));
        }
        if (!InputValidator.validatePhone(phoneNumber)) {
            throw new InvalidInputException(InputValidator.getErrorMessage("phone"));
        }


        // Check for duplicate email
        if (userRepository.exists(email)) {
            throw new InvalidInputException("Email already registered: " + email);
        }

        // Create visitor
        Visitor visitor = new Visitor(email, name, password, phoneNumber, address);

        // Save to repository
        userRepository.save(visitor);

        return visitor;
    }

    /**
     * Register a new admin
     *
     * @param name admin's name
     * @param email admin's email
     * @param password admin's password
     * @param department admin's department
     * @param permissionLevel admin's permission level
     * @return the created admin
     * @throws InvalidInputException if input is invalid
     */
    public Admin registerAdmin(String name, String email, String password,
                               String department, String permissionLevel)
            throws InvalidInputException {

        if (!InputValidator.validateEmail(email)) {
            throw new InvalidInputException(InputValidator.getErrorMessage("email"));
        }
        if (!InputValidator.validateName(name)) {
            throw new InvalidInputException(InputValidator.getErrorMessage("name"));
        }
        if (!InputValidator.validatePassword(password)) {
            throw new InvalidInputException(InputValidator.getErrorMessage("password"));
        }

        if (userRepository.exists(email)) {
            throw new InvalidInputException("Email already registered: " + email);
        }

        Admin admin = new Admin(name, email, password, department, permissionLevel);
        userRepository.save(admin);

        return admin;
    }

    /**
     * Login visitor with email and password
     *
     * Demonstrates:
     * - Finding user by ID (email)
     * - Verifying credentials
     * - Exception throwing for failed login
     * - Polymorphism with instanceof
     *
     * @param email visitor's email
     * @param password visitor's password
     * @return the logged-in visitor
     * @throws InvalidLoginException if credentials are invalid
     */
    public Visitor loginVisitor(String email, String password)
            throws InvalidLoginException {

        // Find user by email
        User user = userRepository.findById(email);

        // Check if user exists
        if (user == null) {
            throw new InvalidLoginException("User not found: " + email);
        }

        // Check if user is a visitor (not admin)
        if (!(user instanceof Visitor)) {
            throw new InvalidLoginException("This account is not a visitor account");
        }

        // Verify password
        if (!user.verifyPassword(password)) {
            throw new InvalidLoginException("Invalid password for email: " + email);
        }

        // Check account is active
        if (!user.isAccountActive()) {
            throw new InvalidLoginException("Account is deactivated: " + email);
        }

        // Perform login
        user.login();
        userRepository.update(user);

        return (Visitor) user;
    }

    /**
     * Login admin with email and password
     *
     * @param email admin's email
     * @param password admin's password
     * @return the logged-in admin
     * @throws InvalidLoginException if credentials are invalid
     */
    public Admin loginAdmin(String email, String password)
            throws InvalidLoginException {

        User user = userRepository.findById(email);

        if (user == null) {
            throw new InvalidLoginException("Admin not found: " + email);
        }

        if (!(user instanceof Admin)) {
            throw new InvalidLoginException("This account is not an admin account");
        }

        if (!user.verifyPassword(password)) {
            throw new InvalidLoginException("Invalid password for admin email: " + email);
        }

        if (!user.isAccountActive()) {
            throw new InvalidLoginException("Admin account is deactivated: " + email);
        }

        user.login();
        userRepository.update(user);

        return (Admin) user;
    }

    /**
     * Logout a user
     *
     * @param email user's email
     * @return true if logout successful
     */
    public boolean logout(String email) {
        User user = userRepository.findById(email);
        if (user != null) {
            user.logout();
            userRepository.update(user);
            return true;
        }
        return false;
    }

    /**
     * Change user password
     *
     * @param email user's email
     * @param oldPassword current password
     * @param newPassword new password
     * @return true if password changed
     * @throws InvalidLoginException if old password is wrong
     * @throws InvalidInputException if new password is invalid
     */
    public boolean changePassword(String email, String oldPassword, String newPassword)
            throws InvalidLoginException, InvalidInputException {

        User user = userRepository.findById(email);

        if (user == null) {
            throw new InvalidLoginException("User not found: " + email);
        }

        if (!InputValidator.validatePassword(newPassword)) {
            throw new InvalidInputException(InputValidator.getErrorMessage("password"));
        }

        boolean changed = user.changePassword(oldPassword, newPassword);
        if (!changed) {
            throw new InvalidLoginException("Old password is incorrect");
        }

        userRepository.update(user);
        return true;
    }

    /**
     * Check if email is already registered
     *
     * @param email the email to check
     * @return true if email exists
     */
    public boolean isEmailRegistered(String email) {
        return userRepository.exists(email);
    }

    /**
     * Get user by email
     *
     * @param email the user's email
     * @return the user if found, null otherwise
     */
    public User getUser(String email) {
        return userRepository.findById(email);
    }
}