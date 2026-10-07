package museum.model;

/**
 * Language.java
 * Enum representing supported languages in the museum ticketing system
 * Demonstrates:
 * - Enum type definition
 * - Static constants
 * - Override methods in enums
 * - Type-safe language selection
 *
 * @author Sriram S
 * @version 1.0
 * @since Java 24
 */
public enum Language {
    /**
     * English language
     */
    ENGLISH("English", "EN"),

    /**
     * Hindi language
     */
    HINDI("हिन्दी", "HI"),

    /**
     * Tamil language
     */
    TAMIL("தமிழ்", "TA");

    /**
     * Display name of language
     */
    private final String displayName;

    /**
     * Language code (ISO 639-1)
     */
    private final String languageCode;

    /**
     * Constructor for Language enum
     *
     * @param displayName the display name of the language
     * @param languageCode the ISO 639-1 language code
     */
    Language(String displayName, String languageCode) {
        this.displayName = displayName;
        this.languageCode = languageCode;
    }

    /**
     * Get display name of language
     * @return display name (e.g., "English", "தமிழ்")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get ISO 639-1 language code
     * @return language code (e.g., "EN", "TA", "HI")
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Get language by display name (case-insensitive)
     *
     * @param displayName the display name to search for
     * @return Language enum or null if not found
     */
    public static Language getByDisplayName(String displayName) {
        for (Language lang : Language.values()) {
            if (lang.displayName.equalsIgnoreCase(displayName)) {
                return lang;
            }
        }
        return null;
    }

    /**
     * Override toString to display language nicely
     * @return display name
     */
    @Override
    public String toString() {
        return displayName;
    }
}