package museum.exception;

/**
 * MuseumException.java
 * Base custom exception for the Museum Ticketing System
 *
 * Demonstrates:
 * - Exception hierarchy and custom exceptions
 * - Extends Exception for checked exceptions
 * - Constructors for exception creation
 *
 * @author Sriram S
 * @version 1.0
 */
public class MuseumException extends Exception {

    /**
     * Constructor with message only
     * @param message exception message
     */
    public MuseumException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message exception message
     * @param cause underlying cause
     */
    public MuseumException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause only
     * @param cause underlying cause
     */
    public MuseumException(Throwable cause) {
        super(cause);
    }
}