package museum.chatbot;

import museum.model.Language;
import java.util.Scanner;

/**
 * Chatbot.java
 * Main chatbot orchestrator - controls conversation flow
 *
 * Demonstrates:
 * - Main orchestration logic
 * - Conversation flow management
 * - Integration of all chatbot components
 * - User I/O handling
 * - Exception handling
 *
 * @author Sriram S
 * @version 1.0
 */
public class Chatbot {

    /**
     * Conversation manager for state tracking
     */
    private final ConversationManager conversationManager;

    /**
     * Input scanner for user input
     */
    private Scanner scanner;

    /**
     * Flag to control main loop
     */
    private boolean isRunning;

    /**
     * Constructor
     */
    public Chatbot() {
        this.conversationManager = new ConversationManager();
        this.scanner = new Scanner(System.in);
        this.isRunning = false;
    }

    /**
     * Start the chatbot
     *
     * Demonstrates:
     * - Main conversation loop
     * - State machine implementation
     * - Exception handling
     */
    public void start() {
        isRunning = true;

        printBanner();
        showLanguageSelection();

        while (isRunning && !conversationManager.getCurrentState().equals(
                ConversationManager.State.EXITING)) {

            try {
                ConversationManager.State state = conversationManager.getCurrentState();

                switch (state) {
                    case WELCOME:
                        handleWelcomeState();
                        break;

                    case LANGUAGE_SELECT:
                        handleLanguageSelect();
                        break;

                    case AUTH_MENU:
                        handleAuthMenu();
                        break;

                    case REGISTERING:
                        handleRegistration();
                        break;

                    case LOGGING_IN:
                        handleLogin();
                        break;

                    case MAIN_MENU:
                        handleMainMenu();
                        break;

                    case VIEWING_EXHIBITS:
                        handleViewExhibits();
                        break;

                    case SEARCHING:
                        handleSearch();
                        break;

                    case BOOKING:
                        handleBooking();
                        break;

                    case ADMIN_MENU:
                        handleAdminMenu();
                        break;

                    case EXITING:
                        handleExit();
                        break;

                    default:
                        handleMainMenu();
                }

            } catch (Exception e) {
                say(conversationManager.getMessage("error.invalid.input", e.getMessage()));
            }
        }

        stop();
    }

