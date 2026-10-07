package museum;

import museum.threading.*;

/**
 * TestPhase9.java
 * Run all threading demonstration classes for Phase 9
 */
public class TestPhase9 {

    public static void main(String[] args) throws Exception {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PHASE 9: MULTITHREADING & CONCURRENCY - COMPLETE");
        System.out.println("=".repeat(70) + "\n");

        System.out.println("Available demonstrations:\n");
        System.out.println("1. Thread Basics (creation, lifecycle, states)");
        System.out.println("2. Synchronization (race conditions, synchronized, locks)");
        System.out.println("3. Thread Pools (ExecutorService, Future)");
        System.out.println("4. Concurrent Collections (ConcurrentHashMap, BlockingQueue)");
        System.out.println("5. Virtual Threads (Java 21 feature)");
        System.out.println("6. Concurrent Booking (real-world example)");
        System.out.println("7. Deadlock (scenarios and prevention)");
        System.out.println("8. Run all demonstrations\n");

        System.out.print("Enter choice (1-8): ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String choice = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1":
                    ThreadBasicsDemo.main(args);
                    break;
                case "2":
                    SynchronizationDemo.main(args);
                    break;
                case "3":
                    ThreadPoolDemo.main(args);
                    break;
                case "4":
                    ConcurrentCollectionsDemo.main(args);
                    break;
                case "5":
                    VirtualThreadsDemo.main(args);
                    break;
                case "6":
                    ConcurrentBookingDemo.main(args);
                    break;
                case "7":
                    DeadlockDemo.main(args);
                    break;
                case "8":
                    ThreadBasicsDemo.main(args);
                    SynchronizationDemo.main(args);
                    ThreadPoolDemo.main(args);
                    ConcurrentCollectionsDemo.main(args);
                    VirtualThreadsDemo.main(args);
                    ConcurrentBookingDemo.main(args);
                    DeadlockDemo.main(args);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }
}