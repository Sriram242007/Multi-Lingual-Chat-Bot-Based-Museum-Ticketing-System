package museum.threading;

import java.util.concurrent.*;

/**
 * ThreadPoolDemo.java
 * Demonstration of thread pools and ExecutorService
 *
 * Demonstrates:
 * - Fixed thread pool
 * - Cached thread pool
 * - Single thread executor
 * - Scheduled executor
 * - Future objects
 * - Task submission
 * - Shutdown and awaitTermination
 *
 * @author Sriram S
 * @version 1.0
 */
public class ThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("THREAD POOL & EXECUTORSERVICE DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateFixedThreadPool();
        demonstrateCachedThreadPool();
        demonstrateSingleThreadExecutor();
        demonstrateScheduledExecutor();
        demonstrateFutures();

        System.out.println("\n✓ Thread pool demonstration complete\n");
    }

    /**
     * Demonstrate fixed thread pool
     *
     * Demonstrates:
     * - Create pool with fixed number of threads
     * - Reuse threads for multiple tasks
     * - Queue for waiting tasks
     */
    private static void demonstrateFixedThreadPool() throws InterruptedException {
        System.out.println("1. FIXED THREAD POOL");
        System.out.println("-".repeat(70));

        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("\nCreated fixed thread pool with 3 threads");
        System.out.println("Submitting 10 tasks:");

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("  Task " + taskId + " executed by " +
                        Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        System.out.println("\nShutting down thread pool...");
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("All tasks completed");

        System.out.println();
    }

    /**
     * Demonstrate cached thread pool
     *
     * Demonstrates:
     * - Creates new threads as needed
     * - Reuses idle threads
     * - Good for short-lived tasks
     */
    private static void demonstrateCachedThreadPool() throws InterruptedException {
        System.out.println("2. CACHED THREAD POOL");
        System.out.println("-".repeat(70));

        ExecutorService executor = Executors.newCachedThreadPool();

        System.out.println("\nCached thread pool (creates threads as needed)");
        System.out.println("Submitting 5 short tasks:");

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("  Task " + taskId + " by " +
                        Thread.currentThread().getName());
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Threads are cached and reused");

        System.out.println();
    }

    /**
     * Demonstrate single thread executor
     *
     * Demonstrates:
     * - Single thread processes tasks sequentially
     * - Guarantees task order
     */
    private static void demonstrateSingleThreadExecutor() throws InterruptedException {
        System.out.println("3. SINGLE THREAD EXECUTOR");
        System.out.println("-".repeat(70));

        ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.println("\nSingle thread executor (sequential processing):");

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("  Task " + taskId + " processed");
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Tasks executed sequentially by single thread");

        System.out.println();
    }

    /**
     * Demonstrate scheduled executor
     *
     * Demonstrates:
     * - Schedule tasks after delay
     * - Schedule periodic tasks
     */
    private static void demonstrateScheduledExecutor() throws InterruptedException {
        System.out.println("4. SCHEDULED EXECUTOR");
        System.out.println("-".repeat(70));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        System.out.println("\nSchedule one-time task (after 1 second):");
        executor.schedule(() -> {
            System.out.println("  Delayed task executed");
        }, 1, TimeUnit.SECONDS);

        System.out.println("\nSchedule periodic task (repeat every 500ms, for 2 seconds):");
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            System.out.println("  Periodic task executed");
        }, 0, 500, TimeUnit.MILLISECONDS);

        Thread.sleep(2000); // Let periodic task run
        future.cancel(false); // Cancel periodic task

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println();
    }

    /**
     * Demonstrate Future objects
     *
     * Demonstrates:
     * - Submit callable tasks
     * - Get results with Future
     * - Wait for task completion
     */
    private static void demonstrateFutures() throws InterruptedException, ExecutionException {
        System.out.println("5. FUTURE OBJECTS");
        System.out.println("-".repeat(70));

        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("\nSubmitting tasks that return values:");

        Future<Integer> future1 = executor.submit(() -> {
            Thread.sleep(200);
            return 100;
        });

        Future<Integer> future2 = executor.submit(() -> {
            Thread.sleep(300);
            return 200;
        });

        Future<Integer> future3 = executor.submit(() -> {
            Thread.sleep(100);
            return 300;
        });

        System.out.println("  Tasks submitted, getting results...");

        // Get results (blocks until ready)
        int result1 = future1.get();
        int result2 = future2.get();
        int result3 = future3.get();

        System.out.println("  Result 1: " + result1);
        System.out.println("  Result 2: " + result2);
        System.out.println("  Result 3: " + result3);
        System.out.println("  Total: " + (result1 + result2 + result3));

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println();
    }
}