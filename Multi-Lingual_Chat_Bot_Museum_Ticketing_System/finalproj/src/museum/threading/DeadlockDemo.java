package museum.threading;

/**
 * DeadlockDemo.java
 * Demonstration of deadlock scenarios and prevention strategies
 *
 * Demonstrates:
 * - What causes deadlock
 * - Detecting deadlock
 * - Preventing deadlock:
 *   1. Lock ordering
 *   2. Timeouts
 *   3. Single lock
 *   4. Deadlock avoidance algorithms
 *
 * @author Sriram S
 * @version 1.0
 */
public class DeadlockDemo {

    static class Account {
        private int id;
        private double balance;

        public Account(int id, double balance) {
            this.id = id;
            this.balance = balance;
        }

        public synchronized void withdraw(double amount) throws InterruptedException {
            if (balance >= amount) {
                Thread.sleep(50); // Simulate processing
                balance -= amount;
            }
        }

        public synchronized void deposit(double amount) throws InterruptedException {
            Thread.sleep(50); // Simulate processing
            balance += amount;
        }

        public int getId() {
            return id;
        }

        public double getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DEADLOCK DEMONSTRATION & PREVENTION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateDeadlock();
        demonstrateDeadlockPrevention();

        System.out.println("\n✓ Deadlock demonstration complete\n");
    }

    /**
     * Demonstrate deadlock scenario
     *
     * Demonstrates:
     * - Thread 1: locks A, waits for B
     * - Thread 2: locks B, waits for A
     * - Result: DEADLOCK
     */
    private static void demonstrateDeadlock() {
        System.out.println("1. DEADLOCK SCENARIO");
        System.out.println("-".repeat(70));

        Object lockA = new Object();
        Object lockB = new Object();

        System.out.println("\nScenario: Transfer between two accounts");
        System.out.println("  Thread 1: lockA → lockB");
        System.out.println("  Thread 2: lockB → lockA");
        System.out.println("  Result: DEADLOCK!\n");

        Thread thread1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 1: Acquired lock A");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread 1: Waiting for lock B...");
                synchronized (lockB) {
                    System.out.println("Thread 1: Acquired lock B");
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Thread 2: Acquired lock B");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread 2: Waiting for lock A...");
                synchronized (lockA) {
                    System.out.println("Thread 2: Acquired lock A");
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join(2000); // Wait max 2 seconds
            thread2.join(2000);

            if (thread1.isAlive() || thread2.isAlive()) {
                System.out.println("\n*** DEADLOCK DETECTED ***");
                System.out.println("Threads are stuck and cannot proceed!");
                System.out.println("Thread 1 alive: " + thread1.isAlive());
                System.out.println("Thread 2 alive: " + thread2.isAlive());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
    }

    /**
     * Demonstrate deadlock prevention strategies
     *
     * Demonstrates:
     * - Lock ordering: always acquire locks in same order
     * - Prevention ensures no deadlock
     */
    private static void demonstrateDeadlockPrevention() {
        System.out.println("2. DEADLOCK PREVENTION - LOCK ORDERING");
        System.out.println("-".repeat(70));

        Object lockA = new Object();
        Object lockB = new Object();

        System.out.println("\nPrevention strategy: Lock ordering");
        System.out.println("  Always lock in same order: A before B");
        System.out.println("  Thread 1: lockA → lockB");
        System.out.println("  Thread 2: lockA → lockB (same order!)\n");

        Thread thread1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 1: Acquired lock A");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (lockB) {
                    System.out.println("Thread 1: Acquired lock B");
                    System.out.println("Thread 1: Work completed");
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            // Same lock order as thread1: A before B
            synchronized (lockA) {
                System.out.println("Thread 2: Acquired lock A");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (lockB) {
                    System.out.println("Thread 2: Acquired lock B");
                    System.out.println("Thread 2: Work completed");
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
            System.out.println("\n*** NO DEADLOCK ***");
            System.out.println("Both threads completed successfully!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nDeadlock Prevention Strategies:");
        System.out.println("  1. Lock Ordering: Always acquire locks in same order");
        System.out.println("  2. Lock Timeout: Use tryLock() with timeout");
        System.out.println("  3. Single Lock: Use one lock for all resources");
        System.out.println("  4. Avoid nested locks: Minimize lock nesting");
        System.out.println("  5. Resource Hierarchy: Order resources and lock accordingly");

        System.out.println();
    }
}