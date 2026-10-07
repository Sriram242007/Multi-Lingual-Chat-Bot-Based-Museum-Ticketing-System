package museum.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {

    private String bookingId;
    private String visitorName;
    private String visitorEmail;
    private String exhibitId;
    private String exhibitName;
    private LocalDate visitDate;
    private String timeSlot;
    private int tickets;
    private double totalAmount;
    private double pricePerTicket;
    private String status;

    // 1. Default Constructor
    public Booking() {
        this.status = "PENDING";
    }

    // 2. 5-Parameter Constructor
    public Booking(String visitorName, String exhibitId, LocalDate visitDate, String timeSlot, int tickets) {
        this.bookingId = "BKG" + (System.currentTimeMillis() % 10000);
        this.visitorName = visitorName;
        this.visitorEmail = visitorName;
        this.exhibitId = exhibitId;
        this.exhibitName = exhibitId;
        this.visitDate = visitDate;
        this.timeSlot = timeSlot;
        this.tickets = tickets;
        this.status = "PENDING";
    }

    // 3. 6-Parameter Constructor
    public Booking(String p1, String p2, String p3, LocalDate visitDate, String timeSlot, int tickets) {
        if (p1 != null && (p1.startsWith("BKG") || p1.startsWith("B") || p1.matches(".*\\d.*"))) {
            this.bookingId = p1;
            this.visitorName = p2;
            this.visitorEmail = p2;
            this.exhibitId = p3;
            this.exhibitName = p3;
        } else {
            this.bookingId = "BKG" + (System.currentTimeMillis() % 10000);
            this.visitorName = p1;
            this.visitorEmail = p2;
            this.exhibitId = p3;
            this.exhibitName = p3;
        }
        this.visitDate = visitDate;
        this.timeSlot = timeSlot;
        this.tickets = tickets;
        this.status = "PENDING";
    }

    // 4. 7-Parameter Constructor
    public Booking(String p1, String p2, String p3, LocalDate visitDate, String timeSlot, int tickets, double priceOrTotal) {
        if (p1 != null && p1.contains("@")) {
            this.bookingId = "BKG" + (System.currentTimeMillis() % 10000);
            this.visitorName = p1;
            this.visitorEmail = p1;
            this.exhibitId = p2;
            this.exhibitName = p3;
            this.pricePerTicket = priceOrTotal;
            this.totalAmount = priceOrTotal * tickets;
        } else {
            this.bookingId = p1;
            this.visitorName = p2;
            this.visitorEmail = p2;
            this.exhibitId = p3;
            this.exhibitName = p3;
            this.totalAmount = priceOrTotal;
            this.pricePerTicket = tickets > 0 ? priceOrTotal / tickets : 0.0;
        }
        this.visitDate = visitDate;
        this.timeSlot = timeSlot;
        this.tickets = tickets;
        this.status = "PENDING";
    }

    // 5. 8-Parameter Constructor
    public Booking(String bookingId, String visitorName, String visitorEmail, String exhibitId, LocalDate visitDate, String timeSlot, int tickets, double totalAmount) {
        this.bookingId = bookingId;
        this.visitorName = visitorName;
        this.visitorEmail = visitorEmail;
        this.exhibitId = exhibitId;
        this.exhibitName = exhibitId;
        this.visitDate = visitDate;
        this.timeSlot = timeSlot;
        this.tickets = tickets;
        this.totalAmount = totalAmount;
        this.pricePerTicket = tickets > 0 ? totalAmount / tickets : 0.0;
        this.status = "PENDING";
    }

    // 6. 9-Parameter Constructor
    public Booking(String bookingId, String visitorName, String visitorEmail, String exhibitId, LocalDate visitDate, String timeSlot, int tickets, double totalAmount, String status) {
        this(bookingId, visitorName, visitorEmail, exhibitId, visitDate, timeSlot, tickets, totalAmount);
        this.status = status;
    }

    // 7. 11-Parameter Constructor
    public Booking(String bookingId, String visitorName, String visitorEmail, String exhibitId, LocalDate visitDate, String timeSlot, int tickets, double totalAmount, double pricePerTicket, String status, String extra) {
        this.bookingId = bookingId;
        this.visitorName = visitorName;
        this.visitorEmail = visitorEmail;
        this.exhibitId = exhibitId;
        this.exhibitName = exhibitId;
        this.visitDate = visitDate;
        this.timeSlot = timeSlot;
        this.tickets = tickets;
        this.totalAmount = totalAmount;
        this.pricePerTicket = pricePerTicket;
        this.status = status;
    }

    // Status Methods
    public void confirmBooking() {
        this.status = "CONFIRMED";
    }

    public boolean isConfirmed() {
        return "CONFIRMED".equalsIgnoreCase(this.status);
    }

    // File I/O Format
    public String toFileFormat() {
        return bookingId + "," +
                (visitorName != null ? visitorName : "") + "," +
                (visitorEmail != null ? visitorEmail : visitorName != null ? visitorName : "") + "," +
                (exhibitId != null ? exhibitId : "") + "," +
                (visitDate != null ? visitDate.toString() : "") + "," +
                (timeSlot != null ? timeSlot : "") + "," +
                tickets + "," +
                totalAmount + "," +
                pricePerTicket + "," +
                (status != null ? status : "PENDING") + ",N/A";
    }

    public static Booking fromFileFormat(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) return null;
        String[] parts = csvLine.split(",");
        if (parts.length < 5) return null;
        try {
            String bId = parts[0].trim();
            String vName = parts[1].trim();
            String vEmail = parts.length > 2 ? parts[2].trim() : vName;
            String eId = parts.length > 3 ? parts[3].trim() : "";
            LocalDate date = parts.length > 4 && !parts[4].trim().isEmpty() ? LocalDate.parse(parts[4].trim()) : LocalDate.now();
            String slot = parts.length > 5 ? parts[5].trim() : "";
            int tix = parts.length > 6 ? Integer.parseInt(parts[6].trim()) : 1;
            double amt = parts.length > 7 ? Double.parseDouble(parts[7].trim()) : 0.0;
            double ppt = parts.length > 8 ? Double.parseDouble(parts[8].trim()) : (tix > 0 ? amt / tix : 0.0);
            String st = parts.length > 9 ? parts[9].trim() : "PENDING";
            String extra = parts.length > 10 ? parts[10].trim() : "N/A";

            return new Booking(bId, vName, vEmail, eId, date, slot, tix, amt, ppt, st, extra);
        } catch (Exception e) {
            return null;
        }
    }

    // Getters and Setters

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getId() { return getBookingId(); }
    public void setId(String id) { setBookingId(id); }

    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
        if (this.visitorEmail == null) this.visitorEmail = visitorName;
    }

    public String getUserName() { return getVisitorName(); }
    public void setUserName(String name) { setVisitorName(name); }

    public String getVisitorEmail() {
        return visitorEmail != null ? visitorEmail : visitorName;
    }
    public void setVisitorEmail(String visitorEmail) { this.visitorEmail = visitorEmail; }

    public String getUserEmail() { return getVisitorEmail(); }
    public void setUserEmail(String email) { setVisitorEmail(email); }

    public String getExhibitId() { return exhibitId; }
    public void setExhibitId(String exhibitId) {
        this.exhibitId = exhibitId;
        if (this.exhibitName == null) this.exhibitName = exhibitId;
    }

    public String getExhibitName() {
        return exhibitName != null ? exhibitName : exhibitId;
    }
    public void setExhibitName(String exhibitName) { this.exhibitName = exhibitName; }

    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }

    // Required by ReportService (Fixes line 123 error)
    public LocalDate getBookingDate() { return visitDate; }
    public void setBookingDate(LocalDate bookingDate) { this.visitDate = bookingDate; }

    public LocalDate getDate() { return visitDate; }
    public void setDate(LocalDate date) { this.visitDate = date; }

    public String getFormattedVisitDate() {
        if (visitDate == null) return "N/A";
        return visitDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getSlot() { return getTimeSlot(); }
    public void setSlot(String slot) { setTimeSlot(slot); }

    public int getTickets() { return tickets; }
    public void setTickets(int tickets) {
        this.tickets = tickets;
        if (tickets > 0 && totalAmount > 0) this.pricePerTicket = totalAmount / tickets;
    }

    public int getTicketQuantity() { return tickets; }
    public void setTicketQuantity(int ticketQuantity) { setTickets(ticketQuantity); }

    public int getNumberOfTickets() { return tickets; }
    public void setNumberOfTickets(int n) { setTickets(n); }

    public double getTotalAmount() {
        if (totalAmount > 0) return totalAmount;
        double price = pricePerTicket > 0 ? pricePerTicket : 150.0;
        return price * (tickets > 0 ? tickets : 1);
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        if (tickets > 0 && totalAmount > 0) this.pricePerTicket = totalAmount / tickets;
    }

    public double getAmount() { return getTotalAmount(); }
    public void setAmount(double amount) { setTotalAmount(amount); }

    public double getPricePerTicket() {
        if (pricePerTicket > 0) return pricePerTicket;
        if (totalAmount > 0 && tickets > 0) return totalAmount / tickets;
        return 150.0;
    }
    public void setPricePerTicket(double pricePerTicket) {
        this.pricePerTicket = pricePerTicket;
        if (tickets > 0) this.totalAmount = pricePerTicket * tickets;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", visitorName='" + visitorName + '\'' +
                ", exhibitId='" + exhibitId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}