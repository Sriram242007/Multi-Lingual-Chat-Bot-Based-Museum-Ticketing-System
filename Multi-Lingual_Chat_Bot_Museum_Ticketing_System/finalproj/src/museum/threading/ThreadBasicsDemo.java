package museum.threading;

/**
 * ThreadBasicsDemo.java
 * Demonstration of thread creation and lifecycle
 *
 * Demonstrates:
 * - Creating threads (extend Thread, implement Runnable)
 * - Thread states and lifecycle
 * - Thread methods (start, join, sleep, yield, interrupt)
 * - Daemon threads
 * - Thread names and IDs
 *
 * @author Sriram S
 * @version 1.0
 */
public class ThreadBasicsDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("THREAD BASICS DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        demonstrateThreadCreation();
        demonstrateThreadStates();
        demonstrateThreadJoin();
        demonstrateDaemonThreads();
        demonstrateThreadInterruption();

        System.out.println("\n✓ Thread basics demonstration complete\n");
    }

    /**
     * Demonstrate two ways to create threads
     *
     * Demonstrates:
     * - Extend Thread class
     * - Implement Runnable interface (preferred)
     */
    private static void demonstrateThreadCreation() {
        System.out.println("1. THREAD CREATION");
        System.out.println("-".repeat(70));

        // Method 1: Extend Thread
        System.out.println("\nMethod 1: Extend Thread class");
        MyThread thread1 = new MyThread("Thread-1");
        System.out.println("  Created: " + thread1.getName());
        thread1.start();

        // Method 2: Implement Runnable (preferred)
        System.out.println("\nMethod 2: Implement Runnable interface (preferred)");
        Thread thread2 = new Thread(new MyRunnable("Thread-2"));
        System.out.println("  Created: " + thread2.getName());
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
    }

    /**
     * Demonstrate thread states
     *
     * Demonstrates:
     * - NEW
     * - RUNNABLE
     * - RUNNING
     * - BLOCKED/WAITING
     * - TERMINATED
     */
    private static void demonstrateThreadStates() {
        System.out.println("2. THREAD STATES");
        System.out.println("-".repeat(70));

        StateWatcherThread watcher = new StateWatcherThread("StateTest");

        System.out.println("\nThread states during lifecycle:");
        System.out.println("  NEW state (before start): " + watcher.getState());

        watcher.start();

        try {
            // Check state while running
            Thread.sleep(50);
            System.out.println("  Running state: " + watcher.getState());

            watcher.join();
            System.out.println("  TERMINATED state (after join): " + watcher.getState());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
    }

    /**
     * Demonstrate join() - waiting for thread completion
     *
     * Demonstrates:
     * - Thread.join()
     * - Synchronizing with thread completion
     */
    private static void demonstrateThreadJoin() {
        System.out.println("3. THREAD JOIN");
        System.out.println("-".repeat(70));

        Thread[] threads = new Thread[3];

        System.out.println("\nCreating and starting 3 threads:");
        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread(new MyRunnable("Worker-" + (i + 1)));
            threads[i].start();
            System.out.println("  Started " + threads[i].getName());
        }

        System.out.println("\nWaiting for all threads to complete...");
        try {
            for (Thread thread : threads) {
                thread.join(); // Wait for this thread
                System.out.println("  Completed: " + thread.getName());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("  All threads completed!");

        System.out.println();
    }

    /**
     * Demonstrate daemon threads
     *
     * Demonstrates:
     * - Daemon threads run in background
     * - JVM exits when all non-daemon threads finish
     */
    private static void demonstrateDaemonThreads() {
        System.out.println("4. DAEMON THREADS");
        System.out.println("-".repeat(70));

        // Daemon thread
        Thread daemon = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("  [Daemon] Running in background...");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("  [Daemon] Interrupted");
            }
        }, "DaemonThread");

        daemon.setDaemon(true); // Set as daemon before starting

        // User thread
        Thread userThread = new Thread(() -> {
            try {
                Thread.sleep(1500);
                System.out.println("  [User] Finished");
            } catch (InterruptedException e) {
                System.out.println("  [User] Interrupted");
            }
        }, "UserThread");

        System.out.println("\nDaemon vs User threads:");
        daemon.start();
        userThread.start();

        try {
            userThread.join();
            System.out.println("  Main thread: User thread finished");
            System.out.println("  (Daemon thread will terminate automatically)");
            Thread.sleep(100); // Let daemon print once more
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
    }

    /**
     * Demonstrate thread interruption
     *
     * Demonstrates:
     * - Thread.interrupt()
     * - InterruptedException
     * - Graceful thread termination
     */
    private static void demonstrateThreadInterruption() {
        System.out.println("5. THREAD INTERRUPTION");
        System.out.println("-".repeat(70));

        InterruptibleThread thread = new InterruptibleThread("Interruptible");

        System.out.println("\nStarting interruptible thread:");
        thread.start();

        try {
            Thread.sleep(1500);
            System.out.println("  Main: Interrupting thread...");
            thread.interrupt();

            thread.join();
            System.out.println("  Main: Thread terminated gracefully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
    }

    // ==================== HELPER CLASSES ====================

    /**
     * Method 1: Extend Thread class
     */
    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("  " + getName() + " - iteration " + (i + 1));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Method 2: Implement Runnable interface
     */
    static class MyRunnable implements Runnable {
        private String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("  " + name + " - iteration " + (i + 1));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Thread that reports its state
     */
    static class StateWatcherThread extends Thread {
        public StateWatcherThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Thread that can be interrupted gracefully
     */
    static class InterruptibleThread extends Thread {
        public InterruptibleThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("  " + getName() + " detected interruption");
                        break;
                    }
                    System.out.println("  " + getName() + " - working... (" + (i + 1) + "/10)");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("  " + getName() + " interrupted during sleep");
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("  " + getName() + " cleaning up resources");
            }
        }
    }
}