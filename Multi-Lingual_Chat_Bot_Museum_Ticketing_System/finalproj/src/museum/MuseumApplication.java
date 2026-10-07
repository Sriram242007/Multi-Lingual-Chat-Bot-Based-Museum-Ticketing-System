package museum;

import museum.chatbot.ChatbotIntegration;
import museum.model.*;
import museum.localization.LocalizationService;
import museum.util.ReceiptGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * MuseumApplication.java
 * Main interactive chatbot application UI for the Multi-Lingual Museum Ticketing System.
 *
 * Provides:
 * - 3 Language selection (English, Tamil, Hindi)
 * - Visitor Registration and Login
 * - Admin Login
 * - Exhibit Browsing and Search
 * - Time Slot Availability Check
 * - Ticket Booking Workflow
 * - Simulated Payment Options (UPI, Card, Cash) & Receipt Generation
 * - Conversational Chatbot User Experience
 *
 * @author Sriram S
 * @version 2.0
 */
public class MuseumApplication {

    private static final Scanner scanner = new Scanner(System.in);
    private static ChatbotIntegration integration;
    private static LocalizationService localization;
    private static Language currentLanguage = Language.ENGLISH;

    public static void main(String[] args) {
        // Initialize integration & sample exhibits
        integration = new ChatbotIntegration();
        integration.initializeSampleExhibits();
        localization = new LocalizationService(currentLanguage);

        printBanner();
        selectLanguage();

        boolean running = true;
        while (running) {
            if (!integration.getConversationManager().isLoggedIn()) {
                running = showGuestMenu();
            } else {
                User currentUser = integration.getConversationManager().getCurrentUser();
                if (currentUser instanceof Admin) {
                    running = showAdminMenu((Admin) currentUser);
                } else {
                    running = showVisitorMenu((Visitor) currentUser);
                }
            }
        }

        botSay(getLocalizedText(
                "Thank you for using the Museum Ticketing System! Have a wonderful visit!",
                "அருங்காட்சியக டிக்கெட் முறையைப் பயன்படுத்தியதற்கு நன்றி! நல்வரவு!",
                "संग्रहालय टिकट प्रणाली का उपयोग करने के लिए धन्यवाद! आपकी यात्रा मंगलमय हो!"
        ));
        scanner.close();
    }

    // ==================== BANNER & BOT OUTPUT HELPERS ====================

    private static void printBanner() {
        System.out.println("\n" + "=".repeat(65));
        System.out.println(" 🤖 MULTI-LINGUAL CHATBOT MUSEUM TICKETING SYSTEM");
        System.out.println("    [ English | தமிழ் (Tamil) | हिंदी (Hindi) ]");
        System.out.println("=".repeat(65) + "\n");
    }

    private static void botSay(String message) {
        System.out.println("\n🤖 Bot: " + message);
    }

    private static String promptUser(String promptText) {
        System.out.print("👤 You > " + promptText + ": ");
        return scanner.nextLine().trim();
    }

    private static String getLocalizedText(String en, String ta, String hi) {
        if (currentLanguage == Language.TAMIL) return ta;
        if (currentLanguage == Language.HINDI) return hi;
        return en;
    }

    // ==================== STEP 1: LANGUAGE SELECTION ====================

