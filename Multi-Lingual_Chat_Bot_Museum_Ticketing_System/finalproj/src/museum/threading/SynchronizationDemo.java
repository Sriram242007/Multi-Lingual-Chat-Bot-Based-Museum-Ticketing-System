package museum.threading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SynchronizationDemo.java
 * Demonstration of synchronization and thread safety
 *
 * Demonstrates:
 * - Race conditions (without synchronization)
 * - Synchronized methods
 * - Synchronized blocks
 * - Lock objects (ReentrantLock)
 * - Volatile keyword
 * - Thread safety
 *
 * @author Sriram S
 * @version 1.0
 */
public class SynchronizationDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SYNCHRONIZATION & THREAD SAFETY DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateRaceCondition();
        demonstrateSynchronizedMethod();
        demonstrateSynchronizedBlock();
        demonstrateReentrantLock();
        demonstrateVolatile();

        System.out.println("\n✓ Synchronization demonstration complete\n");
    }

    /**
     * Demonstrate race condition without synchronization
     *
     * Demonstrates:
     * - Multiple threads accessing shared variable
     * - Unpredictable results
     * - Why synchronization is needed
     */
    private static void demonstrateRaceCondition() {
        System.out.println("1. RACE CONDITION (WITHOUT SYNCHRONIZATION)");
        System.out.println("-".repeat(70));

        UnsafeCounter counter = new UnsafeCounter();

        System.out.println("\nWithout synchronization (UNSAFE):");
        System.out.println("  Creating 5 threads, each incrementing 1000 times");
        System.out.println("  Expected final value: 5000");
        System.out.println("  Actual value (varies due to race condition):");

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("    Final count: " + counter.getCount());
        System.out.println("  Note: Value is less than 5000 due to race condition!");

        System.out.println();
    }

    /**
     * Demonstrate synchronized method
     *
     * Demonstrates:
     * - Synchronized methods
     * - Mutual exclusion
     * - Correct results
     */
    private static void demonstrateSynchronizedMethod() {
        System.out.println("2. SYNCHRONIZED METHOD");
        System.out.println("-".repeat(70));

        SafeCounter counter = new SafeCounter();

        System.out.println("\nWith synchronized method (SAFE):");
        System.out.println("  Creating 5 threads, each incrementing 1000 times");
        System.out.println("  Expected final value: 5000");

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("  Final count: " + counter.getCount());
        System.out.println("  Correct! Synchronized method prevents race condition.");

        System.out.println();
    }

    /**
     * Demonstrate synchronized block
     *
     * Demonstrates:
     * - Synchronized block (more fine-grained control)
     * - Locking on specific objects
     * - Better performance than synchronized method
     */
    private static void demonstrateSynchronizedBlock() {
        System.out.println("3. SYNCHRONIZED BLOCK");
        System.out.println("-".repeat(70));

        BlockCounter counter = new BlockCounter();

        System.out.println("\nUsing synchronized block:");
        System.out.println("  Creating 5 threads, each incrementing 1000 times");

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("  Final count: " + counter.getCount());
        System.out.println("  Synchronized block: only lock when needed (more efficient)");

        System.out.println();
    }

    /**
     * Demonstrate ReentrantLock
     *
     * Demonstrates:
     * - Explicit Lock objects
     * - Lock and unlock
     * - Try-lock
     * - Fair locking
     */
    private static void demonstrateReentrantLock() throws InterruptedException {
        System.out.println("4. REENTRANT LOCK");
        System.out.println("-".repeat(70));

        LockCounter counter = new LockCounter();

        System.out.println("\nUsing ReentrantLock:");
        System.out.println("  Creating 5 threads, each incrementing 1000 times");

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("  Final count: " + counter.getCount());
        System.out.println("  ReentrantLock provides explicit control over locking");
        System.out.println("  Features: tryLock, conditions, fair/unfair modes");

        System.out.println();
    }

    /**
     * Demonstrate volatile keyword
     *
     * Demonstrates:
     * - Visibility of changes across threads
     * - Not a substitute for synchronization
     * - Use case: simple boolean flags
     */
    private static void demonstrateVolatile() {
        System.out.println("5. VOLATILE KEYWORD");
        System.out.println("-".repeat(70));

        VolatileFlag flag = new VolatileFlag();

        System.out.println("\nUsing volatile for visibility:");

        Thread reader = new Thread(() -> {
            int count = 0;
            while (!flag.isStop()) {
                count++;
            }
            System.out.println("  Reader thread stopped after " + count + " iterations");
        }, "Reader");

        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(100);
                System.out.println("  Writer thread setting stop flag");
                flag.setStop(true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Writer");

        reader.start();
        writer.start();

        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("  Volatile ensures visibility of flag change");
        System.out.println("  Use volatile for simple flags, synchronized for complex operations");

        System.out.println();
    }

    // ==================== HELPER CLASSES ====================

    /**
     * Counter WITHOUT synchronization - UNSAFE!
     */
    static class UnsafeCounter {
        private int count = 0;

        public void increment() {
            count++; // NOT atomic - race condition!
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * Counter WITH synchronized method - SAFE
     */
    static class SafeCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    /**
     * Counter using synchronized block
     */
    static class BlockCounter {
        private int count = 0;
        private final Object lock = new Object();

        public void increment() {
            synchronized (lock) {
                count++;
            }
        }

        public int getCount() {
            synchronized (lock) {
                return count;
            }
        }
    }

    /**
     * Counter using ReentrantLock
     */
    static class LockCounter {
        private int count = 0;
        private final Lock lock = new ReentrantLock();

        public void increment() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }

        public int getCount() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * Flag using volatile keyword
     */
    static class VolatileFlag {
        private volatile boolean stop = false;

        public boolean isStop() {
            return stop;
        }

        public void setStop(boolean value) {
            stop = value;
        }
    }
}