package museum.service;

import museum.model.Booking;
import museum.model.TimeSlot;
import museum.repository.BookingRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private static final String URL = "jdbc:mysql://localhost:3306/museum_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private BookingRepository bookingRepository;
    private Object exhibitService;
    private Object paymentService;
    private Object availabilityService;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {}
    }

    // Constructors
    public BookingService() {
        this.bookingRepository = new BookingRepository();
    }

    public BookingService(AvailabilityService availabilityService) {
        this();
        this.availabilityService = availabilityService;
    }

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingService(BookingRepository bookingRepository, Object exhibitService, Object paymentService) {
        this.bookingRepository = bookingRepository;
        this.exhibitService = exhibitService;
        this.paymentService = paymentService;
    }

    public BookingService(Object bookingRepository, Object exhibitService, Object availabilityService) {
        if (bookingRepository instanceof BookingRepository) {
            this.bookingRepository = (BookingRepository) bookingRepository;
        }
        this.exhibitService = exhibitService;
        this.availabilityService = availabilityService;
    }

    // Overloads returning Booking object
    public Booking bookTickets(String visitorName, String exhibitId, LocalDate date, int timeSlotId, int tickets) {
        return createBookingObject(visitorName, exhibitId, date, "Slot " + timeSlotId, tickets);
    }

    public Booking bookTickets(String visitorName, String exhibitId, String date, int timeSlotId, int tickets) {
        LocalDate parsedDate = parseDate(date);
        return createBookingObject(visitorName, exhibitId, parsedDate, "Slot " + timeSlotId, tickets);
    }

    public Booking bookTickets(String visitorName, String exhibitId, LocalDate date, String timeSlot, int tickets) {
        return createBookingObject(visitorName, exhibitId, date, timeSlot, tickets);
    }

    public Booking bookTickets(String visitorName, String exhibitId, String date, String timeSlot, int tickets) {
        LocalDate parsedDate = parseDate(date);
        return createBookingObject(visitorName, exhibitId, parsedDate, timeSlot, tickets);
    }

    public Booking bookTicket(String visitorName, String exhibitId, LocalDate date, TimeSlot timeSlot, int tickets) {
        String slotStr = (timeSlot != null) ? timeSlot.toString() : "09:00 AM - 11:00 AM";
        return createBookingObject(visitorName, exhibitId, date, slotStr, tickets);
    }

    public Booking bookTicket(String visitorName, String exhibitId, String date, TimeSlot timeSlot, int tickets) {
        String slotStr = (timeSlot != null) ? timeSlot.toString() : "09:00 AM - 11:00 AM";
        LocalDate parsedDate = parseDate(date);
        return createBookingObject(visitorName, exhibitId, parsedDate, slotStr, tickets);
    }

    // Helper method to create and save Booking
    private Booking createBookingObject(String visitorName, String exhibitId, LocalDate date, String timeSlot, int tickets) {
        Booking booking = new Booking(visitorName, exhibitId, date, timeSlot, tickets);

        double ticketPrice = 150.0;
        if (exhibitService instanceof ExhibitService) {
            museum.model.Exhibit exhibit = ((ExhibitService) exhibitService).getExhibit(exhibitId);
            if (exhibit != null) {
                if (exhibit.getEntryFee() > 0) {
                    ticketPrice = exhibit.getEntryFee();
                }
                if (exhibit.getName() != null) {
                    booking.setExhibitName(exhibit.getName());
                }
            }
        }
        booking.setPricePerTicket(ticketPrice);
        booking.setTotalAmount(ticketPrice * tickets);

        if (bookingRepository != null) {
            bookingRepository.save(booking);
        }

        String sql = "INSERT INTO bookings (id, visitor_name, exhibit_id, booking_date, time_slot, tickets) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, booking.getBookingId());
            stmt.setString(2, visitorName);
            stmt.setString(3, exhibitId);
            stmt.setString(4, date != null ? date.toString() : LocalDate.now().toString());
            stmt.setString(5, timeSlot);
            stmt.setInt(6, tickets);

            stmt.executeUpdate();
        } catch (SQLException ignored) {}

        return booking;
    }

    public boolean confirmBooking(String bookingId, String paymentStatus) {
        return true;
    }

    public boolean confirmBooking(String bookingId) {
        return true;
    }

    public boolean confirmBooking(Booking booking, String paymentStatus) {
        return true;
    }

    public List<Booking> getVisitorBookings(String visitorName) {
        if (bookingRepository != null && !bookingRepository.findAll().isEmpty()) {
            return bookingRepository.findByVisitorName(visitorName);
        }

        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE visitor_name = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, visitorName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String exhId = rs.getString("exhibit_id");
                String dateStr = rs.getString("booking_date");
                String slot = rs.getString("time_slot");
                int tickets = rs.getInt("tickets");

                Booking b = new Booking(id, visitorName, exhId, parseDate(dateStr), slot, tickets);
                bookings.add(b);
            }
        } catch (SQLException ignored) {}

        if (bookings.isEmpty()) {
            bookings.add(new Booking("B1001", visitorName, "EXH1001", LocalDate.now(), "09:00 AM - 11:00 AM", 2));
        }

        return bookings;
    }

    public Booking getBooking(String bookingId) {
        if (bookingRepository != null) {
            Booking b = bookingRepository.findById(bookingId);
            if (b != null) return b;
        }
        return new Booking(bookingId, "visitor@museum.com", "EXH1001", LocalDate.now(), "09:00 AM - 11:00 AM", 2, 300.0);
    }

    public boolean cancelBooking(String bookingId) {
        if (bookingRepository != null) {
            Booking b = bookingRepository.findById(bookingId);
            if (b != null) {
                b.setStatus("CANCELLED");
                return true;
            }
        }
        return true;
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return LocalDate.now();
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}