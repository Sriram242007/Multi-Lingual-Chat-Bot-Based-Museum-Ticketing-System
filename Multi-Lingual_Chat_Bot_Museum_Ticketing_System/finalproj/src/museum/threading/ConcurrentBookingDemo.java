package museum.threading;

/**
 * ConcurrentBookingDemo.java
 * Real-world example: concurrent booking with race condition and solution
 *
 * Demonstrates:
 * - Race condition in booking system
 * - Overbooking without synchronization
 * - Solution using synchronized methods
 * - Real-world threading issue
 * @author Sriram S
 * @version 1.0
 */
public class ConcurrentBookingDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONCURRENT BOOKING - RACE CONDITION DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateRaceConditionInBooking();
        demonstrateSafeBooking();

        System.out.println("\n✓ Concurrent booking demonstration complete\n");
    }

    /**
     * Demonstrate race condition: overbooking without synchronization
     *
     * Demonstrates:
     * - Multiple threads attempting simultaneous bookings
     * - Overbooking due to race condition
     * - Why synchronization is critical
     */
    private static void demonstrateRaceConditionInBooking() throws InterruptedException {
        System.out.println("1. RACE CONDITION: OVERBOOKING (UNSAFE)");
        System.out.println("-".repeat(70));

        UnsafeTicketSlot slot = new UnsafeTicketSlot(5); // Capacity: 5

        System.out.println("\nUnsafe booking (without synchronization):");
        System.out.println("  Time slot capacity: 5 tickets");
        System.out.println("  Creating 10 threads, each booking 1 ticket");
        System.out.println("  Result: OVERBOOKING due to race condition\n");

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            final int visitorId = i + 1;
            threads[i] = new Thread(() -> {
                boolean booked = slot.bookTicket(visitorId);
                System.out.println("  Visitor " + visitorId +
                        (booked ? " successfully booked" : " failed to book"));
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\n  Final booked tickets: " + slot.getBookedTickets());
        System.out.println("  Capacity: 5");
        System.out.println("  PROBLEM: " + slot.getBookedTickets() +
                " tickets booked (should be max 5)!");
        System.out.println("  Cause: Race condition without synchronization");

        System.out.println();
    }

    /**
     * Demonstrate solution: safe booking with synchronization
     *
     * Demonstrates:
     * - Synchronized method prevents race condition
     * - Proper booking behavior
     */
    private static void demonstrateSafeBooking() throws InterruptedException {
        System.out.println("2. SAFE BOOKING (WITH SYNCHRONIZATION)");
        System.out.println("-".repeat(70));

        SafeTicketSlot slot = new SafeTicketSlot(5); // Capacity: 5

        System.out.println("\nSafe booking (with synchronization):");
        System.out.println("  Time slot capacity: 5 tickets");
        System.out.println("  Creating 10 threads, each booking 1 ticket");
        System.out.println("  Result: NO OVERBOOKING - synchronized access\n");

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            final int visitorId = i + 1;
            threads[i] = new Thread(() -> {
                boolean booked = slot.bookTicket(visitorId);
                System.out.println("  Visitor " + visitorId +
                        (booked ? " successfully booked" : " failed (slot full)"));
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\n  Final booked tickets: " + slot.getBookedTickets());
        System.out.println("  Capacity: 5");
        System.out.println("  CORRECT: " + slot.getBookedTickets() +
                " tickets booked (exactly 5)");
        System.out.println("  Solution: Synchronized method ensures mutual exclusion");

        System.out.println();
    }

    // ==================== UNSAFE BOOKING SLOT ====================

    /**
     * Unsafe time slot booking - RACE CONDITION!
     */
    static class UnsafeTicketSlot {
        private int capacity;
        private int booked = 0;

        public UnsafeTicketSlot(int capacity) {
            this.capacity = capacity;
        }

        // NOT synchronized - race condition!
        public boolean bookTicket(int visitorId) {
            if (booked < capacity) {
                // CRITICAL SECTION - NOT PROTECTED
                try {
                    Thread.sleep(10); // Simulate processing
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                booked++;
                return true;
            }
            return false;
        }

        public int getBookedTickets() {
            return booked;
        }
    }

    // ==================== SAFE BOOKING SLOT ====================

    /**
     * Safe time slot booking - SYNCHRONIZED
     */
    static class SafeTicketSlot {
        private int capacity;
        private int booked = 0;

        public SafeTicketSlot(int capacity) {
            this.capacity = capacity;
        }

        // SYNCHRONIZED - prevents race condition
        public synchronized boolean bookTicket(int visitorId) {
            if (booked < capacity) {
                // CRITICAL SECTION - PROTECTED by synchronized
                try {
                    Thread.sleep(10); // Simulate processing
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                booked++;
                return true;
            }
            return false;
        }

        public synchronized int getBookedTickets() {
            return booked;
        }
    }
}