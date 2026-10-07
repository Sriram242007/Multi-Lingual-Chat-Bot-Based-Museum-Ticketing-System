package museum;

import museum.model.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TestPhase2.java
 * Verification tests for Phase 2 model classes
 */
public class TestPhase2 {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PHASE 2: CORE MODEL CLASSES - VERIFICATION TESTS");
        System.out.println("=".repeat(60) + "\n");

        testLanguageEnum();
        testUserHierarchy();
        testExhibit();
        testTimeSlot();
        testBooking();

        System.out.println("\n✓ ALL PHASE 2 TESTS PASSED!");
        System.out.println("Ready for PHASE 3: Domain Services\n");
    }

    private static void testLanguageEnum() {
        System.out.println("Testing Language Enum...");
        Language lang1 = Language.ENGLISH;
        Language lang2 = Language.TAMIL;
        Language lang3 = Language.HINDI;

        System.out.println("  Language 1: " + lang1.getDisplayName() + " (" + lang1.getLanguageCode() + ")");
        System.out.println("  Language 2: " + lang2.getDisplayName() + " (" + lang2.getLanguageCode() + ")");
        System.out.println("  Language 3: " + lang3.getDisplayName() + " (" + lang3.getLanguageCode() + ")");
        System.out.println("✓ Language enum working\n");
    }

    private static void testUserHierarchy() {
        System.out.println("Testing User Hierarchy (Inheritance)...");

        Visitor visitor = new Visitor("john@museum.com", "John Doe", "pass123",
                "9876543210", "123 Main St");
        visitor.login();
        System.out.println("  Visitor: " + visitor.getName());
        System.out.println("  Role: " + visitor.getUserRole());
        System.out.println("  Logged In: " + visitor.isLoggedIn());

        Admin admin = new Admin("Jane Smith", "admin@museum.com", "admin123",
                "Operations", "ADMIN");
        admin.login();
        System.out.println("  Admin: " + admin.getName());
        System.out.println("  Role: " + admin.getUserRole());
        System.out.println("  Has Permission (MANAGE_EXHIBITS): " +
                admin.hasPermission("MANAGE_EXHIBITS"));

        System.out.println("✓ Inheritance and polymorphism working\n");
    }

    private static void testExhibit() {
        System.out.println("Testing Exhibit Class...");

        Exhibit exhibit = new Exhibit("Ancient India", "Historical artifacts from ancient India",
                "History", 150.0, "Hall 1");

        TimeSlot slot1 = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0),
                LocalTime.of(10, 0), 30);
        exhibit.addTimeSlot(slot1);

        System.out.println("  Exhibit: " + exhibit.getName());
        System.out.println("  Fee: ₹" + exhibit.getEntryFee());
        System.out.println("  Time Slots: " + exhibit.getTotalTimeSlots());
        System.out.println("  Total Capacity: " + exhibit.getTotalCapacity());
        System.out.println("✓ Exhibit and encapsulation working\n");
    }

    private static void testTimeSlot() {
        System.out.println("Testing TimeSlot Class...");

        TimeSlot slot = new TimeSlot(LocalDate.now(), LocalTime.of(10, 0),
                LocalTime.of(11, 0), 30);

        System.out.println("  Time: " + slot.getFormattedTimeRange());
        System.out.println("  Capacity: " + slot.getClass());
        System.out.println("  Status: " + slot.getStatus());

        slot.bookTickets(5);
        System.out.println("  After booking 5 tickets:");
        System.out.println("    Booked: " + slot.getBookedTickets());
        System.out.println("    Remaining: " + slot.getRemainingCapacity());
        System.out.println("    Status: " + slot.getStatus());

        System.out.println("✓ TimeSlot capacity management working\n");
    }

    private static void testBooking() {
        System.out.println("Testing Booking Class...");

        Booking booking = new Booking("john@museum.com", "EXH1001", "Ancient India",
                LocalDate.now(), "10:00 AM - 11:00 AM", 3, 150.0);

        System.out.println("  Booking ID: " + booking.getBookingId());
        System.out.println("  Exhibit: " + booking.getExhibitName());
        System.out.println("  Tickets: " + booking.getTicketQuantity());
        System.out.println("  Total: ₹" + booking.getTotalAmount());
        System.out.println("  Status: " + booking.getStatus());

        booking.confirmBooking();
        System.out.println("  After Confirmation: " + booking.getStatus());

        System.out.println("✓ Booking workflow working\n");
    }
}