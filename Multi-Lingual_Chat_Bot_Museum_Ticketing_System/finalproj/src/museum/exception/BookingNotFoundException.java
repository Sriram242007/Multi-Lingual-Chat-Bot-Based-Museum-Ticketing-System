package museum.exception;

/**
 * BookingNotFoundException.java
 * Exception thrown when a booking is not found
 *
 * Demonstrates:
 * - Custom exception for search/lookup failures
 *
 * Unit 3: User-defined exceptions
 *
 * @author Your Name
 * @version 1.0
 */
public class BookingNotFoundException extends MuseumException {

    /**
     * Booking ID that was not found
     */
    private String bookingId;

    /**
     * Constructor with message
     * @param message the error message
     */
    public BookingNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with message and booking ID
     * @param message the error message
     * @param bookingId the booking ID that was not found
     */
    public BookingNotFoundException(String message, String bookingId) {
        super(message);
        this.bookingId = bookingId;
    }

    /**
     * Get booking ID
     * @return the booking ID
     */
    public String getBookingId() {
        return bookingId;
    }
}