    private static void selectLanguage() {
        botSay("Welcome! Please select your preferred language / உங்கள் மொழியைத் தேர்ந்தெடுக்கவும் / अपनी भाषा चुनें:");
        System.out.println("  1. English");
        System.out.println("  2. தமிழ் (Tamil)");
        System.out.println("  3. हिंदी (Hindi)");

        while (true) {
            String choice = promptUser("Select option (1-3)");
            switch (choice) {
                case "1" -> {
                    currentLanguage = Language.ENGLISH;
                    localization.setLanguage(Language.ENGLISH);
                    integration.getConversationManager().setLanguage(Language.ENGLISH);
                    botSay("Language set to English! How can I assist you today?");
                    return;
                }
                case "2" -> {
                    currentLanguage = Language.TAMIL;
                    localization.setLanguage(Language.TAMIL);
                    integration.getConversationManager().setLanguage(Language.TAMIL);
                    botSay("மொழி தமிழாக அமைக்கப்பட்டது! இன்று நான் உங்களுக்கு எவ்வாறு உதவ முடியும்?");
                    return;
                }
                case "3" -> {
                    currentLanguage = Language.HINDI;
                    localization.setLanguage(Language.HINDI);
                    integration.getConversationManager().setLanguage(Language.HINDI);
                    botSay("भाषा हिंदी पर सेट की गई है! आज मैं आपकी क्या सहायता कर सकता हूँ?");
                    return;
                }
                default -> botSay("Invalid selection. Please choose 1, 2, or 3.");
            }
        }
    }

    // ==================== GUEST MENU (UNAUTHENTICATED) ====================

    private static boolean showGuestMenu() {
        botSay(getLocalizedText(
                "Main Menu - Please choose an option:",
                "முதன்மை மெனு - ஒரு விருப்பத்தைத் தேர்ந்தெடுக்கவும்:",
                "मुख्य मेनू - कृपया एक विकल्प चुनें:"
        ));
        System.out.println("  1. " + getLocalizedText("Register as Visitor", "பார்வையாளராகப் பதிவுசெய்க", "आगंतुक के रूप में पंजीकरण करें"));
        System.out.println("  2. " + getLocalizedText("Login as Visitor", "பார்வையாளராக உள்நுழைக", "आगंतुक के रूप में लॉगिन करें"));
        System.out.println("  3. " + getLocalizedText("Login as Admin", "நிர்வாகியாக உள்நுழைக", "एडमिन के रूप में लॉगिन करें"));
        System.out.println("  4. " + getLocalizedText("Browse Exhibits", "காட்சிக் கூடங்களைப் பார்க்க", "प्रदर्शनी देखें"));
        System.out.println("  5. " + getLocalizedText("Change Language", "மொழியை மாற்றுக", "भाषा बदलें"));
        System.out.println("  6. " + getLocalizedText("Exit Chatbot", "வெளியேறு", "बाहर निकलें"));

        String choice = promptUser(getLocalizedText("Choice", "விருப்பம்", "विकल्प"));

        switch (choice) {
            case "1" -> handleRegisterVisitor();
            case "2" -> handleVisitorLogin();
            case "3" -> handleAdminLogin();
            case "4" -> handleBrowseExhibits();
            case "5" -> selectLanguage();
            case "6" -> { return false; }
            default -> botSay(getLocalizedText("Invalid choice. Try again.", "தவறான விருப்பம். மீண்டும் முயற்சிக்கவும்.", "अमान्य विकल्प। पुनः प्रयास करें।"));
        }
        return true;
    }

    // ==================== VISITOR DASHBOARD MENU ====================

