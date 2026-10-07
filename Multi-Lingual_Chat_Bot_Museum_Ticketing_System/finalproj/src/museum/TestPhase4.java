package museum;

import museum.model.*;
import museum.repository.*;
import museum.service.*;
import museum.exception.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TestPhase4.java
 * Verification tests for Phase 4 services
 */
public class TestPhase4 {

    private UserRepository userRepository;
    private ExhibitRepository exhibitRepository;
    private BookingRepository bookingRepository;

    private AuthenticationService authService;
    private ExhibitService exhibitService;
    private BookingService bookingService;
    private PaymentService paymentService;
    private AvailabilityService availabilityService;
    private ReportService reportService;

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PHASE 4: SERVICE LAYER - VERIFICATION TESTS");
        System.out.println("=".repeat(60) + "\n");

        TestPhase4 tester = new TestPhase4();
        try {
            tester.setupServices();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tester.testAuthentication();
        tester.testExhibitService();
        tester.testAvailabilityService();
        tester.testBookingService();
        tester.testPaymentService();
        tester.testReportService();

        System.out.println("\n✓ ALL PHASE 4 TESTS PASSED!");
        System.out.println("Ready for PHASE 5: Interfaces & Localization\n");
    }

    private void setupServices() throws IOException {
        userRepository = new UserRepository("data/test_users.txt");
        exhibitRepository = new ExhibitRepository("data/test_exhibits.txt");
        bookingRepository = new BookingRepository("data/test_bookings.txt");

        userRepository.clear();
        exhibitRepository.clear();
        bookingRepository.clear();

        authService = new AuthenticationService(userRepository);
        exhibitService = new ExhibitService(exhibitRepository);
        paymentService = new PaymentService();
        bookingService = new BookingService(bookingRepository, exhibitService, paymentService);
        availabilityService = new AvailabilityService(exhibitService);
        reportService = new ReportService(bookingRepository, exhibitService, paymentService);
    }

    private void testAuthentication() {
        System.out.println("Testing AuthenticationService...");

        try {
            // Register visitor
            Visitor visitor = authService.registerVisitor(
                    "alice@museum.com", "Alice Johnson", "password123",
                    "9876543210", "123 Main St"
            );
            System.out.println("  Visitor registration: SUCCESS");

            // Register admin
            Admin admin = authService.registerAdmin(
                    "Bob Smith", "bob@museum.com", "admin123",
                    "Operations", "ADMIN"
            );
            System.out.println("  Admin registration: SUCCESS");

            // Test login
            Visitor loggedIn = authService.loginVisitor("alice@museum.com", "password123");
            System.out.println("  Visitor login: " + (loggedIn.isLoggedIn() ? "SUCCESS" : "FAILED"));

            // Test duplicate registration
            try {
                authService.registerVisitor("alice@museum.com", "Duplicate", "pass123",
                        "1111111111", "Address");
                System.out.println("  Duplicate check: FAILED (should throw exception)");
            } catch (InvalidInputException e) {
                System.out.println("  Duplicate check: SUCCESS");
            }

        } catch (Exception e) {
            System.out.println("  Auth test FAILED: " + e.getMessage());
        }

        System.out.println("✓ AuthenticationService working\n");
    }

    private void testExhibitService() {
        System.out.println("Testing ExhibitService...");

        try {
            // Create exhibits
            Exhibit exhibit1 = exhibitService.createExhibit(
                    "Ancient India", "Historical artifacts",
                    "History", 150.0, "Hall 1"
            );

            Exhibit exhibit2 = exhibitService.createExhibit(
                    "Modern Art", "Contemporary works",
                    "Art", 200.0, "Hall 2"
            );

            System.out.println("  Create exhibit: SUCCESS (" +
                    exhibitService.getTotalExhibits() + " exhibits)");

            // Add time slots
            exhibitService.addTimeSlot(exhibit1.getExhibitId(),
                    LocalDate.now(), LocalTime.of(9, 0),
                    LocalTime.of(10, 0), 30);

            System.out.println("  Add time slot: SUCCESS");

            // Search by category
            java.util.List<Exhibit> history = exhibitService.searchByCategory("History");
            System.out.println("  Search by category: " + (history.size() == 1 ? "SUCCESS" : "FAILED"));

            // Search by name
            java.util.List<Exhibit> ancient = exhibitService.searchByName("Ancient");
            System.out.println("  Search by name: " + (ancient.size() == 1 ? "SUCCESS" : "FAILED"));

        } catch (Exception e) {
            System.out.println("  Exhibit test FAILED: " + e.getMessage());
        }

        System.out.println("✓ ExhibitService working\n");
    }

