package museum.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlot {

    private String slotId;
    private String exhibitId;
    private LocalDate date;
    private String slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity = 100;
    private int availableTickets = 100;
    private String slotName;
    private String timeSlot;
    private String slotTime;
    private boolean available = true;
    private String status = "AVAILABLE";

    // Default Constructor
    public TimeSlot() {}

    // 4-Parameter Constructor (LocalDate date, LocalTime startTime, LocalTime endTime, int capacity)
    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, int capacity) {
        this.slotId = "SLOT" + (System.currentTimeMillis() % 10000);
        this.date = date;
        this.slotDate = (date != null) ? date.toString() : null;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.availableTickets = capacity;
        this.slotName = (startTime != null && endTime != null) ? startTime + " - " + endTime : "09:00 AM - 11:00 AM";
        this.timeSlot = this.slotName;
        this.slotTime = this.slotName;
        this.status = "AVAILABLE";
    }

    // 4-Parameter Constructor for String slotName
    public TimeSlot(String exhibitId, LocalDate date, String slotName, int capacity) {
        this.slotId = "SLOT" + (System.currentTimeMillis() % 10000);
        this.exhibitId = exhibitId;
        this.date = date;
        this.slotDate = (date != null) ? date.toString() : null;
        this.slotName = slotName;
        this.timeSlot = slotName;
        this.slotTime = slotName;
        this.capacity = capacity;
        this.availableTickets = capacity;
        this.status = "AVAILABLE";
    }

    // 5-Parameter Constructor (LocalDate date)
    public TimeSlot(String exhibitId, LocalDate date, LocalTime startTime, LocalTime endTime, int capacity) {
        this.slotId = "SLOT" + (System.currentTimeMillis() % 10000);
        this.exhibitId = exhibitId;
        this.date = date;
        this.slotDate = (date != null) ? date.toString() : null;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.availableTickets = capacity;
        this.slotName = (startTime != null && endTime != null) ? startTime + " - " + endTime : "09:00 AM - 11:00 AM";
        this.timeSlot = this.slotName;
        this.slotTime = this.slotName;
        this.status = "AVAILABLE";
    }

    // 5-Parameter Constructor Overload (String slotId first)
    public TimeSlot(String slotId, String exhibitId, LocalDate date, String slotName, int capacity) {
        this(exhibitId, date, slotName, capacity);
        this.slotId = slotId;
    }

    // Full Constructor
    public TimeSlot(String slotId, String exhibitId, LocalDate date, LocalTime startTime, LocalTime endTime, int capacity, int availableTickets) {
        this.slotId = slotId;
        this.exhibitId = exhibitId;
        this.date = date;
        this.slotDate = (date != null) ? date.toString() : null;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.availableTickets = availableTickets;
        this.slotName = (startTime != null && endTime != null) ? startTime + " - " + endTime : "09:00 AM - 11:00 AM";
        this.timeSlot = this.slotName;
        this.slotTime = this.slotName;
        this.status = availableTickets > 0 ? "AVAILABLE" : "FULL";
    }

    // Getters and Setters
    public String getSlotId() { return slotId; }
    public void setSlotId(String slotId) { this.slotId = slotId; }

    public String getExhibitId() { return exhibitId; }
    public void setExhibitId(String exhibitId) { this.exhibitId = exhibitId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) {
        this.date = date;
        if (date != null) this.slotDate = date.toString();
    }

    public String getSlotDate() { return slotDate; }
    public void setSlotDate(String slotDate) {
        this.slotDate = slotDate;
        try {
            this.date = LocalDate.parse(slotDate);
        } catch (Exception ignored) {}
    }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getAvailableTickets() { return availableTickets; }
    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
        if (availableTickets <= 0) {
            this.status = "FULL";
            this.available = false;
        } else {
            this.status = "AVAILABLE";
            this.available = true;
        }
    }

    public String getSlotName() { return slotName; }
    public void setSlotName(String slotName) {
        this.slotName = slotName;
        this.timeSlot = slotName;
        this.slotTime = slotName;
    }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
        this.slotName = timeSlot;
        this.slotTime = timeSlot;
    }

    public String getSlotTime() { return slotTime; }
    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
        this.timeSlot = slotTime;
        this.slotName = slotTime;
    }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) {
        this.available = available;
        this.status = available ? "AVAILABLE" : "FULL";
    }

    public String getStatus() {
        if (status != null) return status;
        return available ? "AVAILABLE" : "FULL";
    }
    public void setStatus(String status) {
        this.status = status;
        this.available = "AVAILABLE".equalsIgnoreCase(status);
    }

    public String getFormattedTimeRange() {
        if (startTime != null && endTime != null) {
            return startTime + " - " + endTime;
        }
        return slotName != null ? slotName : "09:00 AM - 11:00 AM";
    }

    public boolean bookTickets(int quantity) {
        if (quantity <= availableTickets) {
            setAvailableTickets(availableTickets - quantity);
            return true;
        }
        return false;
    }

    public int getBookedTickets() {
        return capacity - availableTickets;
    }

    public int getRemainingCapacity() {
        return availableTickets;
    }

    public String getDetailedAvailabilityInfo() {
        return "Available Tickets: " + availableTickets + "/" + capacity + " [" + getStatus() + "]";
    }

    @Override
    public String toString() {
        return slotName != null ? slotName : (startTime + " - " + endTime);
    }
}