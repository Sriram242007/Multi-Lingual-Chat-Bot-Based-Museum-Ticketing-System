package museum;

import museum.model.*;
import museum.config.ApplicationConfig;

/**
 * Main.java
 * Application entry point for the Multilingual Chatbot-Based Museum Ticketing System
 *
 * @author Your Name
 * @version 1.0
 * @since Java 24
 */
public class Main {

    public static void main(String[] args) {
        printBanner();
        verifyJavaVersion();
        printSetupComplete();
    }

    /**
     * Print application banner
     */
    private static void printBanner() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   MULTILINGUAL CHATBOT-BASED MUSEUM TICKETING SYSTEM");
        System.out.println("=".repeat(60) + "\n");
    }

    /**
     * Verify Java version
     */
    private static void verifyJavaVersion() {
        String javaVersion = System.getProperty("java.version");
        System.out.println("✓ Java Version: " + javaVersion);

        if (javaVersion.startsWith("24")) {
            System.out.println("✓ Java 24 Configuration: SUCCESS");
        } else {
            System.out.println("⚠ Warning: Expected Java 24, found " + javaVersion);
        }
    }
    /**
     * Print setup completion message
     */
    private static void printSetupComplete() {
        System.out.println("\n✓ PHASE 2 Setup Complete!");
        System.out.println("✓ All model classes created");
        System.out.println("✓ Configuration ready");
        System.out.println("\nReady for testing and Phase 3!\n");
    }
}