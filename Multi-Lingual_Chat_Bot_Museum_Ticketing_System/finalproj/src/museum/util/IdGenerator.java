package museum.util;

/**
 * IdGenerator.java
 * Utility class for generating unique IDs
 *
 * Demonstrates:
 * - Static members (static synchronized counter)
 * - Synchronized methods for thread safety
 * - Encapsulation with private constructor
 *
 * @author Sriram S
 * @version 1.0
 */
public class IdGenerator {

    /**
     * Static counters for different ID types
     * Synchronized to ensure thread-safe incrementing
     */
    private static long visitorCounter = 1000;
    private static long bookingCounter = 10000;
    private static long ticketCounter = 50000;
    private static long transactionCounter = 20000;
    private static long receiptCounter = 30000;

    /**
     * Private constructor to prevent instantiation
     * This is a utility class with only static methods
     */
    private IdGenerator() {
        throw new AssertionError("Cannot instantiate IdGenerator");
    }

    /**
     * Generate next visitor ID
     * Synchronized for thread safety
     *
     * @return next visitor ID
     */
    public static synchronized String nextVisitorId() {
        return "VIS" + (++visitorCounter);
    }

    /**
     * Generate next booking ID
     * Synchronized for thread safety
     *
     * @return next booking ID
     */
    public static synchronized String nextBookingId() {
        return "BK" + (++bookingCounter);
    }

    /**
     * Generate next ticket ID
     * Synchronized for thread safety
     *
     * @return next ticket ID
     */
    public static synchronized String nextTicketId() {
        return "TKT" + (++ticketCounter);
    }

    /**
     * Generate next transaction ID
     * Synchronized for thread safety
     *
     * @return next transaction ID
     */
    public static synchronized String nextTransactionId() {
        return "TXN" + (++transactionCounter);
    }

    /**
     * Generate next receipt ID
     * Synchronized for thread safety
     *
     * @return next receipt ID
     */
    public static synchronized String nextReceiptId() {
        return "RCP" + (++receiptCounter);
    }

    /**
     * Reset all counters (for testing purposes)
     */
    public static synchronized void resetCounters() {
        visitorCounter = 1000;
        bookingCounter = 10000;
        ticketCounter = 50000;
        transactionCounter = 20000;
        receiptCounter = 30000;
    }
}