    /**
     * Print application banner
     *
     * Demonstrates:
     * - Output formatting
     */
    private void printBanner() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   MULTILINGUAL CHATBOT-BASED MUSEUM TICKETING SYSTEM");
        System.out.println("=".repeat(60) + "\n");
    }

    /**
     * Show language selection menu
     *
     * Demonstrates:
     * - Localization service integration
     */
    private void showLanguageSelection() {
        say(conversationManager.getLocalization().getLanguageMenu());

        prompt("Enter number (1-3):");
        String input = readInput();

        try {
            int choice = Integer.parseInt(input.trim());
            Language[] languages = Language.values();

            if (choice >= 1 && choice <= languages.length) {
                Language selected = languages[choice - 1];
                conversationManager.setLanguage(selected);
                say(conversationManager.getMessage("app.welcome"));
                conversationManager.setCurrentState(ConversationManager.State.AUTH_MENU);
            } else {
                say(conversationManager.getMessage("error.invalid.selection"));
                showLanguageSelection();
            }
        } catch (NumberFormatException e) {
            say(conversationManager.getMessage("error.invalid.input", "Invalid number"));
            showLanguageSelection();
        }
    }

    /**
     * Handle welcome state
     */
    private void handleWelcomeState() {
        say(conversationManager.getMessage("app.welcome"));
        conversationManager.setCurrentState(ConversationManager.State.AUTH_MENU);
    }

    /**
     * Handle language selection state
     */
    private void handleLanguageSelect() {
        showLanguageSelection();
    }

    /**
     * Handle authentication menu
     */
    private void handleAuthMenu() {
        say(conversationManager.getLocalization().getAuthenticationMenu());

        prompt("Choose an option:");
        String input = readInput();
        CommandProcessor.ProcessedCommand command = CommandProcessor.processInput(input);

        switch (command.type) {
            case REGISTER:
                conversationManager.setCurrentState(ConversationManager.State.REGISTERING);
                break;

            case LOGIN:
                conversationManager.setCurrentState(ConversationManager.State.LOGGING_IN);
                break;

            case EMPTY:
                // Do nothing, stay in auth menu
                break;

            default:
                say(conversationManager.getMessage("error.invalid.selection"));
        }
    }

    /**
     * Handle registration
     */
    private void handleRegistration() {
        say(conversationManager.getMessage("auth.register"));
        // Registration logic would go here
        conversationManager.setCurrentState(ConversationManager.State.AUTH_MENU);
    }

    /**
     * Handle login
     */
    private void handleLogin() {
        say(conversationManager.getMessage("auth.login"));
        // Login logic would go here
        conversationManager.setCurrentState(ConversationManager.State.AUTH_MENU);
    }

    /**
     * Handle main menu
     *
     * Demonstrates:
     * - Command processing
     * - State transitions
     */
    private void handleMainMenu() {
        if (!conversationManager.isLoggedIn()) {
            conversationManager.setCurrentState(ConversationManager.State.AUTH_MENU);
            return;
        }

        say(conversationManager.getLocalization().getMainMenu());

        prompt("Choose an option:");
        String input = readInput();
        CommandProcessor.ProcessedCommand command = CommandProcessor.processInput(input);

        switch (command.type) {
            case VIEW_EXHIBITS:
                conversationManager.setCurrentState(ConversationManager.State.VIEWING_EXHIBITS);
                break;

            case SEARCH_EXHIBITS:
                conversationManager.setCurrentState(ConversationManager.State.SEARCHING);
                break;

            case BOOK_TICKET:
                conversationManager.setCurrentState(ConversationManager.State.BOOKING);
                break;

            case MY_BOOKINGS:
                say("Viewing your bookings...");
                // Booking logic would go here
                break;

            case CANCEL_BOOKING:
                say("Cancelling booking...");
                // Cancellation logic would go here
                break;

            case ADMIN_LOGIN:
                conversationManager.setCurrentState(ConversationManager.State.ADMIN_MENU);
                break;

            case HELP:
                showHelp();
                break;

            case LOGOUT:
                conversationManager.logout();
                conversationManager.setCurrentState(ConversationManager.State.WELCOME);
                say(conversationManager.getMessage("auth.logout.success"));
                break;

            case EMPTY:
                // Do nothing, stay in main menu
                break;

            default:
                say(conversationManager.getMessage("error.invalid.selection"));
        }
    }

    /**
     * Handle view exhibits state
     */
    private void handleViewExhibits() {
        say(conversationManager.getMessage("exhibit.list"));
        say("1. Ancient India - ₹150");
        say("2. Modern Art - ₹200");
        say("3. Natural History - ₹100");

        conversationManager.setCurrentState(ConversationManager.State.MAIN_MENU);
    }

    /**
     * Handle search state
     */
    private void handleSearch() {
        prompt(conversationManager.getMessage("booking.select"));
        String input = readInput();

        if (!InputNormalizer.isEmpty(input)) {
            say("Searching for: " + input);
            say("Found exhibits matching your search...");
        }

        conversationManager.setCurrentState(ConversationManager.State.MAIN_MENU);
    }

    /**
     * Handle booking state
     */
    private void handleBooking() {
        say(conversationManager.getMessage("booking.menu"));
        say("Booking interface would appear here...");
        conversationManager.setCurrentState(ConversationManager.State.MAIN_MENU);
    }

    /**
     * Handle admin menu
     */
    private void handleAdminMenu() {
        say(conversationManager.getMessage("admin.menu"));
        say("Admin interface would appear here...");
        conversationManager.setCurrentState(ConversationManager.State.MAIN_MENU);
    }

    /**
     * Show help
     */
    private void showHelp() {
        say("\n" + conversationManager.getMessage("general.help") + ":");
        say("Available commands:");
        say("  - view: " + CommandProcessor.getCommandDescription(CommandProcessor.CommandType.VIEW_EXHIBITS));
        say("  - search: " + CommandProcessor.getCommandDescription(CommandProcessor.CommandType.SEARCH_EXHIBITS));
        say("  - book: " + CommandProcessor.getCommandDescription(CommandProcessor.CommandType.BOOK_TICKET));
        say("  - logout: " + CommandProcessor.getCommandDescription(CommandProcessor.CommandType.LOGOUT));
    }

    /**
     * Handle exit
     */
    private void handleExit() {
        say(conversationManager.getMessage("app.goodbye"));
        isRunning = false;
    }

    /**
     * Stop the chatbot
     */
    public void stop() {
        if (scanner != null) {
            scanner.close();
        }
        isRunning = false;
    }

    /**
     * Say something (output message)
     *
     * @param message message to display
     */
    private void say(String message) {
        System.out.println("Bot: " + message);
    }

    /**
     * Prompt for input
     *
     * @param message prompt message
     */
    private void prompt(String message) {
        System.out.print("\n" + message + " ");
    }

    /**
     * Read user input
     *
     * @return user input
     */
    private String readInput() {
        System.out.print("You: ");
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }
}