    private static boolean showVisitorMenu(Visitor visitor) {
        botSay(getLocalizedText(
                "Welcome back, " + visitor.getName() + "! What would you like to do?",
                "மீண்டும் வருக, " + visitor.getName() + "! நீங்கள் என்ன செய்ய விரும்புகிறீர்கள்?",
                "वापसी पर स्वागत है, " + visitor.getName() + "! आप क्या करना चाहेंगे?"
        ));
        System.out.println("  1. " + getLocalizedText("Browse & Search Exhibits", "காட்சிக் கூடங்களைத் தேடுக", "प्रदर्शनी खोजें"));
        System.out.println("  2. " + getLocalizedText("Check Time Slot Availability", "நேர அட்டவணையைச் சரிபார்க்க", "समय सारणी उपलब्धता जांचें"));
        System.out.println("  3. " + getLocalizedText("Book Ticket", "டிக்கெட் முன்பதிவு செய்க", "टिकट बुक करें"));
        System.out.println("  4. " + getLocalizedText("View My Bookings & Receipts", "என் முன்பதிவுகள் மற்றும் ரசீதுகள்", "मेरी बुकिंग और रसीदें"));
        System.out.println("  5. " + getLocalizedText("Cancel a Booking", "முன்பதிவை ரத்து செய்க", "बुकिंग रद्द करें"));
        System.out.println("  6. " + getLocalizedText("Change Language", "மொழியை மாற்றுக", "भाषा बदलें"));
        System.out.println("  7. " + getLocalizedText("Logout", "வெளியேறு", "लॉगआउट"));

        String choice = promptUser(getLocalizedText("Choice", "விருப்பம்", "विकल्प"));

        switch (choice) {
            case "1" -> handleBrowseExhibits();
            case "2" -> handleCheckAvailability();
            case "3" -> handleBookTicketWorkflow(visitor.getEmail());
            case "4" -> handleViewMyBookings(visitor.getEmail());
            case "5" -> handleCancelBooking();
            case "6" -> selectLanguage();
            case "7" -> {
                integration.logout();
                botSay(getLocalizedText("Logged out successfully.", "வெற்றிகரமாக வெளியேறினீர்கள்.", "सफलतापूर्वक लॉगआउट किया गया।"));
            }
            default -> botSay(getLocalizedText("Invalid choice. Try again.", "தவறான விருப்பம். மீண்டும் முயற்சிக்கவும்.", "अमान्य विकल्प। पुनः प्रयास करें।"));
        }
        return true;
    }

    // ==================== ADMIN DASHBOARD MENU ====================

    private static boolean showAdminMenu(Admin admin) {
        botSay(getLocalizedText(
                "Admin Dashboard - Logged in as: " + admin.getName() + " (" + admin.getDepartment() + ")",
                "நிர்வாகி கட்டுப்பாட்டகம் - உள்நுழைந்தவர்: " + admin.getName(),
                "एडमिन डैशबोर्ड - लॉगिन किया गया: " + admin.getName()
        ));
        System.out.println("  1. " + getLocalizedText("View All Exhibits", "அனைத்து காட்சிக் கூடங்களையும் பார்க்க", "सभी प्रदर्शनी देखें"));
        System.out.println("  2. " + getLocalizedText("View Museum Analytics & Revenue Report", "அறிக்கைகள் மற்றும் வருவாய் பார்க்க", "संग्रहालय रिपोर्ट और राजस्व देखें"));
        System.out.println("  3. " + getLocalizedText("Change Language", "மொழியை மாற்றுக", "भाषा बदलें"));
        System.out.println("  4. " + getLocalizedText("Logout", "வெளியேறு", "लॉगआउट"));

        String choice = promptUser(getLocalizedText("Choice", "விருப்பம்", "विकल्प"));

        switch (choice) {
            case "1" -> handleBrowseExhibits();
            case "2" -> handleViewAnalytics();
            case "3" -> selectLanguage();
            case "4" -> {
                integration.logout();
                botSay(getLocalizedText("Admin logged out successfully.", "நிர்வாகி வெற்றிகரமாக வெளியேறினார்.", "एडमिन सफलतापूर्वक लॉगआउट हो गया।"));
            }
            default -> botSay(getLocalizedText("Invalid choice. Try again.", "தவறான விருப்பம். மீண்டும் முயற்சிக்கவும்.", "अमान्य विकल्प। पुनः प्रयास करें।"));
        }
        return true;
    }

    // ==================== WORKFLOW HANDLERS ====================

