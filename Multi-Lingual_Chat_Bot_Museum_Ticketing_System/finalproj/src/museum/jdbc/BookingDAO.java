package museum.jdbc;

/**
 * BookingDAO.java
 * Data Access Object for Booking entities (Academic Demo)
 *
 * Demonstrates:
 * - DAO for complex entity
 * - Multi-table operations
 * - Booking-specific queries
 *
 * @author Sriram S
 * @version 1.0
 */
public class BookingDAO {

    private static final String TABLE_NAME = "bookings";

    /**
     * Create booking
     */
    public void create(String visitorEmail, String exhibitId, String visitDate,
                       int quantity, double totalAmount) {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (visitor_email, exhibit_id, visit_date, quantity, total_amount, status) " +
                "VALUES (?, ?, ?, ?, ?, 'PENDING')";

        System.out.println("\nCreate Booking DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameters:");
        System.out.println("  visitor_email: " + visitorEmail);
        System.out.println("  exhibit_id: " + exhibitId);
        System.out.println("  visit_date: " + visitDate);
        System.out.println("  quantity: " + quantity);
        System.out.println("  total_amount: ₹" + totalAmount);
    }

    /**
     * Get visitor bookings
     */
    public void findByVisitor(String visitorEmail) {
        String sql = "SELECT b.id, b.exhibit_id, b.visit_date, b.quantity, " +
                "b.total_amount, e.name AS exhibit_name " +
                "FROM " + TABLE_NAME + " b " +
                "JOIN exhibits e ON b.exhibit_id = e.id " +
                "WHERE b.visitor_email = ? " +
                "ORDER BY b.visit_date DESC";

        System.out.println("\nFind Visitor Bookings DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameter: visitor_email = " + visitorEmail);
        System.out.println("\nJoins: bookings with exhibits for complete info");
        System.out.println("Sorts: by visit_date in descending order");
    }

    /**
     * Confirm booking
     */
    public void confirm(String bookingId, String paymentId) {
        String sql = "UPDATE " + TABLE_NAME +
                " SET status = 'CONFIRMED', payment_id = ? " +
                "WHERE id = ?";

        System.out.println("\nConfirm Booking DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameters:");
        System.out.println("  payment_id: " + paymentId);
        System.out.println("  booking_id: " + bookingId);
    }

    /**
     * Cancel booking
     */
    public void cancel(String bookingId) {
        String sql = "UPDATE " + TABLE_NAME +
                " SET status = 'CANCELLED' WHERE id = ?";

        System.out.println("\nCancel Booking DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameter: booking_id = " + bookingId);
    }

    /**
     * Get booking statistics
     */
    public void getStatistics() {
        String sql = "SELECT " +
                "COUNT(*) AS total_bookings, " +
                "SUM(CASE WHEN status = 'CONFIRMED' THEN 1 ELSE 0 END) AS confirmed, " +
                "SUM(CASE WHEN status = 'CANCELLED' THEN 1 ELSE 0 END) AS cancelled, " +
                "SUM(total_amount) AS total_revenue " +
                "FROM " + TABLE_NAME;

        System.out.println("\nGet Statistics DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nCalculates:");
        System.out.println("  - Total bookings count");
        System.out.println("  - Confirmed bookings count");
        System.out.println("  - Cancelled bookings count");
        System.out.println("  - Total revenue");
    }

    /**
     * Check availability for time slot
     */
    public void checkAvailability(String exhibitId, String date, String timeSlot) {
        String sql = "SELECT COUNT(*) AS booked_tickets, ts.capacity " +
                "FROM " + TABLE_NAME + " b " +
                "JOIN time_slots ts ON b.time_slot_id = ts.id " +
                "WHERE b.exhibit_id = ? AND b.visit_date = ? " +
                "AND ts.start_time = ? " +
                "GROUP BY ts.capacity";

        System.out.println("\nCheck Availability DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameters:");
        System.out.println("  exhibit_id: " + exhibitId);
        System.out.println("  visit_date: " + date);
        System.out.println("  time_slot: " + timeSlot);
    }
}