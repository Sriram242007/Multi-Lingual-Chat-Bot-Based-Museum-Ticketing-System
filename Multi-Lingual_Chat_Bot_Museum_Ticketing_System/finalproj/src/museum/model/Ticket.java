package museum.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Ticket.java
 * Represents a generated ticket for a booking
 *
 * Demonstrates:
 * - Ticket information encapsulation
 * - Ticket validation
 * - Unique ticket generation
 *
 * @author Your Name
 * @version 1.0
 * @since Java 24
 */
public class Ticket {

    // ==================== PRIVATE FIELDS ====================

    /**
     * Unique ticket ID
     */
    private String ticketId;

    /**
     * Associated booking ID
     */
    private String bookingId;

    /**
     * Ticket number (e.g., 1 of 3)
     */
    private int ticketNumber;

    /**
     * Total tickets in this booking
     */
    private int totalTickets;

    /**
     * Visitor name
     */
    private String visitorName;

    /**
     * Exhibit name
     */
    private String exhibitName;

    /**
     * Entry fee
     */
    private double entryFee;

    /**
     * QR code or token for entry
     */
    private String entryToken;

    /**
     * Ticket generation timestamp
     */
    private LocalDateTime generatedTime;

    /**
     * Ticket validity status
     */
    private boolean isValid;

    // ==================== STATIC COUNTER ====================

    /**
     * Static counter for generating unique ticket IDs
     */
    private static int ticketCounter = 50000;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor for Ticket
     */
    public Ticket(String bookingId, int ticketNumber, int totalTickets,
                  String visitorName, String exhibitName, double entryFee) {
        this.ticketId = "TKT" + (++ticketCounter);
        this.bookingId = bookingId;
        this.ticketNumber = ticketNumber;
        this.totalTickets = totalTickets;
        this.visitorName = visitorName;
        this.exhibitName = exhibitName;
        this.entryFee = entryFee;
        this.entryToken = generateEntryToken();
        this.generatedTime = LocalDateTime.now();
        this.isValid = true;
    }

    // ==================== TICKET OPERATIONS ====================

    /**
     * Generate unique entry token (simulated QR code)
     * @return entry token
     */
    private String generateEntryToken() {
        return ticketId + "-" + System.nanoTime();
    }

    /**
     * Validate and use the ticket
     * @return true if ticket can be used
     */
    public boolean validateAndUse() {
        if (isValid) {
            this.isValid = false;
            return true;
        }
        return false;
    }

    /**
     * Reactivate ticket (for refunds)
     */
    public void reactivate() {
        this.isValid = true;
    }

    /**
     * Get formatted generation time
     * @return formatted timestamp
     */
    public String getFormattedGeneratedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return generatedTime.format(formatter);
    }

    /**
     * Get formatted ticket number display (e.g., "1/3")
     * @return formatted ticket number
     */
    public String getFormattedTicketNumber() {
        return ticketNumber + "/" + totalTickets;
    }

    /**
     * Convert ticket to file format
     * @return comma-separated ticket data
     */
    public String toFileFormat() {
        return ticketId + "," + bookingId + "," + ticketNumber + "," + totalTickets + "," +
                visitorName + "," + exhibitName + "," + entryFee + "," + entryToken + "," +
                generatedTime + "," + isValid;
    }

    /**
     * Override toString
     * @return ticket information
     */
    @Override
    public String toString() {
        return String.format("Ticket{id=%s, booking=%s, visitor=%s, exhibit=%s, valid=%b}",
                ticketId, bookingId, visitorName, exhibitName, isValid);
    }

    // ==================== GETTERS ====================

    public String getTicketId() { return ticketId; }
    public String getBookingId() { return bookingId; }
    public int getTicketNumber() { return ticketNumber; }
    public int getTotalTickets() { return totalTickets; }
    public String getVisitorName() { return visitorName; }
    public String getExhibitName() { return exhibitName; }
    public double getEntryFee() { return entryFee; }
    public String getEntryToken() { return entryToken; }
    public LocalDateTime getGeneratedTime() { return generatedTime; }
    public boolean isValid() { return isValid; }
}