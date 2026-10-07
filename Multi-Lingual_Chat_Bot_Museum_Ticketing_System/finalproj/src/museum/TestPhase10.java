package museum;

import museum.demo.*;

/**
 * TestPhase10.java
 * Run all pattern matching and record demonstration classes
 */
public class TestPhase10 {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PHASE 10: PATTERN MATCHING & RECORDS (JAVA 21) - COMPLETE");
        System.out.println("=".repeat(70) + "\n");

        System.out.println("Java 21 Features (running on Java 24):\n");
        System.out.println("1. Pattern Matching for switch");
        System.out.println("2. Record Basics");
        System.out.println("3. Record Patterns");
        System.out.println("4. Sealed Classes");
        System.out.println("5. Run all demonstrations\n");

        System.out.print("Enter choice (1-5): ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                PatternMatchingDemo.main(args);
                break;
            case "2":
                RecordBasicsDemo.main(args);
                break;
            case "3":
                RecordPatternDemo.main(args);
                break;
            case "4":
                break;
            case "5":
                PatternMatchingDemo.main(args);
                RecordBasicsDemo.main(args);
                RecordPatternDemo.main(args);
                break;
            default:
                System.out.println("Invalid choice");
        }

        scanner.close();
    }
}