package museum.demo;

/**
 * PatternMatchingDemo.java
 * Demonstration of Pattern Matching for switch (Java 21)
 *
 * Demonstrates:
 * - Type patterns in switch
 * - Guard clauses (when conditions)
 * - Switch expressions vs statements
 * - Exhaustive pattern matching
 * - Null patterns
 *
 * @author Sriram S
 * @version 1.0
 */
public class PatternMatchingDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PATTERN MATCHING FOR SWITCH (JAVA 21)");
        System.out.println("=".repeat(70) + "\n");

        demonstrateTypePatterns();
        demonstrateGuardClauses();
        demonstrateSwitchExpressions();
        demonstrateNullPatterns();

        System.out.println("\n✓ Pattern matching demonstration complete\n");
    }

    /**
     * Demonstrate type patterns in switch
     *
     * Demonstrates:
     * - Pattern matching for different types
     * - Replacing instanceof + casting
     * - Cleaner code
     */
    private static void demonstrateTypePatterns() {
        System.out.println("1. TYPE PATTERNS");
        System.out.println("-".repeat(70));

        Object[] objects = {
                "Ancient India",
                150,
                99.99,
                true,
                new int[]{1, 2, 3}
        };

        System.out.println("\nUsing pattern matching (Java 21 - MODERN):\n");

        for (Object obj : objects) {
            // Pattern matching eliminates need for instanceof + casting
            String result = switch (obj) {
                case String s -> "String: " + s;
                case Integer i -> "Integer: " + i;
                case Double d -> "Double: " + d;
                case Boolean b -> "Boolean: " + b;
                case int[] arr -> "IntArray with " + arr.length + " elements";
                default -> "Unknown type";
            };

            System.out.println("  " + result);
        }

        System.out.println("\nOLD WAY (before Java 21):");
        for (Object obj : objects) {
            String result;
            if (obj instanceof String) {
                String s = (String) obj;
                result = "String: " + s;
            } else if (obj instanceof Integer) {
                Integer i = (Integer) obj;
                result = "Integer: " + i;
            } else if (obj instanceof Double) {
                Double d = (Double) obj;
                result = "Double: " + d;
            } else {
                result = "Unknown type";
            }
            System.out.println("  " + result);
        }

        System.out.println("\nBenefits of pattern matching:");
        System.out.println("  - No explicit casting needed");
        System.out.println("  - Variables scoped to case block");
        System.out.println("  - Cleaner, more readable code");
        System.out.println("  - Compiler prevents errors");

        System.out.println();
    }

    /**
     * Demonstrate guard clauses (when conditions)
     *
     * Demonstrates:
     * - Adding conditions to patterns
     * - Fine-grained control
     * - Complex pattern matching logic
     */
    private static void demonstrateGuardClauses() {
        System.out.println("2. GUARD CLAUSES (when)");
        System.out.println("-".repeat(70));

        Object[] exhibits = {
                "Ancient India",
                "Modern Art",
                "small",
                "Natural History Museum",
                123
        };

        System.out.println("\nPattern matching with guard clauses:\n");

        for (Object obj : exhibits) {
            String category = switch (obj) {
                // String with guard - check length
                case String s when s.length() > 10 ->
                        "Long name: " + s;

                // String with guard - check content
                case String s when s.contains("India") ->
                        "Contains 'India': " + s;

                // String with guard - short names
                case String s when s.length() <= 5 ->
                        "Short name: " + s;

                // Any other string
                case String s ->
                        "Medium name: " + s;

                // Integer with guard
                case Integer i when i > 100 ->
                        "Large number: " + i;

                // Default
                default -> "Other: " + obj;
            };

            System.out.println("  " + category);
        }

        System.out.println("\nGuard clauses allow:");
        System.out.println("  - Additional conditions (when)");
        System.out.println("  - Access to pattern variable");
        System.out.println("  - Elegant control flow");
        System.out.println("  - Multiple patterns for same type");

        System.out.println();
    }

    /**
     * Demonstrate switch expressions vs statements
     *
     * Demonstrates:
     * - Switch as expression (returns value)
     * - More functional style
     * - Eliminates need for break statements
     */
    private static void demonstrateSwitchExpressions() {
        System.out.println("3. SWITCH EXPRESSIONS");
        System.out.println("-".repeat(70));

        System.out.println("\nSwitch as expression (returns value):");

        String[] days = {"MONDAY", "WEDNESDAY", "FRIDAY", "SUNDAY"};

        for (String day : days) {
            // Switch expression - returns value
            String description = switch (day) {
                case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" ->
                        "Weekday";
                case "SATURDAY", "SUNDAY" ->
                        "Weekend";
                default ->
                        "Unknown";
            };

            System.out.println("  " + day + " is a " + description);
        }

        System.out.println("\nArithmetic with switch expression:");

        for (int i = 0; i < 5; i++) {
            // Switch expression in arithmetic
            int result = switch (i) {
                case 0 -> 100;
                case 1 -> 200;
                case 2 -> 300;
                default -> 0;
            };

            System.out.println("  Position " + i + " → Value: " + result);
        }

        System.out.println("\nSwitch expression benefits:");
        System.out.println("  - Returns value (functional style)");
        System.out.println("  - No break statements needed");
        System.out.println("  - Compiler ensures exhaustiveness");
        System.out.println("  - Can be used in assignments");

        System.out.println();
    }

    /**
     * Demonstrate null pattern matching
     *
     * Demonstrates:
     * - Handling null values elegantly
     * - null case in switch
     * - Safe null handling
     */
    private static void demonstrateNullPatterns() {
        System.out.println("4. NULL PATTERN MATCHING");
        System.out.println("-".repeat(70));

        Object[] data = {
                "Valid string",
                123,
                null,
                "Another string",
                null
        };

        System.out.println("\nHandling null safely:\n");

        for (Object obj : data) {
            // Null pattern - safe handling
            String result = switch (obj) {
                case null ->
                        "Value is null (handled safely!)";
                case String s ->
                        "String: " + s;
                case Integer i ->
                        "Integer: " + i;
                default ->
                        "Other: " + obj;
            };

            System.out.println("  " + result);
        }

        System.out.println("\nNull pattern benefits:");
        System.out.println("  - Eliminates NullPointerException risk");
        System.out.println("  - Explicit null handling");
        System.out.println("  - Cleaner than null checks");
        System.out.println("  - Part of pattern matching");

        System.out.println();
    }
}