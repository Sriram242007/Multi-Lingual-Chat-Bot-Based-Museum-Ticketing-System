package museum.localization;

import museum.model.Language;

import java.util.HashMap;
import java.util.Map;

/**
 * LocalizationService.java
 * Service for managing localization and providing translated messages
 *
 * Demonstrates:
 * - Service layer for localization
 * - Managing current language state
 * - Providing language-specific messages to the application
 * - Singleton-like pattern for localization
 *
 * @author Sriram S
 * @version 1.0
 */
public class LocalizationService {

    /**
     * Current language for this session
     * Private field - encapsulation
     */
    private Language currentLanguage;

    /**
     * Cache for frequently used messages
     * Demonstrates: HashMap for caching
     */
    private Map<String, String> messageCache;

    /**
     * Constructor with default language
     * @param defaultLanguage the default language
     */
    public LocalizationService(Language defaultLanguage) {
        this.currentLanguage = defaultLanguage;
        this.messageCache = new HashMap<>();
    }

    /**
     * Constructor with English as default
     */
    public LocalizationService() {
        this(Language.ENGLISH);
    }

    /**
     * Set current language
     *
     * Demonstrates:
     * - Encapsulation: controlling language state
     *
     * @param language the language to set
     * @throws IllegalArgumentException if language not supported
     */
    public void setLanguage(Language language) throws IllegalArgumentException {
        if (!MessageProvider.isLanguageSupported(language)) {
            throw new IllegalArgumentException("Language not supported: " + language);
        }

        this.currentLanguage = language;
        messageCache.clear(); // Clear cache when language changes
    }

    /**
     * Get current language
     * @return current language
     */
    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    /**
     * Get localized message for current language
     *
     * Demonstrates:
     * - Service abstraction over message provider
     * - Simplified API for message retrieval
     *
     * @param key the message key
     * @return the translated message
     */
    public String get(String key) {
        return MessageProvider.getMessage(currentLanguage, key);
    }

    /**
     * Get localized message with parameters
     *
     * @param key the message key
     * @param params parameters to substitute
     * @return the translated message with parameters
     */
    public String get(String key, Object... params) {
        return MessageProvider.getMessage(currentLanguage, key, params);
    }

    /**
     * Get language name in current language
     *
     * Demonstrates:
     * - Using language-specific display names
     *
     * @param language the language to display
     * @return the language name in current language
     */
    public String getLanguageName(Language language) {
        String key = "lang." + language.name().toLowerCase();
        return MessageProvider.getMessage(currentLanguage, key);
    }

    /**
     * Get all language names in current language
     *
     * Demonstrates:
     * - Building dynamic lists based on current language
     * - Collections iteration
     *
     * @return map of language to display name
     */
    public Map<Language, String> getAllLanguageNames() {
        Map<Language, String> names = new HashMap<>();

        for (Language lang : MessageProvider.getSupportedLanguages()) {
            names.put(lang, getLanguageName(lang));
        }

        return names;
    }

    /**
     * Display language selection menu in current language
     *
     * Demonstrates:
     * - Formatting output for language selection
     *
     * @return formatted language menu
     */
    public String getLanguageMenu() {
        StringBuilder menu = new StringBuilder();

        menu.append("\n").append(get("lang.select")).append("\n\n");

        int index = 1;
        for (Language lang : MessageProvider.getSupportedLanguages()) {
            menu.append(index).append(". ")
                    .append(getLanguageName(lang))
                    .append("\n");
            index++;
        }

        return menu.toString();
    }

    /**
     * Display authentication menu in current language
     *
     * @return formatted authentication menu
     */
    public String getAuthenticationMenu() {
        StringBuilder menu = new StringBuilder();

        menu.append("\n").append(get("auth.menu")).append("\n\n");
        menu.append("1. ").append(get("auth.register")).append("\n");
        menu.append("2. ").append(get("auth.login")).append("\n");
        menu.append("3. ").append(get("auth.admin.login")).append("\n");
        menu.append("4. ").append(get("general.exit")).append("\n");

        return menu.toString();
    }

    /**
     * Display main menu in current language
     *
     * @return formatted main menu
     */
    public String getMainMenu() {
        StringBuilder menu = new StringBuilder();

        menu.append("\n").append(get("auth.menu")).append("\n\n");
        menu.append("1. ").append(get("exhibit.view")).append("\n");
        menu.append("2. ").append(get("exhibit.search")).append("\n");
        menu.append("3. ").append(get("booking.menu")).append("\n");
        menu.append("4. ").append(get("booking.history")).append("\n");
        menu.append("5. ").append(get("admin.login")).append("\n");
        menu.append("6. ").append(get("general.help")).append("\n");
        menu.append("7. ").append(get("auth.logout")).append("\n");
        menu.append("8. ").append(get("general.exit")).append("\n");

        return menu.toString();
    }

    /**
     * Get formatted error message
     *
     * @param errorKey the error key
     * @param details error details
     * @return formatted error message
     */
    public String getError(String errorKey, String details) {
        return get(errorKey, details);
    }

    /**
     * Check if current language is English
     * @return true if English
     */
    public boolean isEnglish() {
        return currentLanguage == Language.ENGLISH;
    }

    /**
     * Check if current language is Tamil
     * @return true if Tamil
     */
    public boolean isTamil() {
        return currentLanguage == Language.TAMIL;
    }

    /**
     * Check if current language is Hindi
     * @return true if Hindi
     */
    public boolean isHindi() {
        return currentLanguage == Language.HINDI;
    }

    /**
     * Get language direction (LTR or RTL)
     * Currently all supported languages use LTR
     *
     * @return "LTR" or "RTL"
     */
    public String getTextDirection() {
        // All our supported languages use LTR
        return "LTR";
    }

    public void setCurrentLanguage(Language currentLanguage) {
        setLanguage(currentLanguage);
    }
}