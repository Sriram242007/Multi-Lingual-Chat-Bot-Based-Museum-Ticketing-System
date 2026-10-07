package museum.chatbot;

import museum.model.*;
import museum.repository.*;
import museum.service.*;
import museum.exception.*;
import museum.util.ReceiptGenerator;
import museum.util.FileUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * ChatbotIntegration.java
 * Integrates all services with chatbot for end-to-end operations
 *
 * Demonstrates:
 * - Service orchestration
 * - Complete workflow management
 * - Error handling at integration level
 * - Data flow between layers
 *
 * @author Sriram S
 * @version 1.0
 */
public class ChatbotIntegration {

    // ==================== SERVICE INSTANCES ====================

    private UserRepository userRepository;
    private ExhibitRepository exhibitRepository;
    private BookingRepository bookingRepository;

    private AuthenticationService authService;
    private ExhibitService exhibitService;
    private BookingService bookingService;
    private PaymentService paymentService;
    private AvailabilityService availabilityService;
    private ReportService reportService;

    private ConversationManager conversationManager;

    /**
     * Constructor - Initialize all services
     */
    public ChatbotIntegration() {
        initializeRepositories();
        initializeServices();
    }

    /**
     * Initialize repositories
     */
    private void initializeRepositories() {
        try {
            userRepository = new UserRepository("data/users.txt");
            exhibitRepository = new ExhibitRepository("data/exhibits.txt");
            bookingRepository = new BookingRepository("data/bookings.txt");
        } catch (Exception e) {
            throw new IllegalStateException("Unable to initialize museum data: " + e.getMessage(), e);
        }
    }

    /**
     * Initialize all services
     */
    private void initializeServices() {
        authService = new AuthenticationService(userRepository);
        exhibitService = new ExhibitService(exhibitRepository);
        // Use ONE PaymentService instance for booking confirmation and reporting.
        paymentService = new PaymentService();
        bookingService = new BookingService(bookingRepository, exhibitService, paymentService);
        availabilityService = new AvailabilityService(exhibitService);
        reportService = new ReportService(bookingRepository, exhibitService, paymentService);
    }

    /**
     * Clear all repository data
     */
    public void clearData() {
        if (userRepository != null) userRepository.clear();
        if (exhibitRepository != null) exhibitRepository.clear();
        if (bookingRepository != null) bookingRepository.clear();
    }

    /**
     * Get conversation manager
     */
    public ConversationManager getConversationManager() {
        if (conversationManager == null) {
            conversationManager = new ConversationManager();
        }
        return conversationManager;
    }

    // ==================== USER MANAGEMENT ====================

    /**
     * Register new visitor
     */
    public boolean registerVisitor(String email, String name, String password,
                                   String phone, String address) {
        try {
            Visitor visitor = authService.registerVisitor(email, name, password, phone, address);
            getConversationManager().getMessage("auth.register.success", name);
            return true;
        } catch (InvalidInputException e) {
            getConversationManager().getMessage("auth.register.fail", e.getMessage());
            return false;
        }
    }

    /**
     * Login visitor
     */
    public Visitor loginVisitor(String email, String password) {
        try {
            Visitor visitor = authService.loginVisitor(email, password);
            getConversationManager().setCurrentUser(visitor);
            return visitor;
        } catch (InvalidLoginException e) {
            return null;
        }
    }

    /**
     * Login admin
     */
    public Admin loginAdmin(String email, String password) {
        try {
            Admin admin = authService.loginAdmin(email, password);
            getConversationManager().setCurrentUser(admin);
            return admin;
        } catch (InvalidLoginException e) {
            return null;
        }
    }

    /**
     * Logout current user
     */
    public void logout() {
        if (getConversationManager().isLoggedIn()) {
            String email = getConversationManager().getCurrentUser().getEmail();
            authService.logout(email);
            getConversationManager().logout();
        }
    }

    // ==================== EXHIBIT MANAGEMENT ====================

    /**
     * Get all exhibits
     */
    public List<Exhibit> getExhibits() {
        return exhibitService.getAllExhibits();
    }

    /**
     * Search exhibits by name
     */
    public List<Exhibit> searchExhibits(String searchTerm) {
        return exhibitService.searchByName(searchTerm);
    }

    /**
     * Get exhibit details
     */
    public Exhibit getExhibitDetails(String exhibitId) {
        return exhibitService.getExhibit(exhibitId);
    }

