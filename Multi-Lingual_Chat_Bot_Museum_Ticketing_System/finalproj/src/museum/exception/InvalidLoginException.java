package museum.exception;

/**
 * InvalidLoginException.java
 * Exception thrown when login credentials are invalid
 *
 * Demonstrates:
 * - Custom exception for specific business logic error
 * - Extends MuseumException (exception hierarchy)
 *
 * @author Sriram S
 * @version 1.0
 */
public class InvalidLoginException extends MuseumException {

    /**
     * Constructor with message
     * @param message the error message
     */
    public InvalidLoginException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message the error message
     * @param cause the underlying cause
     */
    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}