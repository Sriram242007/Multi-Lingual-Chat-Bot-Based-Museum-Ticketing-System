package museum.service;

import museum.model.Payment;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * PaymentService.java
 * Service for simulated payment processing
 *
 * Demonstrates:
 * - Simulated payment transactions
 * - In-memory payment tracking
 * - Collections: HashMap for payment storage
 * - Random number generation for simulation
 *
 * @author Sriram S
 * @version 1.0
 */
public class PaymentService {

    /**
     * In-memory payment storage
     * Demonstrates: HashMap for fast lookup by transaction ID
     */
    private Map<String, Payment> payments = new HashMap<>();

    /**
     * Random generator for payment simulation
     */
    private Random random = new Random();

    /**
     * Success rate for payment simulation (100% success for valid payments)
     */
    private static final double SUCCESS_RATE = 1.0;

    /**
     * Constructor
     */
    public PaymentService() {
    }

    /**
     * Process a payment transaction
     *
     * Demonstrates:
     * - Creating payment objects
     * - Simulating payment logic
     * - Random success/failure
     * - Storing payments
     *
     * @param bookingId the booking ID
     * @param amount the payment amount
     * @param paymentMethod the payment method (UPI, CARD, CASH)
     * @param payerEmail the payer's email
     * @return the created payment object
     */
    public Payment processPayment(String bookingId, double amount,
                                  String paymentMethod, String payerEmail) {

        // Create payment object
        Payment payment = new Payment(bookingId, amount, paymentMethod, payerEmail);

        // Simulate payment processing (add small delay for realism)
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulate success/failure based on success rate
        if (random.nextDouble() < SUCCESS_RATE) {
            payment.markAsSuccess();
        } else {
            payment.markAsFailed();
        }

        // Store payment
        payments.put(payment.getTransactionId(), payment);

        return payment;
    }

    /**
     * Get payment by transaction ID
     *
     * @param transactionId the transaction ID
     * @return the payment if found
     */
    public Payment getPayment(String transactionId) {
        return payments.get(transactionId);
    }

    /**
     * Get payment associated with a booking.
     *
     * @param bookingId booking ID
     * @return payment for the booking, or null if not found
     */
    public Payment getPaymentByBookingId(String bookingId) {
        for (Payment payment : payments.values()) {
            if (bookingId != null && bookingId.equals(payment.getBookingId())) {
                return payment;
            }
        }
        return null;
    }

    /**
     * Refund a payment
     *
     * @param transactionId the transaction ID
     * @return true if refund processed
     */
    public boolean refundPayment(String transactionId) {
        Payment payment = payments.get(transactionId);
        if (payment != null && payment.getStatus().equals("SUCCESS")) {
            payment.processRefund();
            return true;
        }
        return false;
    }

    /**
     * Get total revenue from successful payments
     *
     * Demonstrates:
     * - Iterating HashMap values
     * - Aggregating values
     *
     * @return total revenue
     */
    public double getTotalRevenue() {
        double total = 0;
        for (Payment payment : payments.values()) {
            if (payment.getStatus().equals("SUCCESS")) {
                total += payment.getAmount();
            }
        }
        return total;
    }

    /**
     * Get total successful payments count
     *
     * @return count of successful payments
     */
    public int getSuccessfulPaymentCount() {
        int count = 0;
        for (Payment payment : payments.values()) {
            if (payment.getStatus().equals("SUCCESS")) {
                count++;
            }
        }
        return count;
    }
}