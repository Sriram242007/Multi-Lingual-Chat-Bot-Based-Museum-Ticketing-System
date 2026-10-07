package museum.threading;

import java.util.*;
import java.util.concurrent.*;

/**
 * ConcurrentCollectionsDemo.java
 * Demonstration of thread-safe collections
 *
 * Demonstrates:
 * - ConcurrentHashMap
 * - CopyOnWriteArrayList
 * - BlockingQueue
 * - ConcurrentLinkedQueue
 * - When to use concurrent collections
 *
 * @author Sriram S
 * @version 1.0
 */
public class ConcurrentCollectionsDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CONCURRENT COLLECTIONS DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateConcurrentHashMap();
        demonstrateCopyOnWriteArrayList();
        demonstrateBlockingQueue();
        demonstrateConcurrentLinkedQueue();

        System.out.println("\n✓ Concurrent collections demonstration complete\n");
    }

    /**
     * Demonstrate ConcurrentHashMap
     *
     * Demonstrates:
     * - Thread-safe HashMap
     * - Segment-based locking (better than full synchronization)
     * - Concurrent reads and writes
     */
    private static void demonstrateConcurrentHashMap() throws InterruptedException {
        System.out.println("1. CONCURRENTHASHMAP");
        System.out.println("-".repeat(70));

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        System.out.println("\nConcurrent reads and writes:");
        System.out.println("  Creating 3 writer threads and 2 reader threads");

        // Writer threads
        for (int i = 0; i < 3; i++) {
            final int writerId = i;
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    map.put("Key" + (writerId * 100 + j), (writerId * 100 + j));
                }
                System.out.println("  Writer " + writerId + " finished");
            }).start();
        }

        // Reader threads
        for (int i = 0; i < 2; i++) {
            final int readerId = i;
            new Thread(() -> {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("  Reader " + readerId + " sees " +
                        map.size() + " entries");
            }).start();
        }

        Thread.sleep(1000);
        System.out.println("\nFinal size: " + map.size());
        System.out.println("ConcurrentHashMap allows concurrent reads/writes");
        System.out.println("Uses segment-based locking for better performance");

        System.out.println();
    }

    /**
     * Demonstrate CopyOnWriteArrayList
     *
     * Demonstrates:
     * - Thread-safe ArrayList
     * - Copies array on write
     * - Good for read-heavy workloads
     */
    private static void demonstrateCopyOnWriteArrayList() throws InterruptedException {
        System.out.println("2. COPYONWRITEARRAYLIST");
        System.out.println("-".repeat(70));

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        System.out.println("\nRead-heavy workload with occasional writes:");

        // Add initial data
        for (int i = 0; i < 5; i++) {
            list.add("Item" + i);
        }

        // Multiple readers
        for (int i = 0; i < 3; i++) {
            final int readerId = i;
            new Thread(() -> {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                int count = 0;
                for (String item : list) {
                    count++;
                }
                System.out.println("  Reader " + readerId + " counted " + count + " items");
            }).start();
        }

        // Single writer
        new Thread(() -> {
            try {
                Thread.sleep(100);
                list.add("NewItem");
                System.out.println("  Writer added new item");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        Thread.sleep(1000);
        System.out.println("\nFinal size: " + list.size());
        System.out.println("CopyOnWriteArrayList is best for read-heavy operations");
        System.out.println("Writes are expensive (copy array), but reads are fast");

        System.out.println();
    }

    /**
     * Demonstrate BlockingQueue
     *
     * Demonstrates:
     * - Producer-consumer pattern
     * - put() and take() block when needed
     * - Thread-safe queue for multi-threaded coordination
     */
    private static void demonstrateBlockingQueue() throws InterruptedException {
        System.out.println("3. BLOCKINGQUEUE");
        System.out.println("-".repeat(70));

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(3);

        System.out.println("\nProducer-Consumer pattern with BlockingQueue:");
        System.out.println("  Queue capacity: 3");

        // Producer
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    String item = "Item" + i;
                    queue.put(item);
                    System.out.println("  Producer added: " + item +
                            " (queue size: " + queue.size() + ")");
                    Thread.sleep(100);
                }
                queue.put("STOP");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // Consumer
        new Thread(() -> {
            try {
                while (true) {
                    String item = queue.take();
                    if ("STOP".equals(item)) {
                        System.out.println("  Consumer: received STOP signal");
                        break;
                    }
                    System.out.println("  Consumer took: " + item +
                            " (queue size: " + queue.size() + ")");
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        Thread.sleep(3000);
        System.out.println("BlockingQueue coordinates producer and consumer threads");

        System.out.println();
    }

    /**
     * Demonstrate ConcurrentLinkedQueue
     *
     * Demonstrates:
     * - Lock-free queue (non-blocking)
     * - CAS (Compare-and-Swap) operations
     * - High-performance concurrent queue
     */
    private static void demonstrateConcurrentLinkedQueue() throws InterruptedException {
        System.out.println("4. CONCURRENTLINKEDQUEUE");
        System.out.println("-".repeat(70));

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        System.out.println("\nLock-free concurrent queue:");
        System.out.println("  Creating multiple producers and consumers");

        // Producers
        for (int i = 0; i < 2; i++) {
            final int producerId = i;
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    String item = "P" + producerId + "-Item" + j;
                    queue.offer(item);
                }
            }).start();
        }

        // Consumers
        for (int i = 0; i < 2; i++) {
            final int consumerId = i;
            new Thread(() -> {
                String item;
                int count = 0;
                while ((item = queue.poll()) != null) {
                    count++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("  Consumer " + consumerId + " consumed " + count + " items");
            }).start();
        }

        Thread.sleep(2000);
        System.out.println("ConcurrentLinkedQueue uses lock-free algorithms");
        System.out.println("Based on CAS (Compare-and-Swap) for high performance");

        System.out.println();
    }
}