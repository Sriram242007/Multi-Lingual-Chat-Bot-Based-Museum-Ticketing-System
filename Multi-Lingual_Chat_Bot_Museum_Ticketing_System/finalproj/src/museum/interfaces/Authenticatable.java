package museum.interfaces;

import museum.exception.InvalidLoginException;
import museum.exception.InvalidInputException;

/**
 * Authenticatable.java
 * Interface defining authentication capability
 *
 * Demonstrates:
 * - Defining authentication contract
 * - Multiple user types can authenticate differently
 *
 * @author Sriram S
 * @version 1.0
 */
public interface Authenticatable {

    /**
     * Login a user
     *
     * @param email user's email
     * @param password user's password
     * @return true if login successful
     * @throws InvalidLoginException if login fails
     */
    boolean login(String email, String password) throws InvalidLoginException;

    /**
     * Logout a user
     *
     * @param email user's email
     * @return true if logout successful
     */
    boolean logout(String email);

    /**
     * Register a new user
     *
     * @param email user's email
     * @param password user's password
     * @return true if registration successful
     * @throws InvalidInputException if input is invalid
     */
    boolean register(String email, String password) throws InvalidInputException;

    /**
     * Check if email is registered
     *
     * @param email email to check
     * @return true if registered
     */
    boolean isRegistered(String email);
}