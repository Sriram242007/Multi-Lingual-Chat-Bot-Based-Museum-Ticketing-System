package museum.chatbot;

import java.util.HashMap;
import java.util.Map;

/**
 * InputNormalizer.java
 * Utility for normalizing and standardizing user input
 *
 * Demonstrates:
 * - String manipulation (trim, toLowerCase, replaceAll)
 * - Collections (HashMap for command aliases)
 * - Input preprocessing
 *
 * @author Sriram S
 * @version 1.0
 */
public class InputNormalizer {
    private static final Map<String, String> COMMAND_ALIASES = new HashMap<>();

    static {
        // View/Browse exhibits
        COMMAND_ALIASES.put("view exhibits", "view");
        COMMAND_ALIASES.put("view", "view");
        COMMAND_ALIASES.put("exhibits", "view");
        COMMAND_ALIASES.put("browse", "view");
        COMMAND_ALIASES.put("list", "view");

        // Search exhibits
        COMMAND_ALIASES.put("search", "search");
        COMMAND_ALIASES.put("search exhibits", "search");
        COMMAND_ALIASES.put("find", "search");
        COMMAND_ALIASES.put("look for", "search");

        // Book ticket
        COMMAND_ALIASES.put("book", "book");
        COMMAND_ALIASES.put("book ticket", "book");
        COMMAND_ALIASES.put("book tickets", "book");
        COMMAND_ALIASES.put("reserve", "book");
        COMMAND_ALIASES.put("booking", "book");
        COMMAND_ALIASES.put("ticket", "book");

        // View bookings
        COMMAND_ALIASES.put("my bookings", "mybookings");
        COMMAND_ALIASES.put("bookings", "mybookings");
        COMMAND_ALIASES.put("history", "mybookings");
        COMMAND_ALIASES.put("my tickets", "mybookings");
        COMMAND_ALIASES.put("view bookings", "mybookings");

        // Cancel booking
        COMMAND_ALIASES.put("cancel", "cancel");
        COMMAND_ALIASES.put("cancel booking", "cancel");
        COMMAND_ALIASES.put("cancel ticket", "cancel");
        COMMAND_ALIASES.put("refund", "cancel");

        // Admin login
        COMMAND_ALIASES.put("admin", "admin");
        COMMAND_ALIASES.put("admin login", "admin");
        COMMAND_ALIASES.put("administrator", "admin");

        // Help
        COMMAND_ALIASES.put("help", "help");
        COMMAND_ALIASES.put("?", "help");
        COMMAND_ALIASES.put("commands", "help");
        COMMAND_ALIASES.put("menu", "help");

        // Logout
        COMMAND_ALIASES.put("logout", "logout");
        COMMAND_ALIASES.put("exit", "logout");
        COMMAND_ALIASES.put("quit", "logout");
        COMMAND_ALIASES.put("bye", "logout");
        COMMAND_ALIASES.put("goodbye", "logout");

        // Yes/No
        COMMAND_ALIASES.put("yes", "yes");
        COMMAND_ALIASES.put("y", "yes");
        COMMAND_ALIASES.put("yep", "yes");
        COMMAND_ALIASES.put("sure", "yes");
        COMMAND_ALIASES.put("ok", "yes");

        COMMAND_ALIASES.put("no", "no");
        COMMAND_ALIASES.put("n", "no");
        COMMAND_ALIASES.put("nope", "no");
        COMMAND_ALIASES.put("nah", "no");
    }

    /**
     * Normalize user input
     *
     * Demonstrates:
     * - String.trim() to remove whitespace
     * - String.toLowerCase() for case-insensitive matching
     * - Multiple preprocessing steps
     *
     * @param input raw user input
     * @return normalized input
     */
    public static String normalize(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        // Remove leading/trailing whitespace
        input = input.trim();

        // Convert to lowercase for case-insensitive matching
        input = input.toLowerCase();

        // Remove extra whitespace (multiple spaces to single space)
        input = input.replaceAll("\\s+", " ");

        return input;
    }

    /**
     * Resolve command from user input
     *
     * Demonstrates:
     * - HashMap lookup
     * - Exact matching and partial matching
     * - Fallback to original if no match
     *
     * @param input normalized user input
     * @return canonical command
     */
    public static String resolveCommand(String input) {
        // Check for exact match in aliases
        if (COMMAND_ALIASES.containsKey(input)) {
            return COMMAND_ALIASES.get(input);
        }

        // Check for partial matches
        for (String alias : COMMAND_ALIASES.keySet()) {
            if (input.contains(alias)) {
                return COMMAND_ALIASES.get(alias);
            }
        }

        // No match found - return input as-is
        return input;
    }

    /**
     * Extract parameters from user input
     *
     * Demonstrates:
     * - String.split() for tokenization
     * - Array manipulation
     *
     * Example: "book ancient india" → ["book", "ancient", "india"]
     *
     * @param input user input
     * @return array of tokens
     */
    public static String[] extractTokens(String input) {
        return input.trim().split("\\s+");
    }

    /**
     * Extract parameter after command
     *
     * Demonstrates:
     * - String.split() and index access
     * - Handling variable input formats
     *
     * Example: "search ancient india" → "ancient india"
     *
     * @param input full user input
     * @param commandLength number of command words
     * @return parameter part, or empty string
     */
    public static String extractParameter(String input, int commandLength) {
        String[] tokens = extractTokens(input);

        if (tokens.length <= commandLength) {
            return "";
        }

        // Reconstruct parameter from remaining tokens
        StringBuilder param = new StringBuilder();
        for (int i = commandLength; i < tokens.length; i++) {
            if (i > commandLength) {
                param.append(" ");
            }
            param.append(tokens[i]);
        }

        return param.toString();
    }

    /**
     * Check if input is a yes response
     *
     * @param input user input
     * @return true if yes
     */
    public static boolean isYes(String input) {
        String normalized = normalize(input);
        return COMMAND_ALIASES.containsKey(normalized) &&
                COMMAND_ALIASES.get(normalized).equals("yes");
    }

    /**
     * Check if input is a no response
     *
     * @param input user input
     * @return true if no
     */
    public static boolean isNo(String input) {
        String normalized = normalize(input);
        return COMMAND_ALIASES.containsKey(normalized) &&
                COMMAND_ALIASES.get(normalized).equals("no");
    }

    /**
     * Check if input is empty or just whitespace
     *
     * Demonstrates:
     * - String.isEmpty() and trim()
     *
     * @param input user input
     * @return true if empty
     */
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
}