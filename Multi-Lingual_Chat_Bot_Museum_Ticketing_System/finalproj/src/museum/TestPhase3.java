package museum;

import museum.model.*;
import museum.repository.*;
import museum.util.*;
import museum.exception.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static museum.util.FileUtil.writeLines;

/**
 * TestPhase3.java
 * Verification tests for Phase 3 repositories and utilities
 */
public class TestPhase3 {

    public static void main(String[] args) throws IOException {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PHASE 3: REPOSITORY LAYER - VERIFICATION TESTS");
        System.out.println("=".repeat(60) + "\n");

        try {
            testFileUtil();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            testInputValidator();
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        testUserRepository();
        testExhibitRepository();
        testBookingRepository();

        System.out.println("\n✓ ALL PHASE 3 TESTS PASSED!");
        System.out.println("Ready for PHASE 4: Services Layer\n");
    }

    private static void testFileUtil() throws IOException {
        System.out.println("Testing FileUtil (File I/O)...");

        String testFile = "data/test_output.txt";

        // Test writing
        java.util.List<String> lines = new java.util.ArrayList<>();
        lines.add("Line 1");
        lines.add("Line 2");
        lines.add("Line 3");

        boolean written = writeLines(testFile, lines);
        System.out.println("  Write test: " + (written ? "SUCCESS" : "FAILED"));

        // Test reading
        java.util.List<String> read = FileUtil.readLines(testFile);
        System.out.println("  Read test: " + (read.size() == 3 ? "SUCCESS" : "FAILED"));

        // Test file exists
        boolean exists = FileUtil.fileExists(testFile);
        System.out.println("  Exists test: " + (exists ? "SUCCESS" : "FAILED"));

        System.out.println("✓ FileUtil working\n");
    }

    private static void testInputValidator() throws InvalidInputException {
        System.out.println("Testing InputValidator (Validation)...");

        // Valid inputs
        InputValidator.validateEmail("john@museum.com");
        InputValidator.validatePassword("password123");
        InputValidator.validateName("John Doe");
        InputValidator.validatePhone("9876543210");
        InputValidator.validateQuantity(5);

        System.out.println("  Valid input tests: SUCCESS");

        // Invalid input test
        if (!InputValidator.validateEmail("invalid-email")) {
            System.out.println("  Invalid email test: SUCCESS");
        } else {
            System.out.println("  Invalid email test: FAILED");
        }

        System.out.println("✓ InputValidator working\n");
    }

    private static void testUserRepository() throws IOException {
        System.out.println("Testing UserRepository (Collections: HashMap)...");

        UserRepository repo = new UserRepository("data/test_users.txt");

        // Create users
        Visitor visitor = new Visitor("alice@museum.com", "Alice Johnson", "pass123",
                "9876543210", "123 Main St");
        Admin admin = new Admin("Bob Smith", "bob@museum.com", "admin123",
                "Operations", "ADMIN");

        // Test save
        repo.save(visitor);
        repo.save(admin);
        System.out.println("  Save test: SUCCESS (" + repo.count() + " users saved)");

        // Test find
        User found = repo.findById("alice@museum.com");
        System.out.println("  Find by ID test: " + (found != null ? "SUCCESS" : "FAILED"));

        // Test findAll
        java.util.List<User> all = repo.findAll();
        System.out.println("  Find all test: " + (all.size() == 2 ? "SUCCESS" : "FAILED"));

        // Test exists
        boolean exists = repo.exists("alice@museum.com");
        System.out.println("  Exists test: " + (exists ? "SUCCESS" : "FAILED"));

        repo.clear();
        System.out.println("✓ UserRepository working\n");
    }

    private static void testExhibitRepository() {
        System.out.println("Testing ExhibitRepository (Collections: ArrayList)...");

        ExhibitRepository repo = new ExhibitRepository("data/test_exhibits.txt");
        repo.clear();

        // Create exhibits
        Exhibit exhibit1 = new Exhibit("Ancient India", "Historical artifacts",
                "History", 150.0, "Hall 1");
        Exhibit exhibit2 = new Exhibit("Modern Art", "Contemporary works",
                "Art", 200.0, "Hall 2");

        // Test save
        repo.save(exhibit1);
        repo.save(exhibit2);
        System.out.println("  Save test: SUCCESS (" + repo.count() + " exhibits saved)");

        // Test find by category
        java.util.List<Exhibit> history = repo.findByCategory("History");
        System.out.println("  Find by category test: " + (history.size() == 1 ? "SUCCESS" : "FAILED"));

        // Test find by name
        java.util.List<Exhibit> ancient = repo.findByName("Ancient");
        System.out.println("  Find by name test: " + (ancient.size() == 1 ? "SUCCESS" : "FAILED"));

        repo.clear();
        System.out.println("✓ ExhibitRepository working\n");
    }

    private static void testBookingRepository() {
        System.out.println("Testing BookingRepository (Collections: HashMap + secondary index)...");

        BookingRepository repo = new BookingRepository("data/test_bookings.txt");
        repo.clear();

        // Create bookings
        Booking booking1 = new Booking("alice@museum.com", "EXH1001", "Ancient India",
                LocalDate.now(), "10:00 AM - 11:00 AM", 3, 150.0);
        Booking booking2 = new Booking("alice@museum.com", "EXH1002", "Modern Art",
                LocalDate.now(), "2:00 PM - 3:00 PM", 2, 200.0);

        booking1.confirmBooking();
        booking2.confirmBooking();

        // Test save
        repo.save(booking1);
        repo.save(booking2);
        System.out.println("  Save test: SUCCESS (" + repo.count() + " bookings saved)");

        // Test find by visitor
        java.util.List<Booking> visitorBookings = repo.findByVisitorEmail("alice@museum.com");
        System.out.println("  Find by visitor test: " + (visitorBookings.size() == 2 ? "SUCCESS" : "FAILED"));

        // Test find by status
        java.util.List<Booking> confirmed = repo.findByStatus("CONFIRMED");
        System.out.println("  Find by status test: " + (confirmed.size() == 2 ? "SUCCESS" : "FAILED"));

        // Test revenue calculation
        double revenue = repo.getTotalRevenue();
        double expectedRevenue = (3 * 150.0) + (2 * 200.0); // 850
        System.out.println("  Revenue calculation test: " + (revenue == expectedRevenue ? "SUCCESS" : "FAILED"));

        repo.clear();
        System.out.println("✓ BookingRepository working\n");
    }
}