    private static void handleRegisterVisitor() {
        botSay(getLocalizedText(
                "Great! Let's get you registered as a visitor.",
                "அற்புதம்! பார்வையாளராகப் பதிவுசெய்வோம்.",
                "बहुत बढ़िया! आइए आपको एक आगंतुक के रूप में पंजीकृत करें।"
        ));

        String email = promptUser(getLocalizedText("Enter Email (e.g. user@museum.com)", "மின்னஞ்சலை உள்ளிடவும்", "ईमेल दर्ज करें"));
        String name = promptUser(getLocalizedText("Enter Full Name", "முழுப் பெயரை உள்ளிடவும்", "पूरा नाम दर्ज करें"));
        String password = promptUser(getLocalizedText("Enter Password (min 6 chars)", "கடவுச்சொல்லை உள்ளிடவும்", "पासवर्ड दर्ज करें"));
        String phone = promptUser(getLocalizedText("Enter Phone Number (10 digits)", "தொலைபேசி எண்ணை உள்ளிடவும்", "फ़ोन नंबर दर्ज करें"));
        String address = promptUser(getLocalizedText("Enter Address", "முகவரியை உள்ளிடவும்", "पता दर्ज करें"));

        boolean success = integration.registerVisitor(email, name, password, phone, address);
        if (success) {
            botSay(getLocalizedText(
                    "Registration successful! You can now log in.",
                    "பதிவு வெற்றிகரமாக முடிந்தது! இப்போது உள்நுழையலாம்.",
                    "पंजीकरण सफल रहा! अब आप लॉगिन कर सकते हैं।"
            ));
        } else {
            botSay(getLocalizedText(
                    "Registration failed. Email may already be registered or details invalid.",
                    "பதிவு தோல்வியடைந்தது. மின்னஞ்சல் ஏற்கனவே பதிவு செய்யப்பட்டிருக்கலாம்.",
                    "पंजीकरण विफल रहा। ईमेल पहले से पंजीकृत हो सकता है।"
            ));
        }
    }

    private static void handleVisitorLogin() {
        botSay(getLocalizedText(
                "Visitor Login - Please enter your credentials:",
                "பார்வையாளர் உள்நுழைவு - உங்கள் விவரங்களை உள்ளிடவும்:",
                "आगंतुक लॉगिन - कृपया अपने क्रेडेंशियल दर्ज करें:"
        ));

        String email = promptUser(getLocalizedText("Email", "மின்னஞ்சல்", "ईमेल"));
        String password = promptUser(getLocalizedText("Password", "கடவுச்சொல்", "पासवर्ड"));

        Visitor visitor = integration.loginVisitor(email, password);
        if (visitor != null) {
            botSay(getLocalizedText(
                    "Login successful! Welcome, " + visitor.getName() + ".",
                    "உள்நுழைவு வெற்றி! வரவேற்கிறோம், " + visitor.getName() + ".",
                    "लॉगिन सफल रहा! आपका स्वागत है, " + visitor.getName() + "।"
            ));
        } else {
            botSay(getLocalizedText(
                    "Login failed. Invalid email or password.",
                    "உள்நுழைவு தோல்வியடைந்தது. தவறான மின்னஞ்சல் அல்லது கடவுச்சொல்.",
                    "लॉगिन विफल रहा। अमान्य ईमेल या पासवर्ड।"
            ));
        }
    }

    private static void handleAdminLogin() {
        botSay(getLocalizedText(
                "Admin Login - Authorized Personnel Only:",
                "நிர்வாகி உள்நுழைவு:",
                "एडमिन लॉगिन:"
        ));

        String email = promptUser(getLocalizedText("Admin Email", "நிர்வாகி மின்னஞ்சல்", "एडमिन ईमेल"));
        String password = promptUser(getLocalizedText("Password", "கடவுச்சொல்", "पासवर्ड"));

        Admin admin = integration.loginAdmin(email, password);
        if (admin != null) {
            botSay(getLocalizedText(
                    "Admin authentication successful! Access granted.",
                    "நிர்வாகி உள்நுழைவு வெற்றி!",
                    "एडमिन प्रमाणीकरण सफल रहा!"
            ));
        } else {
            botSay(getLocalizedText(
                    "Admin login failed. Invalid credentials.",
                    "நிர்வாகி உள்நுழைவு தோல்வியடைந்தது.",
                    "एडमिन लॉगिन विफल रहा।"
            ));
        }
    }

