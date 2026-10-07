package museum.demo;

import java.util.*;

/**
 * CollectionDemo.java
 * Comprehensive demonstration of Java Collections Framework
 *
 * Demonstrates:
 * - ArrayList vs LinkedList vs Vector
 * - HashSet vs TreeSet vs LinkedHashSet
 * - When to use each collection type
 * - Performance characteristics
 * - Iteration methods
 *
 * @author Your Name
 * @version 1.0
 */
public class CollectionDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COLLECTIONS FRAMEWORK DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateList();
        demonstrateSet();
        demonstrateQueue();
        performanceComparison();

        System.out.println("\n✓ Collections demonstration complete\n");
    }

    /**
     * Demonstrate different List implementations
     *
     * Demonstrates:
     * - ArrayList: Fast random access, slow insertion/deletion
     * - LinkedList: Slow random access, fast insertion/deletion
     * - List operations: add, remove, get, contains
     */
    private static void demonstrateList() {
        System.out.println("1. LIST IMPLEMENTATIONS");
        System.out.println("-".repeat(70));

        // ==================== ArrayList ====================
        System.out.println("\nArrayList (Ordered, Mutable, Allows duplicates):");
        List<String> arrayList = new ArrayList<>();

        // Add elements
        arrayList.add("Ancient India");
        arrayList.add("Modern Art");
        arrayList.add("Natural History");
        arrayList.add("Modern Art"); // Duplicate allowed

        System.out.println("  After adding 4 elements: " + arrayList);
        System.out.println("  Size: " + arrayList.size());
        System.out.println("  Contains 'Modern Art': " + arrayList.contains("Modern Art"));
        System.out.println("  Index of 'Natural History': " + arrayList.indexOf("Natural History"));

        // Insert at specific position
        arrayList.add(1, "Contemporary");
        System.out.println("  After insert at index 1: " + arrayList);

        // Remove
        arrayList.remove("Contemporary");
        System.out.println("  After removing 'Contemporary': " + arrayList);

        // Get element by index
        System.out.println("  Element at index 0: " + arrayList.get(0));

        // ==================== LinkedList ====================
        System.out.println("\nLinkedList (Ordered, Mutable, Allows duplicates):");
        List<String> linkedList = new LinkedList<>();

        linkedList.addAll(arrayList); // Add all from ArrayList
        linkedList.add("Sculpture Gallery");

        System.out.println("  After adding elements: " + linkedList);
        System.out.println("  First: " + ((LinkedList<String>) linkedList).getFirst());
        System.out.println("  Last: " + ((LinkedList<String>) linkedList).getLast());

        // Remove first and last
        ((LinkedList<String>) linkedList).removeFirst();
        System.out.println("  After removeFirst(): " + linkedList);

        // ==================== Vector (Legacy) ====================
        System.out.println("\nVector (Legacy - Synchronized, Ordered, Mutable):");
        Vector<String> vector = new Vector<>(arrayList);
        System.out.println("  Created from ArrayList: " + vector);
        System.out.println("  Capacity: " + vector.capacity());
        System.out.println("  Size: " + vector.size());

        System.out.println();
    }

    /**
     * Demonstrate different Set implementations
     *
     * Demonstrates:
     * - HashSet: Unordered, fast lookup O(1)
     * - TreeSet: Sorted, slower O(log n)
     * - LinkedHashSet: Insertion order preserved
     * - Set operations: union, intersection, difference
     */
    private static void demonstrateSet() {
        System.out.println("2. SET IMPLEMENTATIONS");
        System.out.println("-".repeat(70));

        // ==================== HashSet ====================
        System.out.println("\nHashSet (Unordered, Unique elements, Fast lookup):");
        Set<String> hashSet = new HashSet<>();

        hashSet.add("Ancient India");
        hashSet.add("Modern Art");
        hashSet.add("Natural History");
        hashSet.add("Modern Art"); // Duplicate ignored
        hashSet.add("Sculpture");

        System.out.println("  After adding 5 elements (1 duplicate): " + hashSet);
        System.out.println("  Size: " + hashSet.size());
        System.out.println("  Contains 'Ancient India': " + hashSet.contains("Ancient India"));
        System.out.println("  Order is NOT guaranteed: insertion order not preserved");

        // ==================== TreeSet ====================
        System.out.println("\nTreeSet (Sorted, Unique elements, O(log n) operations):");
        Set<String> treeSet = new TreeSet<>(hashSet);

        System.out.println("  Created from HashSet: " + treeSet);
        System.out.println("  First (smallest): " + ((TreeSet<String>) treeSet).first());
        System.out.println("  Last (largest): " + ((TreeSet<String>) treeSet).last());
        System.out.println("  Higher than 'Modern Art': " +
                ((TreeSet<String>) treeSet).higher("Modern Art"));
        System.out.println("  Lower than 'Modern Art': " +
                ((TreeSet<String>) treeSet).lower("Modern Art"));

        // ==================== LinkedHashSet ====================
        System.out.println("\nLinkedHashSet (Insertion order, Unique elements):");
        Set<String> linkedHashSet = new LinkedHashSet<>();

        linkedHashSet.add("Ancient India");
        linkedHashSet.add("Modern Art");
        linkedHashSet.add("Natural History");
        linkedHashSet.add("Sculpture");

        System.out.println("  After adding 4 elements: " + linkedHashSet);
        System.out.println("  Insertion order preserved");

        // ==================== Set Operations ====================
        System.out.println("\nSet Operations:");

        Set<String> set1 = new HashSet<>(Arrays.asList("A", "B", "C", "D"));
        Set<String> set2 = new HashSet<>(Arrays.asList("C", "D", "E", "F"));

        // Union
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("  Set1: " + set1);
        System.out.println("  Set2: " + set2);
        System.out.println("  Union: " + union);

        // Intersection
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("  Intersection: " + intersection);

        // Difference
        Set<String> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("  Difference (Set1 - Set2): " + difference);

        System.out.println();
    }

    /**
     * Demonstrate Queue and Deque
     *
     * Demonstrates:
     * - LinkedList as Queue (FIFO)
     * - PriorityQueue (ordering)
     * - Deque operations
     */
    private static void demonstrateQueue() {
        System.out.println("3. QUEUE & DEQUE IMPLEMENTATIONS");
        System.out.println("-".repeat(70));

        // ==================== Queue (FIFO) ====================
        System.out.println("\nQueue (FIFO - First In First Out):");
        Queue<String> queue = new LinkedList<>();

        // Enqueue
        queue.add("First");
        queue.add("Second");
        queue.add("Third");
        System.out.println("  After adding 3 elements: " + queue);

        // Peek at front
        System.out.println("  Peek (front): " + queue.peek());
        System.out.println("  Queue unchanged: " + queue);

        // Dequeue
        String first = queue.poll();
        System.out.println("  After poll(): " + queue);
        System.out.println("  Polled element: " + first);

        // ==================== PriorityQueue ====================
        System.out.println("\nPriorityQueue (Min-heap based ordering):");
        Queue<Integer> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(5);
        priorityQueue.add(1);
        priorityQueue.add(9);
        priorityQueue.add(3);
        priorityQueue.add(7);

        System.out.println("  After adding [5, 1, 9, 3, 7]: " + priorityQueue);
        System.out.println("  Elements come out in order:");
        while (!priorityQueue.isEmpty()) {
            System.out.print("    " + priorityQueue.poll());
            if (!priorityQueue.isEmpty()) System.out.print(" → ");
        }
        System.out.println();

        // ==================== Deque (Double Ended Queue) ====================
        System.out.println("\nDeque (Double Ended Queue):");
        Deque<String> deque = new LinkedList<>();

        deque.addFirst("First");
        deque.addLast("Last");
        deque.addFirst("New First");
        deque.addLast("New Last");

        System.out.println("  After adding to both ends: " + deque);
        System.out.println("  removeFirst(): " + deque.removeFirst());
        System.out.println("  removeLast(): " + deque.removeLast());
        System.out.println("  Remaining: " + deque);

        System.out.println();
    }

    /**
     * Performance comparison of collections
     *
     * Demonstrates:
     * - Time complexity of different operations
     * - When to use which collection
     */
    private static void performanceComparison() {
        System.out.println("4. PERFORMANCE CHARACTERISTICS");
        System.out.println("-".repeat(70));

        System.out.println("\nOperation Performance (N elements):\n");

        System.out.println("ArrayList:");
        System.out.println("  - add(int index, E): O(n) - needs shifting");
        System.out.println("  - get(int index): O(1) - direct access");
        System.out.println("  - remove(int index): O(n) - needs shifting");
        System.out.println("  - contains(Object): O(n)");
        System.out.println("  Best for: Random access, indexed operations");

        System.out.println("\nLinkedList:");
        System.out.println("  - add(int index, E): O(n) - needs traversal");
        System.out.println("  - get(int index): O(n) - needs traversal");
        System.out.println("  - remove(int index): O(n) - needs traversal");
        System.out.println("  - addFirst/addLast: O(1)");
        System.out.println("  - removeFirst/removeLast: O(1)");
        System.out.println("  Best for: Frequent insertions/deletions at ends");

        System.out.println("\nHashSet:");
        System.out.println("  - add(E): O(1) average, O(n) worst");
        System.out.println("  - contains(Object): O(1) average, O(n) worst");
        System.out.println("  - remove(Object): O(1) average, O(n) worst");
        System.out.println("  Best for: Fast lookup, unique elements");

        System.out.println("\nTreeSet:");
        System.out.println("  - add(E): O(log n)");
        System.out.println("  - contains(Object): O(log n)");
        System.out.println("  - remove(Object): O(log n)");
        System.out.println("  Best for: Sorted data, range queries");

        System.out.println("\nHashMap:");
        System.out.println("  - put(K, V): O(1) average");
        System.out.println("  - get(Object): O(1) average");
        System.out.println("  - remove(Object): O(1) average");
        System.out.println("  Best for: Key-value pairs, fast lookups");

        System.out.println();
    }
}