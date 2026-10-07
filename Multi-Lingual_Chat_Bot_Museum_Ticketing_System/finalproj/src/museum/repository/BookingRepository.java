package museum.repository;

import museum.model.Booking;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    private String filePath = "data/bookings.csv";
    private final List<Booking> bookings = new ArrayList<>();

    // Default No-Args Constructor
    public BookingRepository() {
        loadFromFile();
    }

    // Custom File Path Constructor
    public BookingRepository(String filePath) {
        if (filePath != null && !filePath.trim().isEmpty()) {
            this.filePath = filePath;
        }
        loadFromFile();
    }

    public synchronized void save(Booking booking) {
        if (booking == null) return;
        bookings.add(booking);
        saveToFile();
    }

    public synchronized void add(Booking booking) {
        save(booking);
    }

    public synchronized List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    public synchronized List<Booking> getAllBookings() {
        return findAll();
    }

    public synchronized Booking findById(String id) {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(id) || (b.getVisitorName() != null && b.getVisitorName().equalsIgnoreCase(id))) {
                return b;
            }
        }
        return null;
    }

    public synchronized List<Booking> findByUserEmail(String email) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getUserEmail().equalsIgnoreCase(email) || b.getVisitorName().equalsIgnoreCase(email)) {
                userBookings.add(b);
            }
        }
        return userBookings;
    }

    public synchronized List<Booking> findByVisitorEmail(String email) {
        return findByUserEmail(email);
    }

    public synchronized List<Booking> findByVisitorName(String visitorName) {
        return findByUserEmail(visitorName);
    }

    public synchronized List<Booking> findByStatus(String status) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getStatus() != null && b.getStatus().equalsIgnoreCase(status)) {
                result.add(b);
            }
        }
        return result;
    }

    public synchronized boolean confirmBooking(String bookingId) {
        Booking b = findById(bookingId);
        if (b != null) {
            b.setStatus("CONFIRMED");
            saveToFile();
            return true;
        }
        return false;
    }

    public synchronized boolean confirmBooking(String bookingId, String paymentStatus) {
        Booking b = findById(bookingId);
        if (b != null) {
            b.setStatus("CONFIRMED");
            saveToFile();
            return true;
        }
        return false;
    }

    public synchronized int count() {
        return bookings.size();
    }

    public synchronized double getTotalRevenue() {
        return bookings.stream()
                .mapToDouble(Booking::getTotalAmount)
                .sum();
    }

    public synchronized void clear() {
        bookings.clear();
        saveToFile();
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);

                try {
                    if (parts.length >= 11) {
                        Booking booking = new Booking(
                                parts[0].trim(),                     // bookingId
                                parts[1].trim(),                     // userEmail
                                parts[2].trim(),                     // visitorName
                                parts[3].trim(),                     // exhibitId
                                LocalDate.parse(parts[4].trim()),    // bookingDate
                                parts[5].trim(),                     // timeSlot
                                Integer.parseInt(parts[6].trim()),   // tickets
                                Double.parseDouble(parts[7].trim()),  // totalAmount
                                Double.parseDouble(parts[8].trim()),  // pricePerTicket
                                parts[9].trim(),                     // status
                                parts[10].trim()                     // exhibitName
                        );
                        bookings.add(booking);
                    } else if (parts.length >= 5) {
                        Booking booking = new Booking(
                                parts[0].trim(),                     // visitorName / id
                                parts[1].trim(),                     // exhibitId
                                LocalDate.parse(parts[2].trim()),    // bookingDate
                                parts[3].trim(),                     // timeSlot
                                Integer.parseInt(parts[4].trim())    // tickets
                        );
                        bookings.add(booking);
                    }
                } catch (Exception ignored) {}
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings file: " + e.getMessage());
        }
    }

    private synchronized void saveToFile() {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
            for (Booking booking : bookings) {
                writer.println(booking.toFileFormat());
            }
        } catch (IOException e) {
            System.err.println("Error writing bookings file: " + e.getMessage());
        }
    }
}