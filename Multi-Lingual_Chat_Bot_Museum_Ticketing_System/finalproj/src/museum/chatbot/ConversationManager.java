package museum.chatbot;

import museum.model.User;
import museum.model.Visitor;
import museum.model.Admin;
import museum.model.Language;
import museum.localization.LocalizationService;

/**
 * ConversationManager.java
 * Manages conversation state and user context
 *
 * Demonstrates:
 * - State management
 * - User session tracking
 * - Conversation context maintenance
 * - Encapsulation of conversation state
 *
 * @author Sriram S
 * @version 1.0
 */
public class ConversationManager {

    /**
     * Conversation states
     *
     * Demonstrates:
     * - Enum for state management
     */
    public enum State {
        WELCOME,           // Initial state, showing welcome
        LANGUAGE_SELECT,   // Language selection
        AUTH_MENU,         // Authentication menu
        REGISTERING,       // Registration in progress
        LOGGING_IN,        // Login in progress
        MAIN_MENU,         // Main menu (logged in)
        VIEWING_EXHIBITS,  // Viewing exhibits
        SEARCHING,         // Searching exhibits
        BOOKING,           // Booking in progress
        PAYMENT,           // Payment in progress
        ADMIN_MENU,        // Admin menu
        EXITING            // Application exiting
    }

    /**
     * Current conversation state
     */
    private State currentState;

    /**
     * Currently logged-in user
     */
    private User currentUser;

    /**
     * Localization service for this conversation
     */
    private LocalizationService localizationService;

    /**
     * Temporary data for current operation
     * Example: exhibit being booked, payment method, etc.
     */
    private java.util.Map<String, Object> tempData;

    /**
     * Constructor
     */
    public ConversationManager() {
        this.currentState = State.WELCOME;
        this.currentUser = null;
        this.localizationService = new LocalizationService(Language.ENGLISH);
        this.tempData = new java.util.HashMap<>();
    }

    // ==================== STATE MANAGEMENT ====================

    /**
     * Get current state
     * @return current conversation state
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Set current state
     * @param state new state
     */
    public void setCurrentState(State state) {
        this.currentState = state;
        // Clear temp data when changing major states
        if (state == State.MAIN_MENU || state == State.WELCOME) {
            tempData.clear();
        }
    }

    /**
     * Check if user is logged in
     * @return true if user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null && currentUser.isAccountActive();
    }

    /**
     * Check if current user is a visitor
     * @return true if visitor
     */
    public boolean isVisitor() {
        return currentUser instanceof Visitor;
    }

    /**
     * Check if current user is an admin
     * @return true if admin
     */
    public boolean isAdmin() {
        return currentUser instanceof Admin;
    }

    // ==================== USER MANAGEMENT ====================

    /**
     * Set logged-in user
     * @param user the user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (user != null) {
            this.currentState = State.MAIN_MENU;
        }
    }

    /**
     * Get current user
     * @return current user, or null if not logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Get current visitor
     * @return current visitor, or null if not logged in or not visitor
     */
    public Visitor getCurrentVisitor() {
        if (currentUser instanceof Visitor) {
            return (Visitor) currentUser;
        }
        return null;
    }

    /**
     * Get current admin
     * @return current admin, or null if not logged in or not admin
     */
    public Admin getCurrentAdmin() {
        if (currentUser instanceof Admin) {
            return (Admin) currentUser;
        }
        return null;
    }

    /**
     * Logout current user
     */
    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
        }
        currentUser = null;
        currentState = State.WELCOME;
        tempData.clear();
    }

    // ==================== LANGUAGE MANAGEMENT ====================

    /**
     * Set conversation language
     * @param language the language
     */
    public void setLanguage(Language language) {
        try {
            localizationService.setLanguage(language);
        } catch (IllegalArgumentException e) {
            // Language not supported, keep current language
        }
    }

    /**
     * Get current language
     * @return current language
     */
    public Language getCurrentLanguage() {
        return localizationService.getCurrentLanguage();
    }

    /**
     * Get localization service
     * @return the localization service
     */
    public LocalizationService getLocalization() {
        return localizationService;
    }

    /**
     * Get localized message
     * @param key message key
     * @return translated message
     */
    public String getMessage(String key) {
        return localizationService.get(key);
    }

    /**
     * Get localized message with parameters
     * @param key message key
     * @param params parameters
     * @return translated message with parameters
     */
    public String getMessage(String key, Object... params) {
        return localizationService.get(key, params);
    }

    // ==================== TEMPORARY DATA MANAGEMENT ====================

    /**
     * Store temporary data
     *
     * Demonstrates:
     * - HashMap for temporary context storage
     *
     * @param key data key
     * @param value data value
     */
    public void setTempData(String key, Object value) {
        tempData.put(key, value);
    }

    /**
     * Get temporary data
     * @param key data key
     * @return data value, or null if not found
     */
    public Object getTempData(String key) {
        return tempData.get(key);
    }

    /**
     * Check if temporary data exists
     * @param key data key
     * @return true if key exists
     */
    public boolean hasTempData(String key) {
        return tempData.containsKey(key);
    }

    /**
     * Clear temporary data
     */
    public void clearTempData() {
        tempData.clear();
    }

    /**
     * Remove specific temporary data
     * @param key data key
     */
    public void removeTempData(String key) {
        tempData.remove(key);
    }

    // ==================== CONVERSATION FLOW ====================

    /**
     * Get appropriate welcome message based on state
     *
     * Demonstrates:
     * - State-based message selection
     * - Localization integration
     *
     * @return welcome message
     */
    public String getWelcomeMessage() {
        switch (currentState) {
            case WELCOME:
                return getMessage("app.welcome");
            case MAIN_MENU:
                return getMessage("auth.menu");
            case LANGUAGE_SELECT:
                return getMessage("lang.select");
            default:
                return "";
        }
    }

    /**
     * Get conversation summary
     * @return summary of current conversation state
     */
    public String getConversationSummary() {
        StringBuilder summary = new StringBuilder();

        summary.append("\n[Conversation State: ").append(currentState).append("]\n");

        if (isLoggedIn()) {
            summary.append("[Logged In: ").append(currentUser.getName()).append("]\n");
        } else {
            summary.append("[Not Logged In]\n");
        }

        summary.append("[Language: ").append(getCurrentLanguage()).append("]\n");

        return summary.toString();
    }
}