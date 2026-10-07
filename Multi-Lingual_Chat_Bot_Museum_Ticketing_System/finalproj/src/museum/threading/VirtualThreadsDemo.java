package museum.threading;

import java.util.concurrent.*;
import java.time.Instant;

/**
 * VirtualThreadsDemo.java
 * Demonstration of Virtual Threads (Java 21 feature on Java 24)
 *
 * Demonstrates:
 * - Creating virtual threads
 * - Virtual threads vs platform threads
 * - Millions of virtual threads
 * - Lower resource overhead
 * - Structured concurrency (preview)
 *
 * Unit 4: Virtual Threads (Java 21)
 *
 * @author Your Name
 * @version 1.0
 */
public class VirtualThreadsDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("VIRTUAL THREADS DEMONSTRATION (Java 21 on Java 24)");
        System.out.println("=".repeat(70) + "\n");

        demonstrateVirtualThreadBasics();
        demonstrateVirtualThreadScale();
        demonstrateVirtualThreadsVsPlatformThreads();
        try {
            demonstrateStructuredConcurrency();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n✓ Virtual threads demonstration complete\n");
    }

    /**
     * Demonstrate basic virtual thread creation
     *
     * Demonstrates:
     * - Thread.ofVirtual().start()
     * - Creating virtual threads is lightweight
     */
    private static void demonstrateVirtualThreadBasics() throws InterruptedException {
        System.out.println("1. BASIC VIRTUAL THREAD CREATION");
        System.out.println("-".repeat(70));

        System.out.println("\nCreating and starting 5 virtual threads:");

        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            threads[i] = Thread.ofVirtual()
                    .name("VirtualThread-" + threadId)
                    .start(() -> {
                        try {
                            System.out.println("  " + Thread.currentThread().getName() +
                                    " (virtual: " + Thread.currentThread().isVirtual() + ")");
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
        }

        // Wait for all threads
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Virtual threads are lighter weight than platform threads");
        System.out.println("Thousands or millions can run without exhausting resources");

        System.out.println();
    }

    /**
     * Demonstrate creating thousands of virtual threads
     *
     * Demonstrates:
     * - Virtual threads can scale to millions
     * - Use ExecutorService with virtual thread factory
     */
    private static void demonstrateVirtualThreadScale() throws InterruptedException {
        System.out.println("2. VIRTUAL THREADS AT SCALE");
        System.out.println("-".repeat(70));

        System.out.println("\nCreating 10,000 virtual threads:");

        Instant start = Instant.now();

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        CountDownLatch latch = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(100); // Simulate I/O operation
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        Instant end = Instant.now();

        System.out.println("  10,000 tasks completed in " +
                (end.toEpochMilli() - start.toEpochMilli()) + " ms");
        System.out.println("With platform threads, this would be memory-intensive!");
        System.out.println("Virtual threads make this trivial");

        System.out.println();
    }

    /**
     * Compare virtual threads with platform threads
     *
     * Demonstrates:
     * - Virtual threads are much cheaper to create
     * - Platform threads have higher overhead
     */
    private static void demonstrateVirtualThreadsVsPlatformThreads()
            throws InterruptedException {
        System.out.println("3. VIRTUAL vs PLATFORM THREADS");
        System.out.println("-".repeat(70));

        System.out.println("\nComparison:");

        // Platform threads
        System.out.println("\nPlatform Threads (1000 threads):");
        Instant start = Instant.now();

        ExecutorService platformExecutor = Executors.newFixedThreadPool(10);
        CountDownLatch platformLatch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            platformExecutor.execute(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    platformLatch.countDown();
                }
            });
        }

        platformLatch.await();
        platformExecutor.shutdown();

        Instant end = Instant.now();
        long platformTime = end.toEpochMilli() - start.toEpochMilli();

        System.out.println("  Time: " + platformTime + " ms");
        System.out.println("  Memory: High (platform threads are OS-level)");

        // Virtual threads
        System.out.println("\nVirtual Threads (1000 threads):");
        start = Instant.now();

        ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();
        CountDownLatch virtualLatch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            virtualExecutor.execute(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    virtualLatch.countDown();
                }
            });
        }

        virtualLatch.await();
        virtualExecutor.shutdown();

        end = Instant.now();
        long virtualTime = end.toEpochMilli() - start.toEpochMilli();

        System.out.println("  Time: " + virtualTime + " ms");
        System.out.println("  Memory: Low (virtual threads are JVM-managed)");
        System.out.println("\nAdvantages of Virtual Threads:");
        System.out.println("  - Lightweight (can create millions)");
        System.out.println("  - Simpler to write (no complex threading code)");
        System.out.println("  - Better resource utilization");
        System.out.println("  - Seamless I/O operations");

        System.out.println();
    }

    /**
     * Demonstrate structured concurrency (preview)
     *
     * Demonstrates:
     * - try-with-resources for thread management
     * - Structured Concurrency API
     * - Automatic thread lifecycle management
     */
    private static void demonstrateStructuredConcurrency()
            throws InterruptedException, ExecutionException {
        System.out.println("4. STRUCTURED CONCURRENCY (PREVIEW)");
        System.out.println("-".repeat(70));

        System.out.println("\nStructured Concurrency Pattern:");
        System.out.println("  Automatic thread lifecycle management");

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Tasks are automatically managed by try-with-resources
            Future<Integer> future1 = executor.submit(() -> {
                Thread.sleep(200);
                return 100;
            });

            Future<Integer> future2 = executor.submit(() -> {
                Thread.sleep(300);
                return 200;
            });

            // All threads are automatically waited on and cleaned up
            // No need for manual shutdown() and awaitTermination()
            System.out.println("  Future1 result: " + future1.get());
            System.out.println("  Future2 result: " + future2.get());
        }
        // Executor is automatically shut down here

        System.out.println("\nStructured Concurrency benefits:");
        System.out.println("  - Automatic resource cleanup");
        System.out.println("  - Clearer code structure");
        System.out.println("  - Safer thread lifecycle management");
        System.out.println("  - Prevents thread leaks");

        System.out.println();
    }
}