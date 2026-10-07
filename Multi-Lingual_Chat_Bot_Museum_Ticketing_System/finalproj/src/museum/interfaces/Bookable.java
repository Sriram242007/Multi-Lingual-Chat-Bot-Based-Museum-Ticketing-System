package museum.interfaces;

import museum.model.Booking;
import museum.exception.InvalidInputException;
import museum.exception.SlotUnavailableException;
import java.time.LocalDate;

/**
 * Bookable.java
 * Interface defining booking capability
 *
 * Demonstrates:
 * - Defining an interface
 * - Method contracts without implementation
 * - Multiple classes can implement the same interface
 * - Interface segregation principle
 *
 * @author Sriram S
 * @version 1.0
 */
public interface Bookable {

    /**
     * Book a ticket for an exhibit
     *
     * @param visitorEmail visitor's email
     * @param exhibitId exhibit ID
     * @param visitDate visit date
     * @param timeSlotIndex time slot index
     * @param ticketQuantity number of tickets
     * @return the created booking
     * @throws InvalidInputException if input is invalid
     * @throws SlotUnavailableException if slot is full
     */
    Booking bookTickets(String visitorEmail, String exhibitId, LocalDate visitDate,
                        int timeSlotIndex, int ticketQuantity)
            throws InvalidInputException, SlotUnavailableException;

    /**
     * Get booking by ID
     *
     * @param bookingId the booking ID
     * @return the booking if found
     */
    Booking getBooking(String bookingId);

    /**
     * Cancel a booking
     *
     * @param bookingId the booking ID
     * @return true if cancelled
     */
    boolean cancelBooking(String bookingId) throws Exception;

    /**
     * Get total number of bookings
     *
     * @return number of bookings
     */
    int getTotalBookings();
}