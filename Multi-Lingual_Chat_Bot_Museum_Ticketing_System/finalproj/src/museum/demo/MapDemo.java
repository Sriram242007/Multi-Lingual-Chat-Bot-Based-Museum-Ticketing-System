package museum.demo;

import java.util.*;

/**
 * MapDemo.java
 * Comprehensive demonstration of Map implementations
 *
 * Demonstrates:
 * - HashMap (unordered, fast)
 * - TreeMap (sorted by key)
 * - LinkedHashMap (insertion order)
 * - Map operations
 * - Key-value iteration
 *
 * @author Sriram S
 * @version 1.0
 */
public class MapDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MAP IMPLEMENTATIONS DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateHashMap();
        demonstrateTreeMap();
        demonstrateLinkedHashMap();
        demonstrateMapOperations();
        demonstrateMapIteration();

        System.out.println("\n✓ Map demonstration complete\n");
    }

    /**
     * Demonstrate HashMap
     *
     * Demonstrates:
     * - O(1) average time complexity
     * - Unordered
     * - Allows null key and values
     */
    private static void demonstrateHashMap() {
        System.out.println("1. HASHMAP (Unordered, Fast)");
        System.out.println("-".repeat(70));

        Map<String, Double> prices = new HashMap<>();

        // Put values
        prices.put("Ancient India", 150.0);
        prices.put("Modern Art", 200.0);
        prices.put("Natural History", 100.0);
        prices.put("Sculpture Gallery", 175.0);

        System.out.println("\nHashMap contents (unordered):");
        System.out.println("  " + prices);
        System.out.println("  Size: " + prices.size());

        // Get value
        System.out.println("\nGet values:");
        System.out.println("  Price of 'Modern Art': ₹" + prices.get("Modern Art"));
        System.out.println("  Price of 'Unknown': " + prices.get("Unknown"));

        // Check key/value existence
        System.out.println("\nCheck existence:");
        System.out.println("  Contains 'Ancient India': " + prices.containsKey("Ancient India"));
        System.out.println("  Contains value 150.0: " + prices.containsValue(150.0));

        // Remove
        prices.remove("Sculpture Gallery");
        System.out.println("\nAfter removing 'Sculpture Gallery': " + prices);

        // Get or default
        System.out.println("\nGet or default:");
        System.out.println("  " + prices.getOrDefault("Unknown", 0.0));

        System.out.println();
    }

    /**
     * Demonstrate TreeMap
     *
     * Demonstrates:
     * - Sorted by key
     * - O(log n) operations
     * - Natural ordering
     */
    private static void demonstrateTreeMap() {
        System.out.println("2. TREEMAP (Sorted by Key)");
        System.out.println("-".repeat(70));

        Map<String, Double> prices = new TreeMap<>();

        prices.put("Modern Art", 200.0);
        prices.put("Ancient India", 150.0);
        prices.put("Natural History", 100.0);
        prices.put("Sculpture Gallery", 175.0);

        System.out.println("\nTreeMap contents (sorted alphabetically):");
        System.out.println("  " + prices);

        // First and last keys
        System.out.println("\nFirst and last entries:");
        System.out.println("  First key: " + ((TreeMap<String, Double>) prices).firstKey());
        System.out.println("  Last key: " + ((TreeMap<String, Double>) prices).lastKey());
        System.out.println("  First entry: " + ((TreeMap<String, Double>) prices).firstEntry());
        System.out.println("  Last entry: " + ((TreeMap<String, Double>) prices).lastEntry());

        // Head map (up to but not including key)
        System.out.println("\nHead map (up to 'Modern Art'):");
        Map<String, Double> headMap = ((TreeMap<String, Double>) prices).headMap("Modern Art");
        System.out.println("  " + headMap);

        // Tail map (from key onwards)
        System.out.println("\nTail map (from 'Natural History'):");
        Map<String, Double> tailMap = ((TreeMap<String, Double>) prices).tailMap("Natural History");
        System.out.println("  " + tailMap);

        System.out.println();
    }

    /**
     * Demonstrate LinkedHashMap
     *
     * Demonstrates:
     * - Insertion order preserved
     * - Doubly-linked list
     * - Can be used as LRU cache
     */
    private static void demonstrateLinkedHashMap() {
        System.out.println("3. LINKEDHASHMAP (Insertion Order)");
        System.out.println("-".repeat(70));

        Map<String, Double> prices = new LinkedHashMap<>();

        // Order matters - preserved on iteration
        prices.put("Ancient India", 150.0);
        prices.put("Modern Art", 200.0);
        prices.put("Natural History", 100.0);
        prices.put("Sculpture Gallery", 175.0);

        System.out.println("\nLinkedHashMap contents (insertion order preserved):");
        System.out.println("  " + prices);

        System.out.println("\nInsertion order iteration:");
        for (Map.Entry<String, Double> entry : prices.entrySet()) {
            System.out.println("  " + entry.getKey() + " → ₹" + entry.getValue());
        }

        // LRU Cache example
        System.out.println("\nLRU Cache (Access order LinkedHashMap):");
        Map<String, String> lruCache = new LinkedHashMap<String, String>(4, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 4; // Keep only 4 most recently used
            }
        };

        lruCache.put("A", "Value A");
        lruCache.put("B", "Value B");
        lruCache.put("C", "Value C");
        lruCache.put("D", "Value D");
        System.out.println("  After adding 4 items: " + lruCache);

        lruCache.get("A"); // Access A (moves to end)
        lruCache.put("E", "Value E"); // Add new item (B gets removed as least recently used)
        System.out.println("  After accessing A and adding E: " + lruCache);

        System.out.println();
    }

    /**
     * Demonstrate Map operations
     *
     * Demonstrates:
     * - putIfAbsent
     * - merge
     * - compute
     * - replace
     */
    private static void demonstrateMapOperations() {
        System.out.println("4. MAP OPERATIONS (Java 8+)");
        System.out.println("-".repeat(70));

        Map<String, Integer> visitors = new HashMap<>();

        System.out.println("\nputIfAbsent (add if key doesn't exist):");
        visitors.putIfAbsent("Ancient India", 100);
        visitors.putIfAbsent("Modern Art", 150);
        System.out.println("  After putIfAbsent: " + visitors);

        visitors.putIfAbsent("Ancient India", 200); // Won't replace
        System.out.println("  After second putIfAbsent (same key): " + visitors);

        System.out.println("\ncompute (compute value based on current value):");
        visitors.compute("Ancient India", (k, v) -> v + 50); // Increment
        System.out.println("  After incrementing by 50: " + visitors);

        System.out.println("\nmerge (combine values):");
        Map<String, Integer> moreVisitors = new HashMap<>();
        moreVisitors.put("Ancient India", 50);
        moreVisitors.put("Natural History", 200);

        for (Map.Entry<String, Integer> entry : moreVisitors.entrySet()) {
            visitors.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
        System.out.println("  After merging: " + visitors);

        System.out.println("\nreplace (replace if key exists):");
        visitors.replace("Modern Art", 200);
        System.out.println("  After replace: " + visitors);

        System.out.println();
    }

    /**
     * Demonstrate Map iteration
     *
     * Demonstrates:
     * - entrySet() - most efficient
     * - keySet()
     * - values()
     * - Iterator
     */
    private static void demonstrateMapIteration() {
        System.out.println("5. MAP ITERATION METHODS");
        System.out.println("-".repeat(70));

        Map<String, Double> prices = new HashMap<>();
        prices.put("Ancient India", 150.0);
        prices.put("Modern Art", 200.0);
        prices.put("Natural History", 100.0);

        System.out.println("\nOriginal map: " + prices);

        // Most efficient - entrySet()
        System.out.println("\nUsing entrySet() (MOST EFFICIENT):");
        for (Map.Entry<String, Double> entry : prices.entrySet()) {
            System.out.println("  " + entry.getKey() + " → ₹" + entry.getValue());
        }

        // Using keySet()
        System.out.println("\nUsing keySet():");
        for (String key : prices.keySet()) {
            System.out.println("  " + key + " → ₹" + prices.get(key));
        }

        // Using values()
        System.out.println("\nUsing values() - only values:");
        for (Double value : prices.values()) {
            System.out.println("  Price: ₹" + value);
        }

        // Using Iterator
        System.out.println("\nUsing Iterator:");
        Iterator<Map.Entry<String, Double>> iterator = prices.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Double> entry = iterator.next();
            System.out.println("  " + entry.getKey() + " → ₹" + entry.getValue());
        }

        System.out.println();
    }
}