    /**
     * Initialize sample exhibits (for demo)
     */
    public void initializeSampleExhibits() {
        try {
            // Clear existing
            exhibitRepository.clear();

            // Create exhibits
            Exhibit exhibit1 = exhibitService.createExhibit(
                    "Ancient India",
                    "Historical artifacts and sculptures from ancient India",
                    "History",
                    150.0,
                    "Hall 1"
            );

            Exhibit exhibit2 = exhibitService.createExhibit(
                    "Modern Art",
                    "Contemporary art and installations",
                    "Art",
                    200.0,
                    "Hall 2"
            );

            Exhibit exhibit3 = exhibitService.createExhibit(
                    "Natural History",
                    "Fossils, minerals, and natural wonders",
                    "Science",
                    100.0,
                    "Hall 3"
            );

            // Add time slots for today
            LocalDate today = LocalDate.now();

            // Morning slot
            exhibitService.addTimeSlot(exhibit1.getExhibitId(), today,
                    LocalTime.of(9, 0), LocalTime.of(10, 0), 30);

            // Afternoon slot
            exhibitService.addTimeSlot(exhibit1.getExhibitId(), today,
                    LocalTime.of(14, 0), LocalTime.of(15, 0), 30);

            // Add slots for other exhibits
            exhibitService.addTimeSlot(exhibit2.getExhibitId(), today,
                    LocalTime.of(10, 0), LocalTime.of(11, 0), 25);

            exhibitService.addTimeSlot(exhibit3.getExhibitId(), today,
                    LocalTime.of(11, 0), LocalTime.of(12, 0), 40);

        } catch (Exception e) {
            System.err.println("Error initializing exhibits: " + e.getMessage());
        }
    }

    // ==================== BOOKING MANAGEMENT ====================

    /**
     * Check availability
     */
    public List<TimeSlot> getAvailableSlots(String exhibitId, LocalDate date) {
        return availabilityService.getAvailableSlots(exhibitId, date);
    }

    /**
     * Book tickets
     */
    public Booking bookTickets(String visitorEmail, String exhibitId, LocalDate date,
                               int slotIndex, int quantity) {
        try {
            return bookingService.bookTickets(visitorEmail, exhibitId, date, slotIndex, quantity);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Confirm booking (process payment)
     */
    public Receipt confirmBooking(String bookingId, String paymentMethod) {
        try {
            bookingService.confirmBooking(bookingId, paymentMethod);

            // Get booking
            Booking booking = bookingService.getBooking(bookingId);
            if (booking != null) {
                booking.confirmBooking();
                if (bookingRepository != null) {
                    bookingRepository.confirmBooking(bookingId);
                }

                // Process payment via paymentService
                Payment payment = paymentService.processPayment(
                        booking.getBookingId(),
                        booking.getTotalAmount(),
                        paymentMethod != null ? paymentMethod : "UPI",
                        booking.getVisitorEmail()
                );

                if (payment != null) {
                    Receipt receipt = new Receipt(
                            booking.getBookingId(),
                            payment.getTransactionId(),
                            booking.getVisitorEmail(),
                            booking.getExhibitName(),
                            booking.getFormattedVisitDate(),
                            booking.getTimeSlot(),
                            booking.getTicketQuantity(),
                            booking.getPricePerTicket(),
                            booking.getTotalAmount(),
                            payment.getPaymentMethod(),
                            payment.getStatus(),
                            booking.getStatus()
                    );

                    // Save receipt to file
                    try {
                        String receiptContent = ReceiptGenerator.generateReceipt(booking, payment);
                        FileUtil.writeLines("data/receipts/" + receipt.getReceiptId() + ".txt",
                                java.util.Arrays.asList(receiptContent.split("\n")));
                    } catch (Exception ignored) {}

                    return receipt;
                }
            }

        } catch (Exception e) {
            System.err.println("Error confirming booking: " + e.getMessage());
        }

        return null;
    }

    /**
     * Get visitor bookings
     */
    public List<Booking> getVisitorBookings(String email) {
        return bookingService.getVisitorBookings(email);
    }

    /**
     * Cancel booking
     */
    public boolean cancelBooking(String bookingId) {
        try {
            return bookingService.cancelBooking(bookingId);
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== ANALYTICS ====================

    /**
     * Get analytics report
     */
    public String getAnalyticsReport() {
        return reportService.generateReport();
    }

    /**
     * Get total revenue
     */
    public double getTotalRevenue() {
        return reportService.getTotalRevenue();
    }

    /**
     * Get total bookings
     */
    public int getTotalBookings() {
        return reportService.getTotalBookings();
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Format booking for display
     */
    public String formatBooking(Booking booking) {
        return ReceiptGenerator.generateSummary(booking);
    }

    /**
     * Format receipt
     */
    public String formatReceipt(Booking booking, Payment payment) {
        return ReceiptGenerator.generateReceipt(booking, payment);
    }
}