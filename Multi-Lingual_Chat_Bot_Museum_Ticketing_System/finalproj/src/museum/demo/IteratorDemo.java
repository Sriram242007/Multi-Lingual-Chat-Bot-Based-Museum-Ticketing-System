package museum.demo;

import java.util.*;

/**
 * IteratorDemo.java
 * Demonstration of Iterator patterns and traversal methods
 *
 * Demonstrates:
 * - Iterator interface
 * - For-each loop vs Iterator
 * - ListIterator for bidirectional traversal
 * - Concurrent modification exceptions
 * - Stream API (modern alternative)
 *
 * @author Sriram S
 * @version 1.0
 */
public class IteratorDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ITERATOR PATTERNS DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateIterator();
        demonstrateForEachLoop();
        demonstrateListIterator();
        demonstrateConcurrentModification();
        demonstrateStreamAPI();

        System.out.println("\n✓ Iterator demonstration complete\n");
    }

    /**
     * Demonstrate Iterator interface
     *
     * Demonstrates:
     * - hasNext()
     * - next()
     * - remove()
     * - Safe removal during iteration
     */
    private static void demonstrateIterator() {
        System.out.println("1. ITERATOR INTERFACE");
        System.out.println("-".repeat(70));

        List<String> exhibits = new ArrayList<>();
        exhibits.add("Ancient India");
        exhibits.add("Modern Art");
        exhibits.add("Natural History");
        exhibits.add("Sculpture Gallery");

        System.out.println("\nUsing Iterator:");
        Iterator<String> iterator = exhibits.iterator();

        int count = 0;
        while (iterator.hasNext()) {
            count++;
            String exhibit = iterator.next();
            System.out.println("  " + count + ". " + exhibit);
        }

        System.out.println("\nIterating backward:");
        // Reset for backward iteration (using ListIterator)
        ListIterator<String> listIterator = exhibits.listIterator(exhibits.size());
        while (listIterator.hasPrevious()) {
            System.out.println("  - " + listIterator.previous());
        }

        // Safe removal during iteration
        System.out.println("\nSafe removal during iteration:");
        iterator = exhibits.iterator();
        System.out.println("  Original: " + exhibits);

        while (iterator.hasNext()) {
            String exhibit = iterator.next();
            if (exhibit.contains("Modern")) {
                iterator.remove(); // Safe way to remove
            }
        }
        System.out.println("  After removing 'Modern Art': " + exhibits);

        System.out.println();
    }

    /**
     * Demonstrate for-each loop (enhanced for)
     *
     * Demonstrates:
     * - For-each syntax
     * - Readability improvement
     * - Works with any Iterable
     * - No index access
     */
    private static void demonstrateForEachLoop() {
        System.out.println("2. FOR-EACH LOOP (Enhanced For)");
        System.out.println("-".repeat(70));

        List<String> exhibits = Arrays.asList(
                "Ancient India", "Modern Art", "Natural History"
        );

        System.out.println("\nFor-each loop:");
        for (String exhibit : exhibits) {
            System.out.println("  - " + exhibit);
        }

        System.out.println("\nFor-each with Set:");
        Set<Integer> prices = new HashSet<>(Arrays.asList(100, 150, 200, 100));
        for (Integer price : prices) {
            System.out.println("  Price: ₹" + price);
        }

        System.out.println("\nFor-each with Map:");
        Map<String, Double> feeMap = new HashMap<>();
        feeMap.put("Ancient India", 150.0);
        feeMap.put("Modern Art", 200.0);
        feeMap.put("Natural History", 100.0);

        for (Map.Entry<String, Double> entry : feeMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " → ₹" + entry.getValue());
        }

        System.out.println();
    }

    /**
     * Demonstrate ListIterator
     *
     * Demonstrates:
     * - Bidirectional traversal
     * - previousIndex() and nextIndex()
     * - Add and set during iteration
     */
    private static void demonstrateListIterator() {
        System.out.println("3. LISTITERATOR - BIDIRECTIONAL TRAVERSAL");
        System.out.println("-".repeat(70));

        List<String> days = new ArrayList<>(Arrays.asList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
        ));

        System.out.println("\nOriginal list: " + days);

        // Forward iteration
        System.out.println("\nForward iteration:");
        ListIterator<String> iterator = days.listIterator();
        while (iterator.hasNext()) {
            System.out.println("  Index " + iterator.nextIndex() + ": " + iterator.next());
        }

        // Backward iteration
        System.out.println("\nBackward iteration:");
        while (iterator.hasPrevious()) {
            System.out.println("  Index " + iterator.previousIndex() + ": " + iterator.previous());
        }

        // Modification during iteration
        System.out.println("\nModifying during iteration:");
        iterator = days.listIterator();
        while (iterator.hasNext()) {
            String day = iterator.next();
            if (day.equals("Wednesday")) {
                iterator.set("WED"); // Replace current element
            }
        }
        System.out.println("  After replacing Wednesday: " + days);

        // Add during iteration
        System.out.println("\nAdding during iteration:");
        iterator = days.listIterator();
        while (iterator.hasNext()) {
            if (iterator.nextIndex() == 2) {
                iterator.add("MIDWEEK"); // Add before Wednesday
                break;
            }
            iterator.next();
        }
        System.out.println("  After adding MIDWEEK: " + days);

        System.out.println();
    }

    /**
     * Demonstrate concurrent modification exception
     *
     * Demonstrates:
     * - What NOT to do (using list.remove during iteration)
     * - How to fix it (use iterator.remove())
     */
    private static void demonstrateConcurrentModification() {
        System.out.println("4. CONCURRENT MODIFICATION");
        System.out.println("-".repeat(70));

        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));

        System.out.println("\nUNSAFE - Using list.remove() during iteration:");
        try {
            for (String element : list) {
                System.out.print(element + " ");
                if (element.equals("C")) {
                    // list.remove(element); // This would throw ConcurrentModificationException!
                    System.out.println("← Would cause exception if uncommented");
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException!");
        }

        System.out.println("\nSAFE - Using iterator.remove():");
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            String element = it.next();
            System.out.print(element + " ");
            if (element.equals("C")) {
                it.remove(); // Safe!
                System.out.println("← Safely removed");
                break;
            }
        }
        System.out.println("\nAfter safe removal: " + list);

        System.out.println();
    }

    /**
     * Demonstrate Stream API (modern alternative to Iterator)
     *
     * Demonstrates:
     * - Functional approach to iteration
     * - filter, map, forEach
     * - Cleaner, more readable code
     */
    private static void demonstrateStreamAPI() {
        System.out.println("5. STREAM API (MODERN ALTERNATIVE)");
        System.out.println("-".repeat(70));

        List<String> exhibits = Arrays.asList(
                "Ancient India", "Modern Art", "Natural History", "Sculpture"
        );

        System.out.println("\nTraditional iteration:");
        for (String exhibit : exhibits) {
            if (exhibit.length() > 6) {
                System.out.println("  " + exhibit);
            }
        }

        System.out.println("\nStream API (functional):");
        exhibits.stream()
                .filter(exhibit -> exhibit.length() > 6)
                .forEach(exhibit -> System.out.println("  " + exhibit));

        System.out.println("\nMapping with Stream:");
        exhibits.stream()
                .map(String::toUpperCase)
                .forEach(exhibit -> System.out.println("  " + exhibit));

        System.out.println("\nFiltering and mapping:");
        exhibits.stream()
                .filter(exhibit -> exhibit.contains("Art"))
                .map(String::length)
                .forEach(length -> System.out.println("  Length: " + length));

        System.out.println();
    }
}