    private static void handleBrowseExhibits() {
        List<Exhibit> exhibits = integration.getExhibits();
        botSay(getLocalizedText(
                "Here are the current museum exhibits:",
                "தற்போதைய அருங்காட்சியகக் கூடங்கள்:",
                "वर्तमान संग्रहालय प्रदर्शनी निम्नलिखित हैं:"
        ));

        if (exhibits.isEmpty()) {
            botSay(getLocalizedText("No exhibits found.", "காட்சிக் கூடங்கள் எதுவும் இல்லை.", "कोई प्रदर्शनी नहीं मिली।"));
            return;
        }

        for (int i = 0; i < exhibits.size(); i++) {
            Exhibit e = exhibits.get(i);
            System.out.println(String.format("  [%d] %s (ID: %s) - Fee: ₹%.2f | Location: %s",
                    (i + 1), e.getName(), e.getExhibitId(), e.getEntryFee(), e.getLocation()));
            System.out.println("      " + e.getDescription() + " [Category: " + e.getCategory() + "]");
        }

        String search = promptUser(getLocalizedText(
                "Press ENTER to continue, or type a keyword to search",
                "தேட வார்த்தையை உள்ளிடவும் (அல்லது ENTER அழுத்தவும்)",
                "खोजने के लिए शब्द दर्ज करें (या ENTER दबाएं)"
        ));

        if (!search.isEmpty()) {
            List<Exhibit> results = integration.searchExhibits(search);
            botSay(getLocalizedText(
                    "Search results for '" + search + "':",
                    "தேடல் முடிவுகள் '" + search + "':",
                    "खोज परिणाम '" + search + "':"
            ));
            for (Exhibit e : results) {
                System.out.println("  - " + e.getName() + " (₹" + e.getEntryFee() + ") - " + e.getLocation());
            }
        }
    }

    private static void handleCheckAvailability() {
        botSay(getLocalizedText(
                "Check Time Slot Availability:",
                "நேர அட்டவணை இருப்பைச் சரிபார்க்கவும்:",
                "समय सारणी उपलब्धता जांचें:"
        ));

        String exhibitId = promptUser(getLocalizedText("Enter Exhibit ID (e.g. EXH1001)", "காட்சிக் கூட ஐடியை உள்ளிடவும்", "प्रदर्शनी आईडी दर्ज करें"));
        String dateStr = promptUser(getLocalizedText("Enter Visit Date (YYYY-MM-DD, e.g. " + LocalDate.now() + ")", "தேதியை உள்ளிடவும்", "तिथि दर्ज करें"));

        LocalDate date;
        try {
            date = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            date = LocalDate.now();
        }

        List<TimeSlot> slots = integration.getAvailableSlots(exhibitId, date);
        botSay(getLocalizedText(
                "Available slots for " + exhibitId + " on " + date + ":",
                "கிடைக்கக்கூடிய நேர இடங்கள்:",
                "उपलब्ध समय स्लॉट:"
        ));

        if (slots.isEmpty()) {
            botSay(getLocalizedText("No available time slots.", "இடங்கள் எதுவும் இல்லை.", "कोई समय स्लॉट उपलब्ध नहीं है।"));
        } else {
            for (int i = 0; i < slots.size(); i++) {
                TimeSlot ts = slots.get(i);
                System.out.println("  [" + i + "] " + ts.getFormattedTimeRange() + " - " + ts.getDetailedAvailabilityInfo());
            }
        }
    }

