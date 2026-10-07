package museum.exception;

/**
 * InvalidInputException.java
 * Exception thrown when user input is invalid
 *
 * Demonstrates:
 * - Custom exception for validation errors
 * - Extends MuseumException
 *
 * @author Sriram S
 * @version 1.0
 */
public class InvalidInputException extends MuseumException {

    /**
     * Constructor with message
     * @param message the error message
     */
    public InvalidInputException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message the error message
     * @param cause the underlying cause
     */
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}