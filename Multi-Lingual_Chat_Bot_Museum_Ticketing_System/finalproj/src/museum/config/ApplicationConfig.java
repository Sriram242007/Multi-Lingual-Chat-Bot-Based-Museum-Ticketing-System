package museum.config;

/**
 * ApplicationConfig.java
 * Central configuration for file paths and constants
 *
 * Demonstrates:
 * - Static final members (constant definition)
 * - Packages and access specifiers
 * - Application configuration pattern
 *
 * @author Your Name
 * @version 1.0
 */
public class ApplicationConfig {

    /**
     * Data directory path - relative to project root
     */
    public static final String DATA_DIR = "data/";

    /**
     * User data persistence file
     */
    public static final String USERS_FILE = DATA_DIR + "users.txt";

    /**
     * Exhibit data persistence file
     */
    public static final String EXHIBITS_FILE = DATA_DIR + "exhibits.txt";

    /**
     * Booking data persistence file
     */
    public static final String BOOKINGS_FILE = DATA_DIR + "bookings.txt";

    /**
     * Payment data persistence file
     */
    public static final String PAYMENTS_FILE = DATA_DIR + "payments.txt";

    /**
     * Translations persistence file
     */
    public static final String TRANSLATIONS_FILE = DATA_DIR + "translations.txt";

    /**
     * Receipts directory
     */
    public static final String RECEIPTS_DIR = DATA_DIR + "receipts/";

    /**
     * Default configuration values
     */
    public static final int DEFAULT_SLOTS_PER_DAY = 6;
    public static final int DEFAULT_SLOT_CAPACITY = 30;
    public static final int MIN_PASSWORD_LENGTH = 6;

    /**
     * Admin credentials
     */
    public static final String DEFAULT_ADMIN_EMAIL = "admin@museum.com";
    public static final String DEFAULT_ADMIN_PASSWORD = "admin123";

    /**
     * Application info
     */
    public static final String APP_NAME = "Museum Ticketing System";
    public static final String VERSION = "1.0";

    /**
     * Private constructor to prevent instantiation
     */
    private ApplicationConfig() {
        throw new AssertionError("Cannot instantiate ApplicationConfig");
    }
}