    private static void handleBookTicketWorkflow(String visitorEmail) {
        botSay(getLocalizedText(
                "TICKET BOOKING WORKFLOW",
                "டிக்கெட் முன்பதிவு",
                "टिकट बुकिंग कार्यप्रवाह"
        ));

        // List exhibits to pick from
        List<Exhibit> exhibits = integration.getExhibits();
        if (exhibits.isEmpty()) {
            botSay(getLocalizedText("No exhibits available for booking.", "முன்பதிவு செய்யக்கூடங்கள் இல்லை.", "बुकिंग के लिए कोई प्रदर्शनी उपलब्ध नहीं है।"));
            return;
        }

        System.out.println("\nAvailable Exhibits:");
        for (int i = 0; i < exhibits.size(); i++) {
            Exhibit e = exhibits.get(i);
            System.out.println("  [" + (i + 1) + "] " + e.getName() + " (ID: " + e.getExhibitId() + ") - Fee: ₹" + e.getEntryFee());
        }

        String choiceStr = promptUser(getLocalizedText("Select Exhibit Number (1-" + exhibits.size() + ")", "காட்சிக் கூட எண்ணைத் தேர்ந்தெடுக்கவும்", "प्रदर्शनी संख्या चुनें"));
        int exhIdx = 0;
        try {
            exhIdx = Integer.parseInt(choiceStr) - 1;
        } catch (Exception ignored) {}

        if (exhIdx < 0 || exhIdx >= exhibits.size()) {
            botSay(getLocalizedText("Invalid exhibit selection.", "தவறான தேர்வு.", "अमान्य चयन।"));
            return;
        }

        Exhibit chosenExhibit = exhibits.get(exhIdx);

        String dateStr = promptUser(getLocalizedText("Enter Visit Date (YYYY-MM-DD, default " + LocalDate.now() + ")", "தேதியை உள்ளிடவும்", "तिथि दर्ज करें"));
        LocalDate date;
        try {
            date = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
        } catch (Exception e) {
            date = LocalDate.now();
        }

        List<TimeSlot> slots = integration.getAvailableSlots(chosenExhibit.getExhibitId(), date);
        if (slots.isEmpty()) {
            botSay(getLocalizedText("No available time slots on " + date, "தேர்ந்தெடுக்கப்பட்ட தேதியில் இடங்கள் இல்லை", "चुनी गई तिथि पर कोई समय स्लॉट नहीं है"));
            return;
        }

        System.out.println("\nAvailable Time Slots:");
        for (int i = 0; i < slots.size(); i++) {
            System.out.println("  [" + i + "] " + slots.get(i).getFormattedTimeRange());
        }

        String slotStr = promptUser(getLocalizedText("Select Slot Index (0-" + (slots.size() - 1) + ")", "நேர இடத்தை தேர்வு செய்யவும்", "समय स्लॉट चुनें"));
        int slotIndex = 0;
        try {
            slotIndex = Integer.parseInt(slotStr);
        } catch (Exception ignored) {}

        String quantityStr = promptUser(getLocalizedText("Enter Ticket Quantity (1-10)", "டிக்கெட்டுகளின் எண்ணிக்கையை உள்ளிடவும்", "टिकटों की संख्या दर्ज करें"));
        int quantity = 1;
        try {
            quantity = Math.max(1, Integer.parseInt(quantityStr));
        } catch (Exception ignored) {}

        // Create booking
        Booking booking = integration.bookTickets(visitorEmail, chosenExhibit.getExhibitId(), date, slotIndex, quantity);

        if (booking == null) {
            botSay(getLocalizedText("Booking creation failed.", "முன்பதிவு உருவாக்கப்படவிலை.", "बुकिंग निर्माण विफल रहा।"));
            return;
        }

        botSay(getLocalizedText(
                "Booking created! Details:\n" + integration.formatBooking(booking),
                "முன்பதிவு உருவாக்கப்பட்டது! விவரங்கள்:\n" + integration.formatBooking(booking),
                "बुकिंग बनाई गई! विवरण:\n" + integration.formatBooking(booking)
        ));

        // Proceed to Payment
        handlePaymentWorkflow(booking);
    }

