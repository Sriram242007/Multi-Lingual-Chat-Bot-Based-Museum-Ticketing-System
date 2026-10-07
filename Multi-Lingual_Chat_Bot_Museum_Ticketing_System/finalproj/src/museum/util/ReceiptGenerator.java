package museum.util;

import museum.model.Booking;
import museum.model.Payment;
import museum.model.Receipt;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ReceiptGenerator.java
 * Generates formatted receipt strings using StringBuilder
 *
 * Demonstrates:
 * - StringBuilder for efficient string building
 * - Formatting and layout
 * - StringBuilder append operations
 *
 * @author Sriram S
 * @version 1.0
 */
public class ReceiptGenerator {

    /**
     * Generate a formatted receipt
     *
     * Demonstrates:
     * - StringBuilder for efficient string concatenation
     * - Multiple append operations
     * - String formatting
     *
     * @param booking the booking
     * @param payment the payment
     * @return formatted receipt string
     */
    public static String generateReceipt(Booking booking, Payment payment) {
        StringBuilder receipt = new StringBuilder();

        // Header
        receipt.append("========================================\n");
        receipt.append("       MUSEUM TICKET RECEIPT\n");
        receipt.append("========================================\n\n");

        // Booking details
        receipt.append(String.format("Booking ID       : %s\n", booking.getBookingId()));
        receipt.append(String.format("Receipt Date     : %s\n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))));
        receipt.append("\n");

        // Visitor details
        receipt.append("VISITOR DETAILS\n");
        receipt.append(String.format("Email            : %s\n", booking.getVisitorEmail()));
        receipt.append("\n");

        // Exhibit details
        receipt.append("EXHIBIT DETAILS\n");
        receipt.append(String.format("Exhibit          : %s\n", booking.getExhibitName()));
        receipt.append(String.format("Date             : %s\n", booking.getFormattedVisitDate()));
        receipt.append(String.format("Time Slot        : %s\n", booking.getTimeSlot()));
        receipt.append("\n");

        // Ticket details
        receipt.append("TICKET DETAILS\n");
        receipt.append(String.format("Number of Tickets: %d\n", booking.getTicketQuantity()));
        receipt.append(String.format("Price per Ticket : ₹%.2f\n", booking.getPricePerTicket()));
        receipt.append(String.format("Total Amount     : ₹%.2f\n", booking.getTotalAmount()));
        receipt.append("\n");

        // Payment details
        receipt.append("PAYMENT DETAILS\n");
        receipt.append(String.format("Transaction ID   : %s\n", payment.getTransactionId()));
        receipt.append(String.format("Payment Method   : %s\n", payment.getPaymentMethod()));
        receipt.append(String.format("Payment Status   : %s\n", payment.getStatus()));
        receipt.append(String.format("Payment Date     : %s\n", payment.getFormattedPaymentTime()));
        receipt.append("\n");

        // Booking status
        receipt.append("BOOKING STATUS\n");
        receipt.append(String.format("Status           : %s\n", booking.getStatus()));
        receipt.append("\n");

        // Footer
        receipt.append("========================================\n");
        receipt.append("       THANK YOU FOR VISITING!\n");
        receipt.append("========================================\n\n");
        receipt.append("Please keep this receipt for your records.\n");
        receipt.append("For more information, visit our museum.\n");

        return receipt.toString();
    }

    /**
     * Generate a simple confirmation receipt
     *
     * @param booking the booking
     * @return simple confirmation string
     */
    public static String generateConfirmation(Booking booking) {
        StringBuilder confirmation = new StringBuilder();

        confirmation.append("\n✓ BOOKING CONFIRMATION\n");
        confirmation.append("Booking ID: ").append(booking.getBookingId()).append("\n");
        confirmation.append("Exhibit: ").append(booking.getExhibitName()).append("\n");
        confirmation.append("Date: ").append(booking.getFormattedVisitDate()).append("\n");
        confirmation.append("Time: ").append(booking.getTimeSlot()).append("\n");
        confirmation.append("Tickets: ").append(booking.getTicketQuantity()).append("\n");
        confirmation.append("Total: ₹").append(String.format("%.2f", booking.getTotalAmount())).append("\n\n");

        return confirmation.toString();
    }

    /**
     * Generate booking summary
     *
     * @param booking the booking
     * @return summary string
     */
    public static String generateSummary(Booking booking) {
        StringBuilder summary = new StringBuilder();

        summary.append("\n--- BOOKING SUMMARY ---\n");
        summary.append("ID: ").append(booking.getBookingId()).append("\n");
        summary.append("Exhibit: ").append(booking.getExhibitName()).append("\n");
        summary.append("Visitor Email: ").append(booking.getVisitorEmail()).append("\n");
        summary.append("Date: ").append(booking.getFormattedVisitDate()).append("\n");
        summary.append("Time: ").append(booking.getTimeSlot()).append("\n");
        summary.append("Quantity: ").append(booking.getTicketQuantity()).append("\n");
        summary.append("Total: ₹").append(String.format("%.2f", booking.getTotalAmount())).append("\n");
        summary.append("Status: ").append(booking.getStatus()).append("\n");
        summary.append("-----------------------\n\n");

        return summary.toString();
    }
}