package museum.demo;

import java.time.LocalDate;

/**
 * RecordPatternDemo.java
 * Demonstration of Record Patterns in switch (Java 21)
 *
 * Demonstrates:
 * - Destructuring records in patterns
 * - Accessing record components in switch
 * - Nested record patterns
 * - Combining with guard clauses
 *
 * @author Sriram S
 * @version 1.0
 */
public class RecordPatternDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("RECORD PATTERNS (JAVA 21)");
        System.out.println("=".repeat(70) + "\n");

        demonstrateRecordDestructuring();
        demonstrateRecordPatternWithGuards();
        demonstrateNestedRecordPatterns();

        System.out.println("\n✓ Record pattern demonstration complete\n");
    }

    /**
     * Demonstrate destructuring records in patterns
     *
     * Demonstrates:
     * - Extracting record components in switch
     * - Pattern matching on record fields
     */
    private static void demonstrateRecordDestructuring() {
        System.out.println("1. RECORD DESTRUCTURING");
        System.out.println("-".repeat(70));

        Object[] events = {
                new BookingEvent("alice@museum.com", 150.0, "Ancient India"),
                new CancellationEvent("bob@museum.com", 200.0),
                new PaymentEvent("charlie@museum.com", 100.0, "UPI"),
                new BookingEvent("diana@museum.com", 175.0, "Modern Art")
        };

        System.out.println("\nDestructuring records in switch:\n");

        for (Object event : events) {
            String description = switch (event) {
                // Destructure BookingEvent record
                case BookingEvent(String email, double amount, String exhibit) ->
                        "Booking: " + email + " for " + exhibit + " (₹" + amount + ")";

                // Destructure CancellationEvent record
                case CancellationEvent(String email, double refund) ->
                        "Cancellation: " + email + " refund ₹" + refund;

                // Destructure PaymentEvent record
                case PaymentEvent(String email, double amount, String method) ->
                        "Payment: " + email + " via " + method + " (₹" + amount + ")";

                default ->
                        "Unknown event";
            };

            System.out.println("  " + description);
        }

        System.out.println("\nRecord pattern benefits:");
        System.out.println("  - Direct access to record components");
        System.out.println("  - No manual field extraction");
        System.out.println("  - Type-safe pattern matching");
        System.out.println("  - Cleaner than traditional approach");

        System.out.println();
    }

    /**
     * Demonstrate record patterns with guard clauses
     *
     * Demonstrates:
     * - Combining record patterns with guards
     * - Complex pattern matching logic
     */
    private static void demonstrateRecordPatternWithGuards() {
        System.out.println("2. RECORD PATTERNS WITH GUARDS");
        System.out.println("-".repeat(70));

        Object[] tickets = {
                new TicketInfo("TKT50001", 150.0, "Ancient India"),
                new TicketInfo("TKT50002", 50.0, "Modern Art"),
                new TicketInfo("TKT50003", 200.0, "Natural History"),
                new TicketInfo("TKT50004", 100.0, "Sculpture"),
        };

        System.out.println("\nRecord patterns with guards (when conditions):\n");

        for (Object ticket : tickets) {
            String category = switch (ticket) {
                // Premium ticket (price > 150)
                case TicketInfo(String id, double price, String exhibit) when price > 150 ->
                        "Premium: " + exhibit + " (₹" + price + ")";

                // Budget ticket (price <= 100)
                case TicketInfo(String id, double price, String exhibit) when price <= 100 ->
                        "Budget: " + exhibit + " (₹" + price + ")";

                // Standard ticket (100 < price <= 150)
                case TicketInfo(String id, double price, String exhibit) ->
                        "Standard: " + exhibit + " (₹" + price + ")";
                default -> throw new IllegalStateException("Unexpected value: " + ticket);
            };

            System.out.println("  " + category);
        }

        System.out.println("\nGuard clauses with record patterns:");
        System.out.println("  - Access record components in guard");
        System.out.println("  - Complex conditional logic");
        System.out.println("  - Multiple patterns for same record type");

        System.out.println();
    }

    /**
     * Demonstrate nested record patterns
     *
     * Demonstrates:
     * - Records containing other records
     * - Destructuring nested structures
     * - Hierarchical pattern matching
     */
    private static void demonstrateNestedRecordPatterns() {
        System.out.println("3. NESTED RECORD PATTERNS");
        System.out.println("-".repeat(70));

        Object[] orders = {
                new Order(
                        "ORD001",
                        new TicketInfo("TKT50001", 150.0, "Ancient India"),
                        new Visitor("alice@museum.com", "Alice")
                ),
                new Order(
                        "ORD002",
                        new TicketInfo("TKT50002", 100.0, "Modern Art"),
                        new Visitor("bob@museum.com", "Bob")
                ),
                new Order(
                        "ORD003",
                        new TicketInfo("TKT50003", 200.0, "Natural History"),
                        new Visitor("charlie@museum.com", "Charlie")
                )
        };

        System.out.println("\nNested record destructuring:\n");

        for (Object order : orders) {
            String details = switch (order) {
                // Destructure nested records
                case Order(String orderId,
                           TicketInfo(String ticketId, double price, String exhibit),
                           Visitor(String email, String name)) when price > 150 ->
                        "Premium Order " + orderId + ": " + name + " for " + exhibit;

                case Order(String orderId,
                           TicketInfo(String ticketId, double price, String exhibit),
                           Visitor(String email, String name)) ->
                        "Standard Order " + orderId + ": " + name + " for " + exhibit;

                default ->
                        "Unknown order";
            };

            System.out.println("  " + details);
        }

        System.out.println("\nNested record patterns:");
        System.out.println("  - Destructure hierarchical structures");
        System.out.println("  - Access nested record components");
        System.out.println("  - Powerful for complex data");
        System.out.println("  - Eliminates boilerplate extraction code");

        System.out.println();
    }

    // ==================== RECORD DEFINITIONS ====================

    record BookingEvent(String email, double amount, String exhibit) {}
    record CancellationEvent(String email, double refund) {}
    record PaymentEvent(String email, double amount, String method) {}

    record TicketInfo(String id, double price, String exhibit) {}
    record Visitor(String email, String name) {}
    record Order(String id, TicketInfo ticket, Visitor visitor) {}
}