    private static void handlePaymentWorkflow(Booking booking) {
        botSay(getLocalizedText(
                "PAYMENT OPTIONS - Total Due: ₹" + String.format("%.2f", booking.getTotalAmount()),
                "கட்டண விருப்பங்கள் - செலுத்த வேண்டிய தொகை: ₹" + String.format("%.2f", booking.getTotalAmount()),
                "भुगतान विकल्प - कुल देय राशि: ₹" + String.format("%.2f", booking.getTotalAmount())
        ));

        System.out.println("  1. " + getLocalizedText("UPI (GPay / PhonePe / Paytm / BHIM)", "UPI மூலம் செலுத்துக", "UPI (जीपे / फोनपे / पेटीएम)"));
        System.out.println("  2. " + getLocalizedText("Credit / Debit Card", "கிரெடிட் / டெபிட் கார்டு", "क्रेडिट / डेबिट कार्ड"));
        System.out.println("  3. " + getLocalizedText("Cash at Museum Counter", "ரொக்கம் (கவுண்டரில்)", "नकद (काउंटर पर)"));

        String payChoice = promptUser(getLocalizedText("Select Payment Method (1-3)", "கட்டண முறையைத் தேர்ந்தெடுக்கவும்", "भुगतान विधि चुनें"));
        String paymentMethod = switch (payChoice) {
            case "2" -> "CARD";
            case "3" -> "CASH";
            default -> "UPI";
        };

        botSay(getLocalizedText(
                "Processing " + paymentMethod + " payment...",
                "கட்டணம் செயல்படுத்தப்படுகிறது...",
                "भुगतान संसाधित किया जा रहा है..."
        ));

        Receipt receipt = integration.confirmBooking(booking.getBookingId(), paymentMethod);

        if (receipt != null) {
            botSay(getLocalizedText(
                    "Payment Successful! Here is your official Museum Ticket Receipt:",
                    "கட்டணம் வெற்றி! இதோ உங்கள் அதிகாரப்பூர்வ அருங்காட்சியக டிக்கெட் ரசீது:",
                    "भुगतान सफल रहा! यह आपकी आधिकारिक संग्रहालय टिकट रसीद है:"
            ));
            System.out.println("\n" + ReceiptGenerator.generateSummary(booking));
        } else {
            botSay(getLocalizedText(
                    "Payment processing incomplete or failed. Please check booking status.",
                    "கட்டணச் செயல்முறை தோல்வியடைந்தது.",
                    "भुगतान प्रसंस्करण अधूरा रहा या विफल हो गया।"
            ));
        }
    }

    private static void handleViewMyBookings(String visitorEmail) {
        List<Booking> bookings = integration.getVisitorBookings(visitorEmail);
        botSay(getLocalizedText(
                "Your Bookings & Ticket Receipts:",
                "உங்கள் முன்பதிவுகள் மற்றும் டிக்கெட் ரசீதுகள்:",
                "आपकी बुकिंग और टिकट रसीदें:"
        ));

        if (bookings.isEmpty()) {
            botSay(getLocalizedText("No bookings found.", "முன்பதிவுகள் எதுவும் இல்லை.", "कोई बुकिंग नहीं मिली।"));
        } else {
            for (Booking b : bookings) {
                System.out.println(integration.formatBooking(b));
            }
        }
    }

    private static void handleCancelBooking() {
        String bookingId = promptUser(getLocalizedText("Enter Booking ID to Cancel", "ரத்து செய்ய வேண்டிய முன்பதிவு ஐடி", "रद्द करने के लिए बुकिंग आईडी दर्ज करें"));
        boolean cancelled = integration.cancelBooking(bookingId);
        if (cancelled) {
            botSay(getLocalizedText("Booking " + bookingId + " cancelled successfully.", "முன்பதிவு ரத்து செய்யப்பட்டது.", "बुकिंग सफलतापूर्वक रद्द कर दी गई।"));
        } else {
            botSay(getLocalizedText("Could not cancel booking. Check ID.", "முன்பதிவை ரத்து செய்ய முடியவில்லை.", "बुकिंग रद्द नहीं की जा सकी।"));
        }
    }

    private static void handleViewAnalytics() {
        botSay(getLocalizedText("Generating Museum System Analytics Report...", "அறிக்கை உருவாக்கப்படுகிறது...", "संग्रहालय रिपोर्ट तैयार की जा रही है..."));
        String report = integration.getAnalyticsReport();
        System.out.println("\n" + report);
    }
}