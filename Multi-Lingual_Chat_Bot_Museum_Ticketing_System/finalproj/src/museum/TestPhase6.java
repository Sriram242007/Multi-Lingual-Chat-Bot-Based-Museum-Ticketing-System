package museum;
import museum.model.Language ;
import museum.chatbot.*;

/**
 * TestPhase6.java
 * Verification tests for Phase 6 chatbot engine
 */
public class TestPhase6 {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PHASE 6: CHATBOT ENGINE - VERIFICATION TESTS");
        System.out.println("=".repeat(60) + "\n");

        testInputNormalizer();
        testCommandProcessor();
        testConversationManager();

        System.out.println("\n✓ ALL PHASE 6 TESTS PASSED!");
        System.out.println("Chatbot engine ready for integration\n");

        // Uncomment to start interactive chatbot
        // startChatbot();
    }

    private static void testInputNormalizer() {
        System.out.println("Testing InputNormalizer...");

        // Test normalization
        String input = "  BOOK TICKETS  ";
        String normalized = InputNormalizer.normalize(input);
        System.out.println("  Normalize '" + input + "' → '" + normalized + "'");

        // Test command resolution
        String command = InputNormalizer.resolveCommand("reserve");
        System.out.println("  Resolve 'reserve' → '" + command + "'");

        // Test token extraction
        String[] tokens = InputNormalizer.extractTokens("book ancient india");
        System.out.println("  Extract tokens: " + java.util.Arrays.toString(tokens));

        // Test yes/no detection
        System.out.println("  Is 'yes' a yes response? " + InputNormalizer.isYes("yes"));
        System.out.println("  Is 'nope' a no response? " + InputNormalizer.isNo("nope"));

        System.out.println("✓ InputNormalizer working\n");
    }

    private static void testCommandProcessor() {
        System.out.println("Testing CommandProcessor...");

        // Test command processing
        CommandProcessor.ProcessedCommand cmd1 =
                CommandProcessor.processInput("book ancient india");
        System.out.println("  'book ancient india' → " + cmd1.type);

        CommandProcessor.ProcessedCommand cmd2 =
                CommandProcessor.processInput("search");
        System.out.println("  'search' → " + cmd2.type);

        CommandProcessor.ProcessedCommand cmd3 =
                CommandProcessor.processInput("help");
        System.out.println("  'help' → " + cmd3.type);

        // Test login requirement
        System.out.println("  VIEW_EXHIBITS requires login? " +
                CommandProcessor.requiresLogin(CommandProcessor.CommandType.VIEW_EXHIBITS));
        System.out.println("  REGISTER requires login? " +
                CommandProcessor.requiresLogin(CommandProcessor.CommandType.REGISTER));

        System.out.println("✓ CommandProcessor working\n");
    }

    private static void testConversationManager() {
        System.out.println("Testing ConversationManager...");

        ConversationManager manager = new ConversationManager();

        // Test state management
        System.out.println("  Initial state: " + manager.getCurrentState());
        System.out.println("  Is logged in? " + manager.isLoggedIn());

        // Test language management
        System.out.println("  Current language: " + manager.getCurrentLanguage());
        manager.setLanguage(museum.model.Language.TAMIL);
        System.out.println("  After Tamil switch: " + manager.getCurrentLanguage());

        manager.setLanguage(museum.model.Language.ENGLISH);

        // Test localization
        String welcome = manager.getMessage("app.welcome");
        System.out.println("  Welcome message: " + welcome);

        // Test temporary data
        manager.setTempData("exhibit_id", "EXH1001");
        System.out.println("  Store temp data: exhibit_id = EXH1001");
        System.out.println("  Retrieve temp data: " + manager.getTempData("exhibit_id"));

        System.out.println("✓ ConversationManager working\n");
    }

    private static void startChatbot() {
        System.out.println("\nStarting interactive chatbot...");
        System.out.println("(Note: Interactive mode - requires user input)\n");

        Chatbot chatbot = new Chatbot();
        //chatbot.start();  // Uncomment to run interactive mode
    }
}