package museum.chatbot;

/**
 * CommandProcessor.java
 * Processes user commands and routes them to handlers
 *
 * Demonstrates:
 * - Command parsing and routing
 * - Switch statements for command handling
 * - Pattern matching (if applicable with Java 21 features)
 * - Method signatures and return types
 *
 * Unit 1: Control flow (switch), methods, String operations
 *
 * @author Your Name
 * @version 1.0
 */
public class CommandProcessor {

    /**
     * Command type enum
     *
     * Demonstrates:
     * - Enum for command categorization
     */
    public enum CommandType {
        VIEW_EXHIBITS,
        SEARCH_EXHIBITS,
        BOOK_TICKET,
        MY_BOOKINGS,
        CANCEL_BOOKING,
        ADMIN_LOGIN,
        HELP,
        LOGOUT,
        LANGUAGE_CHANGE,
        REGISTER,
        LOGIN,
        YES_RESPONSE,
        NO_RESPONSE,
        INVALID,
        EMPTY
    }

    /**
     * Processed command with type and parameters
     */
    public static class ProcessedCommand {
        public CommandType type;
        public String parameter;

        public ProcessedCommand(CommandType type, String parameter) {
            this.type = type;
            this.parameter = parameter;
        }

        public ProcessedCommand(CommandType type) {
            this(type, "");
        }
    }

    /**
     * Process user input into a command
     *
     * Demonstrates:
     * - String normalization and processing
     * - Command routing
     * - Default case handling
     *
     * @param input raw user input
     * @return processed command with type and parameters
     */
    public static ProcessedCommand processInput(String input) {
        // Check for empty input
        if (InputNormalizer.isEmpty(input)) {
            return new ProcessedCommand(CommandType.EMPTY);
        }

        // Normalize input
        String normalized = InputNormalizer.normalize(input);

        // Check for yes/no responses
        if (InputNormalizer.isYes(normalized)) {
            return new ProcessedCommand(CommandType.YES_RESPONSE);
        }

        if (InputNormalizer.isNo(normalized)) {
            return new ProcessedCommand(CommandType.NO_RESPONSE);
        }

        // Resolve command from aliases
        String command = InputNormalizer.resolveCommand(normalized);

        // Extract parameter (text after command)
        String parameter = InputNormalizer.extractParameter(normalized, 1);

        // Route to appropriate command type
        // Demonstrates: switch statement for command routing
        switch (command) {
            case "view":
                return new ProcessedCommand(CommandType.VIEW_EXHIBITS, parameter);

            case "search":
                return new ProcessedCommand(CommandType.SEARCH_EXHIBITS, parameter);

            case "book":
                return new ProcessedCommand(CommandType.BOOK_TICKET, parameter);

            case "mybookings":
                return new ProcessedCommand(CommandType.MY_BOOKINGS, parameter);

            case "cancel":
                return new ProcessedCommand(CommandType.CANCEL_BOOKING, parameter);

            case "admin":
                return new ProcessedCommand(CommandType.ADMIN_LOGIN, parameter);

            case "help":
                return new ProcessedCommand(CommandType.HELP, parameter);

            case "logout":
                return new ProcessedCommand(CommandType.LOGOUT, parameter);

            case "register":
                return new ProcessedCommand(CommandType.REGISTER, parameter);

            case "login":
                return new ProcessedCommand(CommandType.LOGIN, parameter);

            default:
                return new ProcessedCommand(CommandType.INVALID, command);
        }
    }

    /**
     * Check if command requires logged-in user
     *
     * Demonstrates:
     * - Method for authorization logic
     *
     * @param commandType the command type
     * @return true if login required
     */
    public static boolean requiresLogin(CommandType commandType) {
        switch (commandType) {
            case VIEW_EXHIBITS:
            case SEARCH_EXHIBITS:
            case BOOK_TICKET:
            case MY_BOOKINGS:
            case CANCEL_BOOKING:
                return true;

            case ADMIN_LOGIN:
            case HELP:
            case LOGOUT:
            case LANGUAGE_CHANGE:
            case REGISTER:
            case LOGIN:
            case YES_RESPONSE:
            case NO_RESPONSE:
            case INVALID:
            case EMPTY:
            default:
                return false;
        }
    }

    /**
     * Check if command requires admin privileges
     *
     * @param commandType the command type
     * @return true if admin required
     */
    public static boolean requiresAdmin(CommandType commandType) {
        switch (commandType) {
            case ADMIN_LOGIN:
                return true;

            default:
                return false;
        }
    }

    /**
     * Get command description
     *
     * @param commandType the command type
     * @return human-readable description
     */
    public static String getCommandDescription(CommandType commandType) {
        switch (commandType) {
            case VIEW_EXHIBITS:
                return "View all available exhibits";
            case SEARCH_EXHIBITS:
                return "Search for exhibits by name or category";
            case BOOK_TICKET:
                return "Book tickets for an exhibit";
            case MY_BOOKINGS:
                return "View your booking history";
            case CANCEL_BOOKING:
                return "Cancel an existing booking";
            case ADMIN_LOGIN:
                return "Login as administrator";
            case HELP:
                return "Show help and available commands";
            case LOGOUT:
                return "Logout from the system";
            case REGISTER:
                return "Create a new visitor account";
            case LOGIN:
                return "Login to your account";
            case LANGUAGE_CHANGE:
                return "Change conversation language";
            case YES_RESPONSE:
                return "Affirmative response";
            case NO_RESPONSE:
                return "Negative response";
            case INVALID:
                return "Invalid command";
            case EMPTY:
                return "Empty input";
            default:
                return "Unknown command";
        }
    }
}