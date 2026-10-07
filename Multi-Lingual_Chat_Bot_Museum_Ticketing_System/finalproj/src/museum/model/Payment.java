package museum.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Payment.java
 * Represents a payment transaction for a booking
 *
 * Demonstrates:
 * - Payment information encapsulation
 * - Transaction tracking
 * - Payment status management
 *
 * @author Your Name
 * @version 1.0
 * @since Java 24
 */
public class Payment {

    // ==================== PRIVATE FIELDS ====================

    /**
     * Unique transaction ID
     */
    private String transactionId;

    /**
     * Associated booking ID
     */
    private String bookingId;

    /**
     * Payment amount in rupees
     */
    private double amount;

    /**
     * Payment method (UPI, CARD, CASH)
     */
    private String paymentMethod;

    /**
     * Payment status (PENDING, SUCCESS, FAILED, REFUNDED)
     */
    private String status;

    /**
     * Payment timestamp
     */
    private LocalDateTime paymentTime;

    /**
     * Payer details
     */
    private String payerEmail;

    /**
     * Payment description/notes
     */
    private String description;

    // ==================== STATIC COUNTER ====================

    /**
     * Static counter for generating unique transaction IDs
     */
    private static int transactionCounter = 20000;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor for Payment
     */
    public Payment(String bookingId, double amount, String paymentMethod, String payerEmail) {
        this.transactionId = "TXN" + (++transactionCounter);
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.payerEmail = payerEmail;
        this.status = "PENDING";
        this.paymentTime = LocalDateTime.now();
        this.description = "";
    }

    // ==================== PAYMENT OPERATIONS ====================

    /**
     * Mark payment as successful
     */
    public void markAsSuccess() {
        this.status = "SUCCESS";
    }

    /**
     * Mark payment as failed
     */
    public void markAsFailed() {
        this.status = "FAILED";
    }

    /**
     * Process refund
     */
    public void processRefund() {
        this.status = "REFUNDED";
    }

    /**
     * Get formatted payment time
     * @return formatted timestamp
     */
    public String getFormattedPaymentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return paymentTime.format(formatter);
    }
    /**
     * Get formatted amount with currency
     * @return formatted amount
     */
    public String getFormattedAmount() {
        return String.format("₹%.2f", amount);
    }

    /**
     * Convert payment to file format
     * @return comma-separated payment data
     */
    public String toFileFormat() {
        return transactionId + "," + bookingId + "," + amount + "," + paymentMethod + "," +
                status + "," + paymentTime + "," + payerEmail + "," + description;
    }

    /**
     * Override toString
     * @return payment information
     */
    @Override
    public String toString() {
        return String.format("Payment{id=%s, booking=%s, amount=₹%.2f, method=%s, status=%s}",
                transactionId, bookingId, amount, paymentMethod, status);
    }

    // ==================== GETTERS ====================

    public String getTransactionId() { return transactionId; }
    public String getBookingId() { return bookingId; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
    public LocalDateTime getPaymentTime() { return paymentTime; }
    public String getPayerEmail() { return payerEmail; }
    public String getDescription() { return description; }

    // ==================== SETTERS ====================

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }
}