package museum.localization;

import museum.model.Language;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Provides all user-facing messages for the supported languages.
 *
 * The project previously contained two different message-key conventions
 * (for example "welcome" and "app.welcome").  This implementation supports
 * both so that MuseumApplication and the chatbot classes use the same source
 * of truth.
 */
public final class MessageProvider {

    private static final Map<Language, Map<String, String>> MESSAGES =
            new EnumMap<>(Language.class);

    private static final Map<String, String> ALIASES = new LinkedHashMap<>();

    static {
        initializeEnglishMessages();
        initializeTamilMessages();
        initializeHindiMessages();
        initializeAliases();
    }

    private MessageProvider() {
    }

    private static void initializeEnglishMessages() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("welcome", "Welcome to Museum Ticketing System");
        m.put("app_title", "Museum Ticketing System");
        m.put("select_language", "Select Language");
        m.put("english", "English");
        m.put("tamil", "Tamil");
        m.put("hindi", "Hindi");
        m.put("auth_menu_title", "Authentication Menu");
        m.put("auth_register", "Register as Visitor");
        m.put("auth_login", "Login");
        m.put("auth_admin_login", "Admin Login");
        m.put("auth_back", "Back");
        m.put("auth_exit", "Exit");
        m.put("register_title", "Register as Visitor");
        m.put("enter_email", "Enter email:");
        m.put("enter_name", "Enter name:");
        m.put("enter_password", "Enter password:");
        m.put("enter_phone", "Enter phone (optional):");
        m.put("enter_address", "Enter address (optional):");
        m.put("registration_success", "Registration successful! Please login.");
        m.put("registration_failed", "Registration failed!");
        m.put("email_already_exists", "Email is already registered!");
        m.put("login_title", "Login");
        m.put("login_success", "Login successful! Welcome");
        m.put("login_failed", "Login failed! Invalid email or password.");
        m.put("not_registered", "User not registered. Please register first.");
        m.put("main_menu_title", "Main Menu");
        m.put("view_exhibits", "View Exhibits");
        m.put("search_exhibits", "Search Exhibits");
        m.put("book_ticket", "Book Ticket");
        m.put("my_bookings", "My Bookings");
        m.put("cancel_booking", "Cancel Booking");
        m.put("view_analytics", "View Analytics (Admin)");
        m.put("help", "Help");
        m.put("logout", "Logout");
        m.put("exit", "Exit");
        m.put("exhibits_list", "Available Exhibits:");
        m.put("no_exhibits", "No exhibits available.");
        m.put("booking_title", "Book Ticket");
        m.put("enter_exhibit_id", "Enter exhibit ID:");
        m.put("select_date", "Enter date (YYYY-MM-DD):");
        m.put("select_timeslot", "Select time slot:");
        m.put("enter_quantity", "Enter quantity:");
        m.put("total_amount", "Total Amount:");
        m.put("booking_created", "Booking created successfully!");
        m.put("booking_failed", "Booking failed!");
        m.put("payment_title", "Payment");
        m.put("select_payment_method", "Select payment method:");
        m.put("payment_upi", "UPI");
        m.put("payment_card", "Card");
        m.put("payment_cash", "Cash");
        m.put("payment_processing", "Processing payment...");
        m.put("payment_success", "Payment successful!");
        m.put("payment_failed", "Payment failed! Please try again.");
        m.put("booking_confirmed", "Booking confirmed!");
        m.put("receipt_generated", "Receipt generated!");
        m.put("my_bookings_title", "My Bookings");
        m.put("no_bookings", "You have no bookings.");
        m.put("booking_id", "Booking ID:");
        m.put("booking_status", "Status:");
        m.put("booking_date", "Date:");
        m.put("booking_quantity", "Quantity:");
        m.put("booking_amount", "Amount:");
        m.put("cancel_booking_title", "Cancel Booking");
        m.put("enter_booking_id", "Enter booking ID to cancel:");
        m.put("cancel_success", "Booking cancelled successfully!");
        m.put("cancel_failed", "Failed to cancel booking!");
        m.put("booking_not_found", "Booking not found!");
        m.put("analytics_title", "Museum Analytics");
        m.put("total_bookings", "Total Bookings:");
        m.put("confirmed_bookings", "Confirmed:");
        m.put("cancelled_bookings", "Cancelled:");
        m.put("total_revenue", "Total Revenue:");
        m.put("most_visited", "Most Visited Exhibit:");
        m.put("peak_timeslot", "Peak Time Slot:");
        m.put("admin_menu_title", "Admin Menu");
        m.put("admin_view_analytics", "View Analytics");
        m.put("admin_manage_exhibits", "Manage Exhibits");
        m.put("admin_view_bookings", "View All Bookings");
        m.put("admin_logout", "Logout");
        m.put("invalid_input", "Invalid input! Please try again.");
        m.put("invalid_email", "Invalid email format!");
        m.put("invalid_password", "Password must be at least 4 characters!");
        m.put("invalid_name", "Name must be at least 2 characters!");
        m.put("invalid_phone", "Phone must be 10 digits!");
        m.put("invalid_quantity", "Quantity must be between 1 and 100!");
        m.put("enter_choice", "Enter your choice:");
        m.put("invalid_choice", "Invalid choice! Please try again.");
        m.put("goodbye", "Thank you for using the Museum Ticketing System. Goodbye!");
        m.put("error", "An error occurred! Please try again.");
        m.put("loading", "Loading...");
        m.put("success", "Success!");
        m.put("failed", "Failed!");
        m.put("back_to_menu", "Press Enter to continue...");
        m.put("search_prompt", "Enter search keyword:");
        m.put("search_results", "Search Results:");
        m.put("no_search_results", "No exhibits found.");
        m.put("help_title", "Help - How to use the system:");
        m.put("logout_success", "You have been logged out.");
        m.put("invalid_selection", "Invalid selection! Please try again.");
        m.put("error_details", "Error: {0}");
        MESSAGES.put(Language.ENGLISH, m);
    }

    private static void initializeTamilMessages() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("welcome", "அருங்காட்சியக டிக்கெட் அமைப்பிற்கு வரவேற்கிறோம்");
        m.put("app_title", "அருங்காட்சியக டிக்கெட் அமைப்பு");
        m.put("select_language", "மொழியைத் தேர்ந்தெடுக்கவும்");
        m.put("english", "ஆங்கிலம்");
        m.put("tamil", "தமிழ்");
        m.put("hindi", "இந்தி");
        m.put("auth_menu_title", "அங்கீகார மெனு");
        m.put("auth_register", "பார்வையாளராக பதிவு செய்யவும்");
        m.put("auth_login", "உள்நுழைக");
        m.put("auth_admin_login", "நிர்வாகி உள்நுழைவு");
        m.put("auth_back", "பின்செல்");
        m.put("auth_exit", "வெளியேறு");
        m.put("register_title", "பார்வையாளராக பதிவு செய்யவும்");
        m.put("enter_email", "மின்னஞ்சலை உள்ளிடவும்:");
        m.put("enter_name", "பெயரை உள்ளிடவும்:");
        m.put("enter_password", "கடவுச்சொல்லை உள்ளிடவும்:");
        m.put("enter_phone", "தொலைபேசி எண்ணை உள்ளிடவும் (விருப்பம்):");
        m.put("enter_address", "முகவரியை உள்ளிடவும் (விருப்பம்):");
        m.put("registration_success", "பதிவு வெற்றிகரமாக முடிந்தது! தயவுசெய்து உள்நுழைக.");
        m.put("registration_failed", "பதிவு தோல்வியடைந்தது!");
        m.put("email_already_exists", "இந்த மின்னஞ்சல் ஏற்கனவே பதிவு செய்யப்பட்டுள்ளது!");
        m.put("login_title", "உள்நுழைவு");
        m.put("login_success", "உள்நுழைவு வெற்றிகரமாக முடிந்தது! வரவேற்கிறோம்");
        m.put("login_failed", "உள்நுழைவு தோல்வியடைந்தது! மின்னஞ்சல் அல்லது கடவுச்சொல் தவறாக உள்ளது.");
        m.put("not_registered", "பயனர் பதிவு செய்யப்படவில்லை. முதலில் பதிவு செய்யவும்.");
        m.put("main_menu_title", "முக்கிய மெனு");
        m.put("view_exhibits", "அருங்காட்சியகப் பொருட்களைக் காண்க");
        m.put("search_exhibits", "அருங்காட்சியகப் பொருட்களைத் தேடுக");
        m.put("book_ticket", "டிக்கெட் பதிவு செய்க");
        m.put("my_bookings", "எனது பதிவுகள்");
        m.put("cancel_booking", "பதிவை ரத்து செய்க");
        m.put("view_analytics", "பகுப்பாய்வைக் காண்க (நிர்வாகி)");
        m.put("help", "உதவி");
        m.put("logout", "வெளியேறு");
        m.put("exit", "வெளியேறு");
        m.put("exhibits_list", "கிடைக்கக்கூடிய அருங்காட்சியகப் பொருட்கள்:");
        m.put("no_exhibits", "அருங்காட்சியகப் பொருட்கள் எதுவும் இல்லை.");
        m.put("booking_title", "டிக்கெட் பதிவு");
        m.put("enter_exhibit_id", "அருங்காட்சியகப் பொருளின் ID-ஐ உள்ளிடவும்:");
        m.put("select_date", "தேதியை உள்ளிடவும் (YYYY-MM-DD):");
        m.put("select_timeslot", "நேர இடைவெளியைத் தேர்ந்தெடுக்கவும்:");
        m.put("enter_quantity", "எண்ணிக்கையை உள்ளிடவும்:");
        m.put("total_amount", "மொத்த தொகை:");
        m.put("booking_created", "பதிவு வெற்றிகரமாக உருவாக்கப்பட்டது!");
        m.put("booking_failed", "பதிவு தோல்வியடைந்தது!");
        m.put("payment_title", "பணம் செலுத்துதல்");
        m.put("select_payment_method", "பணம் செலுத்தும் முறையைத் தேர்ந்தெடுக்கவும்:");
        m.put("payment_upi", "UPI");
        m.put("payment_card", "அட்டை");
        m.put("payment_cash", "பணம்");
        m.put("payment_processing", "பணம் செலுத்துதல் நடைபெறுகிறது...");
        m.put("payment_success", "பணம் செலுத்துதல் வெற்றிகரமாக முடிந்தது!");
        m.put("payment_failed", "பணம் செலுத்துதல் தோல்வியடைந்தது! மீண்டும் முயற்சிக்கவும்.");
        m.put("booking_confirmed", "பதிவு உறுதிசெய்யப்பட்டது!");
        m.put("receipt_generated", "ரசீது உருவாக்கப்பட்டது!");
        m.put("my_bookings_title", "எனது பதிவுகள்");
        m.put("no_bookings", "உங்களிடம் பதிவுகள் எதுவும் இல்லை.");
        m.put("booking_id", "பதிவு ID:");
        m.put("booking_status", "நிலை:");
        m.put("booking_date", "தேதி:");
        m.put("booking_quantity", "எண்ணிக்கை:");
        m.put("booking_amount", "தொகை:");
        m.put("cancel_booking_title", "பதிவை ரத்து செய்க");
        m.put("enter_booking_id", "ரத்து செய்ய வேண்டிய பதிவு ID-ஐ உள்ளிடவும்:");
        m.put("cancel_success", "பதிவு வெற்றிகரமாக ரத்து செய்யப்பட்டது!");
        m.put("cancel_failed", "பதிவை ரத்து செய்ய முடியவில்லை!");
        m.put("booking_not_found", "பதிவு கிடைக்கவில்லை!");
        m.put("analytics_title", "அருங்காட்சியகப் பகுப்பாய்வு");
        m.put("total_bookings", "மொத்த பதிவுகள்:");
        m.put("confirmed_bookings", "உறுதிசெய்யப்பட்டவை:");
        m.put("cancelled_bookings", "ரத்து செய்யப்பட்டவை:");
        m.put("total_revenue", "மொத்த வருவாய்:");
        m.put("most_visited", "அதிகம் பார்வையிடப்பட்ட பகுதி:");
        m.put("peak_timeslot", "அதிக நெரிசல் நேரம்:");
        m.put("admin_menu_title", "நிர்வாகி மெனு");
        m.put("admin_view_analytics", "பகுப்பாய்வைக் காண்க");
        m.put("admin_manage_exhibits", "அருங்காட்சியகப் பொருட்களை நிர்வகிக்கவும்");
        m.put("admin_view_bookings", "அனைத்து பதிவுகளையும் காண்க");
        m.put("admin_logout", "வெளியேறு");
        m.put("invalid_input", "தவறான உள்ளீடு! மீண்டும் முயற்சிக்கவும்.");
        m.put("invalid_email", "தவறான மின்னஞ்சல் வடிவம்!");
        m.put("invalid_password", "கடவுச்சொல் குறைந்தது 4 எழுத்துகள் இருக்க வேண்டும்!");
        m.put("invalid_name", "பெயர் குறைந்தது 2 எழுத்துகள் இருக்க வேண்டும்!");
        m.put("invalid_phone", "தொலைபேசி எண் 10 இலக்கங்கள் இருக்க வேண்டும்!");
        m.put("invalid_quantity", "எண்ணிக்கை 1 முதல் 100 வரை இருக்க வேண்டும்!");
        m.put("enter_choice", "உங்கள் தேர்வை உள்ளிடவும்:");
        m.put("invalid_choice", "தவறான தேர்வு! மீண்டும் முயற்சிக்கவும்.");
        m.put("goodbye", "அருங்காட்சியக டிக்கெட் அமைப்பைப் பயன்படுத்தியதற்கு நன்றி. வணக்கம்!");
        m.put("error", "பிழை ஏற்பட்டுள்ளது! மீண்டும் முயற்சிக்கவும்.");
        m.put("loading", "ஏற்றப்படுகிறது...");
        m.put("success", "வெற்றி!");
        m.put("failed", "தோல்வி!");
        m.put("back_to_menu", "தொடர Enter விசையை அழுத்தவும்...");
        m.put("search_prompt", "தேட வேண்டிய சொல்லை உள்ளிடவும்:");
        m.put("search_results", "தேடல் முடிவுகள்:");
        m.put("no_search_results", "பொருத்தமான அருங்காட்சியகப் பொருட்கள் எதுவும் கிடைக்கவில்லை.");
        m.put("help_title", "உதவி - அமைப்பைப் பயன்படுத்தும் முறை:");
        m.put("logout_success", "நீங்கள் வெளியேறிவிட்டீர்கள்.");
        m.put("invalid_selection", "தவறான தேர்வு! மீண்டும் முயற்சிக்கவும்.");
        m.put("error_details", "பிழை: {0}");
        MESSAGES.put(Language.TAMIL, m);
    }

    private static void initializeHindiMessages() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("welcome", "संग्रहालय टिकटिंग सिस्टम में आपका स्वागत है");
        m.put("app_title", "संग्रहालय टिकटिंग सिस्टम");
        m.put("select_language", "भाषा चुनें");
        m.put("english", "अंग्रेज़ी");
        m.put("tamil", "तमिल");
        m.put("hindi", "हिंदी");
        m.put("auth_menu_title", "प्रमाणीकरण मेनू");
        m.put("auth_register", "आगंतुक के रूप में पंजीकरण करें");
        m.put("auth_login", "लॉगिन करें");
        m.put("auth_admin_login", "प्रशासक लॉगिन");
        m.put("auth_back", "वापस जाएँ");
        m.put("auth_exit", "बाहर निकलें");
        m.put("register_title", "आगंतुक के रूप में पंजीकरण करें");
        m.put("enter_email", "ईमेल दर्ज करें:");
        m.put("enter_name", "नाम दर्ज करें:");
        m.put("enter_password", "पासवर्ड दर्ज करें:");
        m.put("enter_phone", "फोन नंबर दर्ज करें (वैकल्पिक):");
        m.put("enter_address", "पता दर्ज करें (वैकल्पिक):");
        m.put("registration_success", "पंजीकरण सफल हुआ! कृपया लॉगिन करें।");
        m.put("registration_failed", "पंजीकरण विफल हुआ!");
        m.put("email_already_exists", "यह ईमेल पहले से पंजीकृत है!");
        m.put("login_title", "लॉगिन");
        m.put("login_success", "लॉगिन सफल हुआ! आपका स्वागत है");
        m.put("login_failed", "लॉगिन विफल हुआ! ईमेल या पासवर्ड गलत है।");
        m.put("not_registered", "उपयोगकर्ता पंजीकृत नहीं है। पहले पंजीकरण करें।");
        m.put("main_menu_title", "मुख्य मेनू");
        m.put("view_exhibits", "प्रदर्शनी देखें");
        m.put("search_exhibits", "प्रदर्शनी खोजें");
        m.put("book_ticket", "टिकट बुक करें");
        m.put("my_bookings", "मेरी बुकिंग");
        m.put("cancel_booking", "बुकिंग रद्द करें");
        m.put("view_analytics", "विश्लेषण देखें (प्रशासक)");
        m.put("help", "सहायता");
        m.put("logout", "लॉगआउट करें");
        m.put("exit", "बाहर निकलें");
        m.put("exhibits_list", "उपलब्ध प्रदर्शनियाँ:");
        m.put("no_exhibits", "कोई प्रदर्शनी उपलब्ध नहीं है।");
        m.put("booking_title", "टिकट बुक करें");
        m.put("enter_exhibit_id", "प्रदर्शनी ID दर्ज करें:");
        m.put("select_date", "तारीख दर्ज करें (YYYY-MM-DD):");
        m.put("select_timeslot", "समय स्लॉट चुनें:");
        m.put("enter_quantity", "मात्रा दर्ज करें:");
        m.put("total_amount", "कुल राशि:");
        m.put("booking_created", "बुकिंग सफलतापूर्वक बनाई गई!");
        m.put("booking_failed", "बुकिंग विफल हुई!");
        m.put("payment_title", "भुगतान");
        m.put("select_payment_method", "भुगतान विधि चुनें:");
        m.put("payment_upi", "UPI");
        m.put("payment_card", "कार्ड");
        m.put("payment_cash", "नकद");
        m.put("payment_processing", "भुगतान प्रक्रिया जारी है...");
        m.put("payment_success", "भुगतान सफल हुआ!");
        m.put("payment_failed", "भुगतान विफल हुआ! कृपया पुनः प्रयास करें।");
        m.put("booking_confirmed", "बुकिंग की पुष्टि हो गई!");
        m.put("receipt_generated", "रसीद बनाई गई!");
        m.put("my_bookings_title", "मेरी बुकिंग");
        m.put("no_bookings", "आपके पास कोई बुकिंग नहीं है।");
        m.put("booking_id", "बुकिंग ID:");
        m.put("booking_status", "स्थिति:");
        m.put("booking_date", "तारीख:");
        m.put("booking_quantity", "मात्रा:");
        m.put("booking_amount", "राशि:");
        m.put("cancel_booking_title", "बुकिंग रद्द करें");
        m.put("enter_booking_id", "रद्द करने के लिए बुकिंग ID दर्ज करें:");
        m.put("cancel_success", "बुकिंग सफलतापूर्वक रद्द कर दी गई!");
        m.put("cancel_failed", "बुकिंग रद्द नहीं हो सकी!");
        m.put("booking_not_found", "बुकिंग नहीं मिली!");
        m.put("analytics_title", "संग्रहालय विश्लेषण");
        m.put("total_bookings", "कुल बुकिंग:");
        m.put("confirmed_bookings", "पुष्टि की गई:");
        m.put("cancelled_bookings", "रद्द की गई:");
        m.put("total_revenue", "कुल राजस्व:");
        m.put("most_visited", "सबसे अधिक देखी गई प्रदर्शनी:");
        m.put("peak_timeslot", "सबसे व्यस्त समय स्लॉट:");
        m.put("admin_menu_title", "प्रशासक मेनू");
        m.put("admin_view_analytics", "विश्लेषण देखें");
        m.put("admin_manage_exhibits", "प्रदर्शनियाँ प्रबंधित करें");
        m.put("admin_view_bookings", "सभी बुकिंग देखें");
        m.put("admin_logout", "लॉगआउट करें");
        m.put("invalid_input", "अमान्य इनपुट! कृपया पुनः प्रयास करें।");
        m.put("invalid_email", "अमान्य ईमेल प्रारूप!");
        m.put("invalid_password", "पासवर्ड कम से कम 4 वर्ण का होना चाहिए!");
        m.put("invalid_name", "नाम कम से कम 2 वर्ण का होना चाहिए!");
        m.put("invalid_phone", "फोन नंबर 10 अंकों का होना चाहिए!");
        m.put("invalid_quantity", "मात्रा 1 से 100 के बीच होनी चाहिए!");
        m.put("enter_choice", "अपनी पसंद दर्ज करें:");
        m.put("invalid_choice", "अमान्य विकल्प! कृपया पुनः प्रयास करें।");
        m.put("goodbye", "Museum Ticketing System का उपयोग करने के लिए धन्यवाद। अलविदा!");
        m.put("error", "एक त्रुटि हुई! कृपया पुनः प्रयास करें।");
        m.put("loading", "लोड हो रहा है...");
        m.put("success", "सफलता!");
        m.put("failed", "विफल!");
        m.put("back_to_menu", "जारी रखने के लिए Enter दबाएँ...");
        m.put("search_prompt", "खोज शब्द दर्ज करें:");
        m.put("search_results", "खोज परिणाम:");
        m.put("no_search_results", "कोई मेल खाती प्रदर्शनी नहीं मिली।");
        m.put("help_title", "सहायता - सिस्टम का उपयोग कैसे करें:");
        m.put("logout_success", "आप लॉगआउट हो गए हैं।");
        m.put("invalid_selection", "अमान्य चयन! कृपया पुनः प्रयास करें।");
        m.put("error_details", "त्रुटि: {0}");
        MESSAGES.put(Language.HINDI, m);
    }

    private static void initializeAliases() {
        alias("app.welcome", "welcome");
        alias("app.title", "app_title");
        alias("auth.email", "enter_email");
        alias("booking.success", "booking_created");
        alias("app.goodbye", "goodbye");
        alias("lang.select", "select_language");
        alias("lang.english", "english");
        alias("lang.tamil", "tamil");
        alias("lang.hindi", "hindi");
        alias("auth.menu", "auth_menu_title");
        alias("auth.register", "auth_register");
        alias("auth.login", "auth_login");
        alias("auth.admin.login", "auth_admin_login");
        alias("auth.logout", "logout");
        alias("auth.logout.success", "logout_success");
        alias("auth.register.success", "registration_success");
        alias("auth.register.fail", "registration_failed");
        alias("admin.login", "auth_admin_login");
        alias("admin.menu", "admin_menu_title");
        alias("general.exit", "exit");
        alias("general.help", "help");
        alias("error.invalid.input", "invalid_input");
        alias("error.invalid.selection", "invalid_selection");
        alias("exhibit.view", "view_exhibits");
        alias("exhibit.search", "search_exhibits");
        alias("exhibit.list", "exhibits_list");
        alias("booking.menu", "book_ticket");
        alias("booking.select", "search_prompt");
        alias("booking.history", "my_bookings");
    }

    private static void alias(String key, String target) {
        ALIASES.put(key, target);
    }

    public static String getMessage(Language language, String key) {
        if (language == null) language = Language.ENGLISH;
        Map<String, String> messages = MESSAGES.get(language);
        if (messages == null) messages = MESSAGES.get(Language.ENGLISH);

        String message = messages.get(key);
        if (message == null) {
            String target = ALIASES.get(key);
            if (target != null) message = messages.get(target);
        }

        // If a new key is missing in the selected language, try its English
        // equivalent instead of displaying the raw key.
        if (message == null && !Language.ENGLISH.equals(language)) {
            Map<String, String> english = MESSAGES.get(Language.ENGLISH);
            message = english.get(key);
            if (message == null) {
                String target = ALIASES.get(key);
                if (target != null) message = english.get(target);
            }
        }

        return message != null ? message : key;
    }

    public static String getMessage(Language language, String key, Object... params) {
        String message = getMessage(language, key);
        if (params == null || params.length == 0) return message;
        return MessageFormat.format(message, params);
    }

    public static boolean isLanguageSupported(Language language) {
        return language != null && MESSAGES.containsKey(language);
    }

    public static Set<Language> getSupportedLanguages() {
        return Collections.unmodifiableSet(MESSAGES.keySet());
    }
}
