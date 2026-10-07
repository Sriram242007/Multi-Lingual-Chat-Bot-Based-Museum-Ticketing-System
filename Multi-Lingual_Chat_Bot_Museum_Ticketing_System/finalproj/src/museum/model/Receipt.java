package museum.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Receipt.java
 * Represents a generated booking receipt
 *
 * Demonstrates:
 * - Receipt information structure
 * - Receipt status tracking
 * - Formatted output for display/printing
 *
 * @author Your Name
 * @version 1.0
 * @since Java 24
 */
public class Receipt {

    // ==================== PRIVATE FIELDS ====================

    /**
     * Unique receipt ID
     */
    private String receiptId;

    /**
     * Associated booking ID
     */
    private String bookingId;

    /**
     * Associated transaction ID
     */
    private String transactionId;

    /**
     * Visitor name
     */
    private String visitorName;

    /**
     * Exhibit name
     */
    private String exhibitName;

    /**
     * Visit date
     */
    private String visitDate;

    /**
     * Time slot
     */
    private String timeSlot;

    /**
     * Number of tickets
     */
    private int ticketQuantity;

    /**
     * Price per ticket
     */
    private double pricePerTicket;

    /**
     * Total amount
     */
    private double totalAmount;

    /**
     * Payment method
     */
    private String paymentMethod;

    /**
     * Payment status
     */
    private String paymentStatus;

    /**
     * Booking status
     */
    private String bookingStatus;

    /**
     * Receipt generation timestamp
     */
    private LocalDateTime generatedTime;

    // ==================== STATIC COUNTER ====================

    /**
     * Static counter for generating unique receipt IDs
     */
    private static int receiptCounter = 30000;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor for Receipt
     */
    public Receipt(String bookingId, String transactionId, String visitorName,
                   String exhibitName, String visitDate, String timeSlot,
                   int ticketQuantity, double pricePerTicket, double totalAmount,
                   String paymentMethod, String paymentStatus, String bookingStatus) {
        this.receiptId = "RCP" + (++receiptCounter);
        this.bookingId = bookingId;
        this.transactionId = transactionId;
        this.visitorName = visitorName;
        this.exhibitName = exhibitName;
        this.visitDate = visitDate;
        this.timeSlot = timeSlot;
        this.ticketQuantity = ticketQuantity;
        this.pricePerTicket = pricePerTicket;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.bookingStatus = bookingStatus;
        this.generatedTime = LocalDateTime.now();
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get formatted generation time
     * @return formatted timestamp
     */
    public String getFormattedGeneratedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return generatedTime.format(formatter);
    }

    /**
     * Get formatted total amount with currency
     * @return formatted amount
     */
    public String getFormattedTotalAmount() {
        return String.format("₹%.2f", totalAmount);
    }

    /**
     * Get formatted price per ticket
     * @return formatted price
     */
    public String getFormattedPricePerTicket() {
        return String.format("₹%.2f", pricePerTicket);
    }

    /**
     * Generate receipt as string (for printing/display)
     * @return formatted receipt string
     */
    public String generateReceiptString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("       MUSEUM TICKET RECEIPT\n");
        sb.append("========================================\n\n");
        sb.append(String.format("Booking ID       : %s\n", bookingId));
        sb.append(String.format("Receipt ID       : %s\n", receiptId));
        sb.append(String.format("Visitor          : %s\n", visitorName));
        sb.append(String.format("Exhibit          : %s\n", exhibitName));
        sb.append(String.format("Date             : %s\n", visitDate));
        sb.append(String.format("Time Slot        : %s\n", timeSlot));
        sb.append(String.format("Tickets          : %d\n", ticketQuantity));
        sb.append(String.format("Price/Ticket     : %s\n", getFormattedPricePerTicket()));
        sb.append(String.format("Total Amount     : %s\n", getFormattedTotalAmount()));
        sb.append(String.format("Payment Method   : %s\n", paymentMethod));
        sb.append(String.format("Payment Status   : %s\n", paymentStatus));
        sb.append(String.format("Booking Status   : %s\n", bookingStatus));
        sb.append(String.format("Transaction ID   : %s\n", transactionId));
        sb.append("\n========================================\n");
        sb.append("       THANK YOU FOR VISITING!\n");
        sb.append("========================================\n");
        return sb.toString();
    }

    /**
     * Convert receipt to file format
     * @return comma-separated receipt data
     */
    public String toFileFormat() {
        return receiptId + "," + bookingId + "," + transactionId + "," + visitorName + "," +
                exhibitName + "," + visitDate + "," + timeSlot + "," + ticketQuantity + "," +
                pricePerTicket + "," + totalAmount + "," + paymentMethod + "," + paymentStatus + "," +
                bookingStatus + "," + generatedTime;
    }

    /**
     * Override toString
     * @return receipt information
     */
    @Override
    public String toString() {
        return String.format("Receipt{id=%s, booking=%s, exhibit=%s, amount=₹%.2f}",
                receiptId, bookingId, exhibitName, totalAmount);
    }

    // ==================== GETTERS ====================

    public String getReceiptId() { return receiptId; }
    public String getBookingId() { return bookingId; }
    public String getTransactionId() { return transactionId; }
    public String getVisitorName() { return visitorName; }
    public String getExhibitName() { return exhibitName; }
    public String getVisitDate() { return visitDate; }
    public String getTimeSlot() { return timeSlot; }
    public int getTicketQuantity() { return ticketQuantity; }
    public double getPricePerTicket() { return pricePerTicket; }
    public double getTotalAmount() { return totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getBookingStatus() { return bookingStatus; }
    public LocalDateTime getGeneratedTime() { return generatedTime; }
}