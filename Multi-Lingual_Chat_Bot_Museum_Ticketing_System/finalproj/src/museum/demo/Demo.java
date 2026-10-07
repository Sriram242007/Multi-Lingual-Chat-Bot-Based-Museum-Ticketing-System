package museum;

/**
 * Demo.java
 * Simple demonstration launcher
 *
 * Run either TestPhase7 for automated tests or MuseumApplication for interactive use
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MUSEUM TICKETING SYSTEM - DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        System.out.println("Choose a mode:");
        System.out.println("1. Run automated tests (TestPhase7)");
        System.out.println("2. Run interactive application (MuseumApplication)");
        System.out.println("3. View system summary");
        System.out.println();

        System.out.print("Enter choice (1-3): ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.println("\nRunning automated tests...\n");
                TestPhase7.main(args);
                break;

            case "2":
                System.out.println("\nStarting interactive application...\n");
                MuseumApplication.main(args);
                break;

            case "3":
                displaySystemSummary();
                break;

            default:
                System.out.println("Invalid choice");
        }

        scanner.close();
    }

    private static void displaySystemSummary() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MUSEUM TICKETING SYSTEM - COMPLETE IMPLEMENTATION");
        System.out.println("=".repeat(70) + "\n");

        System.out.println("ARCHITECTURE OVERVIEW:");
        System.out.println("└─ Chatbot Layer (ConversationManager, CommandProcessor)");
        System.out.println("   └─ Service Layer (AuthenticationService, BookingService, etc.)");
        System.out.println("      └─ Repository Layer (UserRepository, BookingRepository)");
        System.out.println("         └─ Model Layer (User, Booking, Exhibit, etc.)");
        System.out.println();

        System.out.println("KEY FEATURES:");
        System.out.println("✓ Multilingual support (English, Tamil, Hindi)");
        System.out.println("✓ User registration and authentication");
        System.out.println("✓ Exhibit browsing and searching");
        System.out.println("✓ Real-time availability checking");
        System.out.println("✓ Thread-safe booking (synchronized methods)");
        System.out.println("✓ Simulated payment processing");
        System.out.println("✓ Receipt generation (StringBuilder)");
        System.out.println("✓ Analytics and reporting");
        System.out.println("✓ Admin interface");
        System.out.println();

        System.out.println("OOP CONCEPTS DEMONSTRATED:");
        System.out.println("✓ Encapsulation (private fields, public getters/setters)");
        System.out.println("✓ Inheritance (User → Visitor/Admin)");
        System.out.println("✓ Polymorphism (abstract methods, interfaces)");
        System.out.println("✓ Abstraction (abstract classes, interfaces)");
        System.out.println("✓ Static members (counters, constants)");
        System.out.println("✓ Collections (ArrayList, HashMap, HashSet, EnumSet)");
        System.out.println("✓ Generics (Repository<T>)");
        System.out.println("✓ Exception handling (custom exceptions)");
        System.out.println("✓ Synchronization (thread-safe booking)");
        System.out.println("✓ String processing (StringBuilder, normalization)");
        System.out.println();

        System.out.println("DATA PERSISTENCE:");
        System.out.println("✓ File-based storage (users.txt, bookings.txt, exhibits.txt)");
        System.out.println("✓ Receipt generation and storage");
        System.out.println("✓ Automatic save on modifications");
        System.out.println();

        System.out.println("PACKAGES STRUCTURE:");
        System.out.println("museum/");
        System.out.println("├── model/ ..................... Domain classes");
        System.out.println("├── service/ ................... Business logic");
        System.out.println("├── repository/ ................ Data access layer");
        System.out.println("├── chatbot/ ................... Conversation management");
        System.out.println("├── exception/ ................. Custom exceptions");
        System.out.println("├── localization/ .............. Multilingual support");
        System.out.println("├── interfaces/ ................ Service contracts");
        System.out.println("├── generic/ ................... Generic utilities");
        System.out.println("├── util/ ...................... Helper functions");
        System.out.println("├── config/ .................... Configuration");
        System.out.println("├── threading/ ................. Concurrency demos");
        System.out.println("├── demo/ ...................... Academic demonstrations");
        System.out.println("└── jdbc/ ...................... Database demos");
        System.out.println();

        System.out.println("JAVA VERSION: Java 24");
        System.out.println("STATUS: ✓ Complete and Tested");
        System.out.println();
    }
}