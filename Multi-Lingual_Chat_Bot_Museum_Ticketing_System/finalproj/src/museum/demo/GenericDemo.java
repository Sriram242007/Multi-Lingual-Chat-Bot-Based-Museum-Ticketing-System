package museum.demo;

import java.util.*;

/**
 * GenericDemo.java
 * Comprehensive demonstration of Generics and Type Safety
 *
 * Demonstrates:
 * - Generic classes
 * - Generic methods
 * - Bounded type parameters
 * - Wildcards (? extends, ? super)
 * - Type erasure
 * - Benefits of generics (type safety, no casting)
 *
 * @author Sriram S
 * @version 1.0
 */
public class GenericDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("GENERICS DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateGenericClass();
        demonstrateGenericMethods();
        demonstrateBoundedTypes();
        demonstrateWildcards();
        demonstrateTypeErasure();

        System.out.println("\n✓ Generics demonstration complete\n");
    }

    /**
     * Demonstrate generic classes
     *
     * Demonstrates:
     * - Type-safe containers
     * - No casting needed
     * - Compile-time type checking
     */
    private static void demonstrateGenericClass() {
        System.out.println("1. GENERIC CLASSES");
        System.out.println("-".repeat(70));

        // Generic Box class
        Box<String> stringBox = new Box<>("Ancient India");
        Box<Integer> intBox = new Box<>(150);
        Box<Double> doubleBox = new Box<>(99.99);

        System.out.println("\nCreated generic boxes:");
        System.out.println("  String box: " + stringBox.get());
        System.out.println("  Integer box: " + intBox.get());
        System.out.println("  Double box: " + doubleBox.get());

        // Type safety - no casting needed
        String exhibit = stringBox.get(); // No cast!
        System.out.println("\nNo casting needed:");
        System.out.println("  String value: " + exhibit);

        System.out.println();
    }

    /**
     * Demonstrate generic methods
     *
     * Demonstrates:
     * - Type parameters in methods
     * - Generic static methods
     * - Convenience of generics
     */
    private static void demonstrateGenericMethods() {
        System.out.println("2. GENERIC METHODS");
        System.out.println("-".repeat(70));

        // Generic method for printing arrays
        System.out.println("\nPrint array of integers:");
        Integer[] intArray = {1, 2, 3, 4, 5};
        printArray(intArray);

        System.out.println("\nPrint array of strings:");
        String[] stringArray = {"Ancient", "Modern", "Natural"};
        printArray(stringArray);

        System.out.println("\nPrint array of doubles:");
        Double[] doubleArray = {150.0, 200.0, 100.0};
        printArray(doubleArray);

        // Generic method to find maximum
        System.out.println("\nFind maximum value:");
        System.out.println("  Max of [3, 1, 4, 1, 5, 9]: " + findMax(3, 1, 4, 1, 5, 9));

        System.out.println();
    }

    /**
     * Demonstrate bounded type parameters
     *
     * Demonstrates:
     * - <T extends Number>
     * - Restricting type parameters
     * - Type bounds
     */
    private static void demonstrateBoundedTypes() {
        System.out.println("3. BOUNDED TYPE PARAMETERS");
        System.out.println("-".repeat(70));

        // Bounded to Number class
        System.out.println("\nGeneric numbers (bounded to Number):");

        NumberBox<Integer> intBox = new NumberBox<>(150);
        NumberBox<Double> doubleBox = new NumberBox<>(99.99);

        System.out.println("  Integer box value: " + intBox.get());
        System.out.println("  Double box value: " + doubleBox.get());
        System.out.println("  Integer box as double: " + intBox.getAsDouble());
        System.out.println("  Double box as double: " + doubleBox.getAsDouble());

        // This would cause compile error:
        // NumberBox<String> stringBox = new NumberBox<>("Text"); // Error!

        System.out.println("\nBounded to Comparable:");
        String[] sortedStrings = {"Natural", "Ancient", "Modern"};
        Arrays.sort(sortedStrings);
        System.out.println("  Sorted strings: " + Arrays.toString(sortedStrings));

        System.out.println();
    }

    /**
     * Demonstrate wildcards
     *
     * Demonstrates:
     * - Upper bounded wildcard (? extends T)
     * - Lower bounded wildcard (? super T)
     * - Unbounded wildcard (?)
     */
    private static void demonstrateWildcards() {
        System.out.println("4. WILDCARDS");
        System.out.println("-".repeat(70));

        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubleList = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);

        // Upper bounded wildcard (? extends Number)
        System.out.println("\nUpper bounded wildcard (? extends Number):");
        System.out.println("  Sum of integers: " + sumNumbers(intList));
        System.out.println("  Sum of doubles: " + sumNumbers(doubleList));

        // Lower bounded wildcard (? super Integer)
        System.out.println("\nLower bounded wildcard (? super Integer):");
        List<Number> numbers = new ArrayList<>();
        addIntegers(numbers);
        System.out.println("  After adding integers to Number list: " + numbers);

        // Unbounded wildcard (?)
        System.out.println("\nUnbounded wildcard (?):");
        printList(intList);
        printList(doubleList);

        System.out.println();
    }

    /**
     * Demonstrate type erasure
     *
     * Demonstrates:
     * - Generic info removed at runtime
     * - Why you can't create generic arrays
     * - instanceof limitations with generics
     */
    private static void demonstrateTypeErasure() {
        System.out.println("5. TYPE ERASURE");
        System.out.println("-".repeat(70));

        Box<String> stringBox = new Box<>("Text");
        Box<Integer> intBox = new Box<>(42);

        System.out.println("\nType erasure - generics exist only at compile time:");
        System.out.println("  stringBox.getClass(): " + stringBox.getClass().getName());
        System.out.println("  intBox.getClass(): " + intBox.getClass().getName());
        System.out.println("  They're the same class at runtime!");

        System.out.println("\nCan't create generic arrays:");
        System.out.println("  Box<String>[] boxes = new Box<String>[5]; // Error!");
        System.out.println("  Must use: Box<String>[] boxes = new Box[5]; // Warning");

        System.out.println("\nCan't use instanceof with type parameters:");
        System.out.println("  if (object instanceof Box<String>) // Error!");
        System.out.println("  Must use: if (object instanceof Box) // OK");

        System.out.println();
    }

    // ==================== HELPER CLASSES ====================

    /**
     * Generic Box class
     */
    static class Box<T> {
        private T value;

        public Box(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }
    }

    /**
     * Bounded generic class - only accepts Number types
     */
    static class NumberBox<T extends Number> {
        private T value;

        public NumberBox(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public double getAsDouble() {
            return value.doubleValue();
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Generic method to print array
     */
    static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    /**
     * Generic method to find maximum (variadic)
     */
    static <T extends Comparable<T>> T findMax(T... elements) {
        T max = elements[0];
        for (T element : elements) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }

    /**
     * Sum with upper bounded wildcard (? extends Number)
     */
    static double sumNumbers(List<? extends Number> list) {
        double sum = 0;
        for (Number number : list) {
            sum += number.doubleValue();
        }
        return sum;
    }

    /**
     * Add integers with lower bounded wildcard (? super Integer)
     */
    static void addIntegers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
    }

    /**
     * Print any list with unbounded wildcard (?)
     */
    static void printList(List<?> list) {
        System.out.println("  List: " + list);
    }
}