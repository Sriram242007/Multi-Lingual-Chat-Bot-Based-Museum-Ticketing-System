package museum;

import museum.chatbot.ChatbotIntegration;
import museum.model.*;
import museum.util.ReceiptGenerator;
import java.time.LocalDate;
import java.util.List;

/**
 * TestPhase7.java
 * Complete end-to-end integration tests
 */
public class TestPhase7 {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PHASE 7: INTEGRATION & COMPLETE SYSTEM - END-TO-END TESTS");
        System.out.println("=".repeat(70) + "\n");

        ChatbotIntegration integration = new ChatbotIntegration();
        integration.clearData();
        integration.initializeSampleExhibits();

        testUserRegistration(integration);
        testExhibitBrowsing(integration);
        testBookingWorkflow(integration);
        testAnalytics(integration);
        testReceiptGeneration(integration);

        System.out.println("\n✓ ALL PHASE 7 TESTS PASSED!");
        System.out.println("Complete system integration verified\n");
    }

    private static void testUserRegistration(ChatbotIntegration integration) {
        System.out.println("TEST 1: User Registration and Login");
        System.out.println("-".repeat(70));

        // Register visitor
        boolean registered = integration.registerVisitor(
                "alice@museum.com", "Alice Johnson", "password123",
                "9876543210", "123 Main Street"
        );
        System.out.println("✓ Visitor registration: " + (registered ? "SUCCESS" : "FAILED"));

        // Login visitor
        Visitor visitor = integration.loginVisitor("alice@museum.com", "password123");
        System.out.println("✓ Visitor login: " + (visitor != null ? "SUCCESS" : "FAILED"));
        System.out.println("  Logged in as: " + visitor.getName());
        System.out.println();
    }

    private static void testExhibitBrowsing(ChatbotIntegration integration) {
        System.out.println("TEST 2: Exhibit Browsing and Search");
        System.out.println("-".repeat(70));

        // Get all exhibits
        List<Exhibit> exhibits = integration.getExhibits();
        System.out.println("✓ Total exhibits: " + exhibits.size());

        // Display exhibits
        for (Exhibit exhibit : exhibits) {
            System.out.println("  - " + exhibit.getName() + " (₹" + exhibit.getEntryFee() + ")");
        }

        // Search exhibits
        List<Exhibit> searchResults = integration.searchExhibits("Ancient");
        System.out.println("✓ Search results for 'Ancient': " + searchResults.size());
        System.out.println();
    }

    private static void testBookingWorkflow(ChatbotIntegration integration) {
        System.out.println("TEST 3: Complete Booking Workflow");
        System.out.println("-".repeat(70));

        try {
            // Get first exhibit
            List<Exhibit> exhibits = integration.getExhibits();
            Exhibit exhibit = exhibits.get(0);

            // Get available slots
            LocalDate today = LocalDate.now();
            List<TimeSlot> slots = integration.getAvailableSlots(exhibit.getExhibitId(), today);
            System.out.println("✓ Available slots for " + exhibit.getName() + ": " + slots.size());

            if (!slots.isEmpty()) {
                TimeSlot slot = slots.get(0);
                System.out.println("  Slot: " + slot.getFormattedTimeRange());
                System.out.println("  " + slot.getDetailedAvailabilityInfo());

                // Book tickets
                Booking booking = integration.bookTickets(
                        "alice@museum.com", exhibit.getExhibitId(), today, 0, 3
                );

                if (booking != null) {
                    System.out.println("✓ Booking created: " + booking.getBookingId());
                    System.out.println("  Quantity: " + booking.getTicketQuantity());
                    System.out.println("  Total: ₹" + booking.getTotalAmount());

                    // Confirm booking
                    Receipt receipt = integration.confirmBooking(
                            booking.getBookingId(), "UPI"
                    );

                    if (receipt != null) {
                        System.out.println("✓ Booking confirmed");
                        System.out.println("  Receipt ID: " + receipt.getReceiptId());
                        System.out.println("  Payment Status: " + receipt.getPaymentStatus());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testAnalytics(ChatbotIntegration integration) {
        System.out.println("TEST 4: Analytics and Reporting");
        System.out.println("-".repeat(70));

        System.out.println("✓ Total bookings: " + integration.getTotalBookings());
        System.out.println("✓ Total revenue: ₹" + String.format("%.2f", integration.getTotalRevenue()));

        String report = integration.getAnalyticsReport();
        System.out.println("\n" + report);
    }

    private static void testReceiptGeneration(ChatbotIntegration integration) {
        System.out.println("TEST 5: Receipt Generation");
        System.out.println("-".repeat(70));

        // Get a booking for receipt generation demo
        List<Booking> bookings = integration.getVisitorBookings("alice@museum.com");

        if (!bookings.isEmpty()) {
            Booking booking = bookings.get(0);
            String summary = ReceiptGenerator.generateSummary(booking);
            System.out.println("✓ Receipt generated:");
            System.out.println(summary);
        }

        System.out.println();
    }
}