package museum.interfaces;

import museum.model.Payment;

/**
 * Payable.java
 * Interface defining payment capability
 *
 * Demonstrates:
 * - Defining payment contract
 * - Separating payment logic from other concern
 *
 * @author Sriram S
 * @version 1.0
 */
public interface Payable {

    /**
     * Process a payment
     *
     * @param bookingId booking ID
     * @param amount payment amount
     * @param paymentMethod payment method (UPI, CARD, CASH)
     * @param payerEmail payer's email
     * @return the created payment
     */
    Payment processPayment(String bookingId, double amount,
                           String paymentMethod, String payerEmail);

    /**
     * Get payment by transaction ID
     *
     * @param transactionId transaction ID
     * @return the payment if found
     */
    Payment getPayment(String transactionId);

    /**
     * Process refund for a payment
     *
     * @param transactionId transaction ID
     * @return true if refund processed
     */
    boolean refundPayment(String transactionId);

    /**
     * Get total revenue
     *
     * @return total revenue from successful payments
     */
    double getTotalRevenue();
}