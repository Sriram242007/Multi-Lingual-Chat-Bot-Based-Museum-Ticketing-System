package museum.demo;
import museum.model.*;
import java.util.*;

/**
 * SetDemo.java
 * Comprehensive demonstration of Set implementations
 *
 * Demonstrates:
 * - HashSet (unordered, fast)
 * - TreeSet (sorted)
 * - LinkedHashSet (insertion order)
 * - EnumSet (for enum values)
 * - Set operations (union, intersection, difference)
 *
 * @author Sriram S
 * @version 1.0
 */
public class SetDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SET IMPLEMENTATIONS DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateHashSet();
        demonstrateTreeSet();
        demonstrateLinkedHashSet();
        demonstrateEnumSet();
        demonstrateSetOperations();

        System.out.println("\n✓ Set demonstration complete\n");
    }

    /**
     * Demonstrate HashSet
     *
     * Demonstrates:
     * - O(1) average operations
     * - Unordered
     * - No duplicates
     */
    private static void demonstrateHashSet() {
        System.out.println("1. HASHSET (Unordered, Fast, No Duplicates)");
        System.out.println("-".repeat(70));

        Set<String> exhibits = new HashSet<>();

        // Add elements
        exhibits.add("Ancient India");
        exhibits.add("Modern Art");
        exhibits.add("Natural History");
        exhibits.add("Ancient India"); // Duplicate - ignored
        exhibits.add("Sculpture");

        System.out.println("\nHashSet after adding 5 elements (1 duplicate):");
        System.out.println("  " + exhibits);
        System.out.println("  Size: " + exhibits.size());
        System.out.println("  Note: Unordered and no duplicates");

        // Check membership
        System.out.println("\nMembership checks:");
        System.out.println("  Contains 'Ancient India': " + exhibits.contains("Ancient India"));
        System.out.println("  Contains 'Modern Sculpture': " + exhibits.contains("Modern Sculpture"));

        // Remove
        exhibits.remove("Sculpture");
        System.out.println("\nAfter removing 'Sculpture':");
        System.out.println("  " + exhibits);

        System.out.println();
    }

    /**
     * Demonstrate TreeSet
     *
     * Demonstrates:
     * - Sorted order
     * - Natural ordering (Comparable)
     * - O(log n) operations
     */
    private static void demonstrateTreeSet() {
        System.out.println("2. TREESET (Sorted, No Duplicates)");
        System.out.println("-".repeat(70));

        Set<String> exhibits = new TreeSet<>();

        exhibits.add("Modern Art");
        exhibits.add("Ancient India");
        exhibits.add("Natural History");
        exhibits.add("Sculpture Gallery");
        exhibits.add("Dinosaur Fossils");

        System.out.println("\nTreeSet contents (sorted alphabetically):");
        System.out.println("  " + exhibits);

        TreeSet<String> treeExhibits = (TreeSet<String>) exhibits;

        System.out.println("\nTreeSet operations:");
        System.out.println("  First: " + treeExhibits.first());
        System.out.println("  Last: " + treeExhibits.last());
        System.out.println("  Lower than 'Modern Art': " + treeExhibits.lower("Modern Art"));
        System.out.println("  Higher than 'Modern Art': " + treeExhibits.higher("Modern Art"));
        System.out.println("  Floor of 'Modern': " + treeExhibits.floor("Modern"));
        System.out.println("  Ceiling of 'Nature': " + treeExhibits.ceiling("Nature"));

        System.out.println("\nSubset (from 'A' to 'N', exclusive):");
        Set<String> subSet = treeExhibits.subSet("A", "N");
        System.out.println("  " + subSet);

        System.out.println("\nHeadSet (before 'Natural History'):");
        Set<String> headSet = treeExhibits.headSet("Natural History");
        System.out.println("  " + headSet);

        System.out.println("\nTailSet (from 'Sculpture' onwards):");
        Set<String> tailSet = treeExhibits.tailSet("Sculpture");
        System.out.println("  " + tailSet);

        System.out.println("\nDescending order:");
        for (String exhibit : treeExhibits.descendingSet()) {
            System.out.print("  " + exhibit);
        }
        System.out.println("\n");
    }

    /**
     * Demonstrate LinkedHashSet
     *
     * Demonstrates:
     * - Insertion order preserved
     * - No duplicates
     * - Faster than TreeSet
     */
    private static void demonstrateLinkedHashSet() {
        System.out.println("3. LINKEDHASHSET (Insertion Order, No Duplicates)");
        System.out.println("-".repeat(70));

        Set<String> exhibits = new LinkedHashSet<>();

        exhibits.add("Ancient India");
        exhibits.add("Modern Art");
        exhibits.add("Natural History");
        exhibits.add("Sculpture Gallery");
        exhibits.add("Modern Art"); // Duplicate - ignored

        System.out.println("\nLinkedHashSet (insertion order preserved):");
        System.out.println("  " + exhibits);
        System.out.println("  Size: " + exhibits.size());

        System.out.println("\nIteration preserves insertion order:");
        int index = 1;
        for (String exhibit : exhibits) {
            System.out.println("  " + index + ". " + exhibit);
            index++;
        }

        System.out.println();
    }

    /**
     * Demonstrate EnumSet
     *
     * Demonstrates:
     * - Optimized for enum types
     * - Very fast and memory-efficient
     * - Type-safe enum operations
     */
    private static void demonstrateEnumSet() {
        System.out.println("4. ENUMSET (Optimized for Enums)");
        System.out.println("-".repeat(70));

        // Create EnumSet for Days of week
        EnumSet<Day> weekdays = EnumSet.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY,
                Day.THURSDAY, Day.FRIDAY);

        System.out.println("\nWeekdays: " + weekdays);

        // All days
        Set<Day> allDays = EnumSet.allOf(Day.class);
        System.out.println("\nAll days: " + allDays);

        // Complement (all except)
        Set<Day> weekendDays = EnumSet.complementOf(weekdays);
        System.out.println("\nWeekend days: " + weekendDays);

        // Range
        Set<Day> midWeek = EnumSet.range(Day.TUESDAY, Day.THURSDAY);
        System.out.println("\nMid-week (Tue-Thu): " + midWeek);

        // Check membership
        System.out.println("\nMembership checks:");
        System.out.println("  Saturday is weekday: " + weekdays.contains(Day.SATURDAY));
        System.out.println("  Monday is weekday: " + weekdays.contains(Day.MONDAY));

        System.out.println();
    }

    /**
     * Demonstrate Set operations
     *
     * Demonstrates:
     * - Union (addAll)
     * - Intersection (retainAll)
     * - Difference (removeAll)
     */
    private static void demonstrateSetOperations() {
        System.out.println("5. SET OPERATIONS");
        System.out.println("-".repeat(70));

        Set<String> set1 = new HashSet<>(Arrays.asList("A", "B", "C", "D", "E"));
        Set<String> set2 = new HashSet<>(Arrays.asList("D", "E", "F", "G"));

        System.out.println("\nOriginal sets:");
        System.out.println("  Set1: " + set1);
        System.out.println("  Set2: " + set2);

        // Union
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("\nUnion (Set1 ∪ Set2):");
        System.out.println("  " + union);

        // Intersection
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("\nIntersection (Set1 ∩ Set2):");
        System.out.println("  " + intersection);

        // Difference
        Set<String> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("\nDifference (Set1 - Set2):");
        System.out.println("  " + difference);

        // Symmetric difference
        Set<String> symmetric = new HashSet<>(union);
        symmetric.removeAll(intersection);
        System.out.println("\nSymmetric Difference (Union - Intersection):");
        System.out.println("  " + symmetric);

        // Subset check
        System.out.println("\nSubset checks:");
        Set<String> subset = new HashSet<>(Arrays.asList("B", "C"));
        System.out.println("  {B, C} is subset of Set1: " + set1.containsAll(subset));
        System.out.println("  Set1 is subset of Set2: " + set2.containsAll(set1));

        System.out.println();
    }

    /**
     * Enum for days of week (used in EnumSet demo)
     */
    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}