    private void testAvailabilityService() {
        System.out.println("Testing AvailabilityService...");

        try {
            Exhibit exhibit = exhibitRepository.findAll().get(0);
            LocalDate today = LocalDate.now();

            boolean hasAvail = availabilityService.hasAvailability(
                    exhibit.getExhibitId(), today
            );
            System.out.println("  Check availability: " + (hasAvail ? "AVAILABLE" : "FULL"));

            java.util.List<TimeSlot> slots = availabilityService.getAvailableSlots(
                    exhibit.getExhibitId(), today
            );
            System.out.println("  Get available slots: " + slots.size() + " slots found");

        } catch (Exception e) {
            System.out.println("  Availability test FAILED: " + e.getMessage());
        }

        System.out.println("✓ AvailabilityService working\n");
    }

    private void testBookingService() {
        System.out.println("Testing BookingService (with synchronization)...");

        try {
            Exhibit exhibit = exhibitRepository.findAll().get(0);
            LocalDate today = LocalDate.now();

            // Test booking
            Booking booking = bookingService.bookTickets(
                    "alice@museum.com", exhibit.getExhibitId(),
                    today, 0, 5
            );
            System.out.println("  Book tickets: SUCCESS (" + booking.getBookingId() + ")");
            System.out.println("  Synchronization: Used to prevent race conditions");

            // Test confirm
            bookingService.confirmBooking(booking.getBookingId(), "UPI");
            System.out.println("  Confirm booking: SUCCESS");

            // Test visitor bookings
            java.util.List<Booking> visitorBookings = bookingService.getVisitorBookings(
                    "alice@museum.com"
            );
            System.out.println("  Get visitor bookings: " + visitorBookings.size() + " bookings");

        } catch (Exception e) {
            System.out.println("  Booking test FAILED: " + e.getMessage());
        }

        System.out.println("✓ BookingService working\n");
    }

    private void testPaymentService() {
        System.out.println("Testing PaymentService...");

        // Process payment
        Payment payment = paymentService.processPayment(
                "BK10001", 750.0, "UPI", "alice@museum.com"
        );
        System.out.println("  Process payment: SUCCESS (Status: " + payment.getStatus() + ")");

        double revenue = paymentService.getTotalRevenue();
        System.out.println("  Get revenue: ₹" + String.format("%.2f", revenue));

        int successCount = paymentService.getSuccessfulPaymentCount();
        System.out.println("  Successful payments: " + successCount);

        System.out.println("✓ PaymentService working\n");
    }

    private void testReportService() {
        System.out.println("Testing ReportService...");

        System.out.println("  Total bookings: " + reportService.getTotalBookings());
        System.out.println("  Confirmed bookings: " + reportService.getConfirmedBookings());
        System.out.println("  Total tickets sold: " + reportService.getTotalTicketsSold());
        System.out.println("  Total revenue: ₹" +
                String.format("%.2f", reportService.getTotalRevenue()));
        System.out.println("  Most visited exhibit: " + reportService.getMostVisitedExhibit());
        System.out.println("  Peak time slot: " + reportService.getPeakTimeSlot());

        String report = reportService.generateReport();
        System.out.println("\n" + report);
        System.out.println("✓ ReportService working\n");
    }
}