package museum;

import museum.demo.*;

/**
 * TestPhase8.java
 * Run all demonstration classes for Phase 8
 */
public class TestPhase8 {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PHASE 8: ADVANCED DEMONSTRATIONS - COMPLETE");
        System.out.println("=".repeat(70) + "\n");

        System.out.println("Available demonstrations:\n");
        System.out.println("1. Collections Framework Demo");
        System.out.println("2. Generics Demo");
        System.out.println("3. Iterator Patterns Demo");
        System.out.println("4. Map Implementations Demo");
        System.out.println("5. Set Implementations Demo");
        System.out.println("6. Run all demonstrations\n");

        System.out.print("Enter choice (1-6): ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                CollectionDemo.main(args);
                break;
            case "2":
                GenericDemo.main(args);
                break;
            case "3":
                IteratorDemo.main(args);
                break;
            case "4":
                MapDemo.main(args);
                break;
            case "5":
                SetDemo.main(args);
                break;
            case "6":
                CollectionDemo.main(args);
                GenericDemo.main(args);
                IteratorDemo.main(args);
                MapDemo.main(args);
                SetDemo.main(args);
                break;
            default:
                System.out.println("Invalid choice");
        }

        scanner.close();
    }
}