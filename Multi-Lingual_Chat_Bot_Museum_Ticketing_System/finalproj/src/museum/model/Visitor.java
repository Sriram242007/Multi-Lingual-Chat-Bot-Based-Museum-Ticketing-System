package museum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Visitor.java
 * Represents a museum visitor (extends User)
 *
 * Demonstrates:
 * - Inheritance (extends User)
 * - Constructor chaining (super())
 * - Concrete implementation of abstract methods
 * - Instance collections (ArrayList for bookings)
 * - Access specifiers (public, private, protected)
 * - Polymorphism (overriding abstract methods)
 *
 * @author Your Name
 * @version 1.0
 * @since Java 24
 */
public class Visitor extends User {

    // ==================== PRIVATE FIELDS ====================

    /**
     * List of all bookings made by this visitor
     * Uses ArrayList for dynamic storage and iteration
     */
    private List<Booking> bookings;

    /**
     * Phone number of visitor
     */
    private String phoneNumber;

    /**
     * Address of visitor
     */
    private String address;

    /**
     * Login status of current session
     */
    private boolean isLoggedIn;

    /**
     * Last login time
     */
    private LocalDateTime lastLoginTime;

    /**
     * Preferred language
     */
    private Language preferredLanguage;

    // ==================== CONSTRUCTORS ====================

    /**
     * Full constructor for Visitor
     * Calls parent constructor using super()
     *
     * @param email visitor's email
     * @param name visitor's name
     * @param password visitor's password
     * @param phoneNumber visitor's phone number
     * @param address visitor's address
     */
    public Visitor(String email, String name, String password,
                   String phoneNumber, String address) {
        super(email, name, password, "VISITOR");
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.bookings = new ArrayList<>();
        this.isLoggedIn = false;
        this.lastLoginTime = null;
        this.preferredLanguage = Language.ENGLISH;
    }

    /**
     * Minimal constructor for Visitor (with defaults)
     *
     * @param email visitor's email
     * @param name visitor's name
     * @param password visitor's password
     */
    public Visitor(String email, String name, String password) {
        super(email, name, password, "VISITOR");
        this.phoneNumber = "";
        this.address = "";
        this.bookings = new ArrayList<>();
        this.isLoggedIn = false;
        this.lastLoginTime = null;
        this.preferredLanguage = Language.ENGLISH;
    }

    // ==================== IMPLEMENT ABSTRACT METHODS ====================

    @Override
    public void login() {
        if (isAccountActive()) {
            this.isLoggedIn = true;
            this.lastLoginTime = LocalDateTime.now();
        }
    }

    @Override
    public void logout() {
        this.isLoggedIn = false;
    }

    @Override
    public String getUserRole() {
        return "VISITOR - Can browse exhibits and book tickets";
    }

    // ==================== VISITOR-SPECIFIC METHODS ====================

    /**
     * Add a booking to visitor's booking list
     *
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        if (booking != null) {
            this.bookings.add(booking);
        }
    }

    /**
     * Get all bookings for this visitor
     * @return list of bookings
     */
    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    /**
     * Find booking by booking ID
     *
     * @param bookingId the booking ID to search for
     * @return Booking if found, null otherwise
     */
    public Booking findBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Get total number of bookings
     * @return number of bookings
     */
    public int getTotalBookings() {
        return bookings.size();
    }

    /**
     * Cancel a booking by ID
     *
     * @param bookingId the booking ID to cancel
     * @return true if booking was cancelled
     */
    public boolean cancelBooking(String bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            booking.setStatus("CANCELLED");
            return true;
        }
        return false;
    }

    /**
     * Set preferred language for this visitor
     *
     * @param language the preferred language
     */
    public void setPreferredLanguage(Language language) {
        this.preferredLanguage = language;
    }

    /**
     * Get preferred language
     * @return preferred language
     */
    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * Check if visitor is currently logged in
     * @return login status
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Convert visitor to file format for persistence
     * @return comma-separated visitor data
     */
    public String toFileFormat() {
        return super.toFileFormat() + "," + phoneNumber + "," + address + "," +
                preferredLanguage;
    }

    /**
     * Override toString
     * @return visitor information
     */
    @Override
    public String toString() {
        return String.format("Visitor{email='%s', name='%s', phone='%s', bookings=%d, loggedIn=%b}",
                getEmail(), getName(), phoneNumber, bookings.size(), isLoggedIn);
    }

    // ==================== GETTERS ====================

    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public LocalDateTime getLastLoginTime() { return lastLoginTime; }

    // ==================== SETTERS ====================

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }

    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) {
            this.address = address;
        }
    }
}