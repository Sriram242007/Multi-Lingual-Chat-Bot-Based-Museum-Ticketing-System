package museum.demo;

import java.time.LocalDate;

/**
 * RecordBasicsDemo.java
 * Demonstration of Java Records (Java 21)
 *
 * Demonstrates:
 * - Record declaration and components
 * - Automatic implementation of equals, hashCode, toString
 * - Canonical and compact constructors
 * - Records as immutable data carriers
 * - Comparing records
 *
 * @author SRIRAM S
 * @version 1.0
 */
public class RecordBasicsDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("JAVA RECORDS (JAVA 21)");
        System.out.println("=".repeat(70) + "\n");

        demonstrateRecordBasics();
        demonstrateRecordMethods();
        demonstrateRecordEquality();
        demonstrateCompactConstructor();

        System.out.println("\n✓ Records demonstration complete\n");
    }

    /**
     * Demonstrate record basics
     *
     * Demonstrates:
     * - Concise record syntax
     * - Automatic getter methods
     * - Immutability
     */
    private static void demonstrateRecordBasics() {
        System.out.println("1. RECORD BASICS");
        System.out.println("-".repeat(70));

        // Create record instances
        Ticket ticket1 = new Ticket("TKT50001", "Ancient India", LocalDate.now(), 150.0);
        Ticket ticket2 = new Ticket("TKT50002", "Modern Art", LocalDate.now(), 200.0);

        System.out.println("\nRecord: Ticket(id, exhibit, date, price)");
        System.out.println("  Ticket 1: " + ticket1);
        System.out.println("  Ticket 2: " + ticket2);

        System.out.println("\nAutomatic accessor methods:");
        System.out.println("  ticket1.id() = " + ticket1.id());
        System.out.println("  ticket1.exhibit() = " + ticket1.exhibit());
        System.out.println("  ticket1.date() = " + ticket1.date());
        System.out.println("  ticket1.price() = " + ticket1.price());

        System.out.println("\nRecords are immutable:");
        System.out.println("  ticket1.price = " + ticket1.price());
        System.out.println("  (Cannot modify - no setters!)");

        System.out.println("\nRecord benefits:");
        System.out.println("  - Concise syntax (no boilerplate)");
        System.out.println("  - Automatic toString()");
        System.out.println("  - Automatic equals() and hashCode()");
        System.out.println("  - Immutability by design");
        System.out.println("  - Perfect for data carriers");

        System.out.println();
    }

    /**
     * Demonstrate automatic record methods
     *
     * Demonstrates:
     * - toString() implementation
     * - hashCode() implementation
     * - equals() implementation
     */
    private static void demonstrateRecordMethods() {
        System.out.println("2. AUTOMATIC RECORD METHODS");
        System.out.println("-".repeat(70));

        Booking booking = new Booking("BK10001", "alice@museum.com", "Ancient India");

        System.out.println("\nAutomatic toString():");
        System.out.println("  " + booking);
        System.out.println("  (Automatically includes all components)");

        System.out.println("\nAutomatic hashCode():");
        System.out.println("  booking.hashCode() = " + booking.hashCode());
        System.out.println("  (Based on all components)");

        System.out.println("\nUsing record in HashMap:");
        java.util.Map<Booking, String> bookings = new java.util.HashMap<>();
        bookings.put(booking, "Confirmed");
        System.out.println("  Can use record as HashMap key (hashCode/equals work)");
        System.out.println("  Retrieved value: " + bookings.get(booking));

        System.out.println();
    }

    /**
     * Demonstrate record equality
     *
     * Demonstrates:
     * - Records with same values are equal
     * - Automatic equals() implementation
     */
    private static void demonstrateRecordEquality() {
        System.out.println("3. RECORD EQUALITY");
        System.out.println("-".repeat(70));

        // Create two tickets with same values
        Ticket ticket1 = new Ticket("TKT50001", "Ancient India", LocalDate.of(2026, 9, 16), 150.0);
        Ticket ticket2 = new Ticket("TKT50001", "Ancient India", LocalDate.of(2026, 9, 16), 150.0);
        Ticket ticket3 = new Ticket("TKT50002", "Modern Art", LocalDate.of(2026, 9, 16), 200.0);

        System.out.println("\nAutomatic equals() implementation:");
        System.out.println("  ticket1 = " + ticket1);
        System.out.println("  ticket2 = " + ticket2);
        System.out.println("  ticket3 = " + ticket3);

        System.out.println("\nEquality checks:");
        System.out.println("  ticket1.equals(ticket2) = " + ticket1.equals(ticket2));
        System.out.println("  (Same values → equal)");
        System.out.println("  ticket1.equals(ticket3) = " + ticket1.equals(ticket3));
        System.out.println("  (Different values → not equal)");

        System.out.println("\nUsing records in collections:");
        java.util.Set<Ticket> ticketSet = new java.util.HashSet<>();
        ticketSet.add(ticket1);
        ticketSet.add(ticket2);
        ticketSet.add(ticket3);

        System.out.println("  Added 3 tickets (2 duplicates)");
        System.out.println("  Set size: " + ticketSet.size());
        System.out.println("  (Automatically deduplicates equal records)");

        System.out.println();
    }

    /**
     * Demonstrate compact constructor
     *
     * Demonstrates:
     * - Validation in constructor
     * - Compact constructor syntax
     */
    private static void demonstrateCompactConstructor() {
        System.out.println("4. COMPACT CONSTRUCTOR");
        System.out.println("-".repeat(70));

        System.out.println("\nRecord with validation (compact constructor):");

        // Valid price
        try {
            Payment payment1 = new Payment("TXN20001", 500.0);
            System.out.println("  Valid payment: " + payment1);
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }

        // Invalid price
        try {
            Payment payment2 = new Payment("TXN20002", -100.0);
            System.out.println("  Created: " + payment2);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Rejected invalid payment: " + e.getMessage());
        }

        System.out.println("\nCompact constructor:");
        System.out.println("  - Runs before field assignment");
        System.out.println("  - Can validate parameters");
        System.out.println("  - Prevents invalid records");
        System.out.println("  - No need to assign fields (automatic)");

        System.out.println();
    }

    // ==================== RECORD DEFINITIONS ====================

    /**
     * Simple record - data carrier
     */
    record Ticket(String id, String exhibit, LocalDate date, double price) {
    }

    /**
     * Record for booking
     */
    record Booking(String id, String visitorEmail, String exhibit) {
    }

    /**
     * Record with compact constructor for validation
     */
    record Payment(String id, double amount) {
        // Compact constructor - validates before creating record
        public Payment {
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
        }
    }
}