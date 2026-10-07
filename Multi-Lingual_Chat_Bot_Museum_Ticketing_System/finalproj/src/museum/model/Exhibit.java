package museum.model;

import java.util.ArrayList;
import java.util.List;

public class Exhibit {

    private String id;
    private String exhibitId;
    private String name;
    private String title;
    private String description;
    private String category;
    private double price;
    private double pricePerTicket;
    private String location;
    private int capacity = 100;
    private List<TimeSlot> timeSlots = new ArrayList<>();

    public Exhibit() {}

    public Exhibit(String id, String title, String description, String category, double price, String location) {
        this.id = id;
        this.exhibitId = id;
        this.title = title;
        this.name = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.pricePerTicket = price;
        this.location = location;
    }

    // 5-Parameter Constructor: (name, description, category, price, location) — auto-generates id
    public Exhibit(String name, String description, String category, double price, String location) {
        this("EXH" + (System.currentTimeMillis() % 10000), name, description, category, price, location);
    }

    public Exhibit(String id, String title, String description, double price) {
        this(id, title, description, "General", price, "Main Hall");
    }

    // Getters and Setters with aliases for all test classes
    public String getId() { return id != null ? id : exhibitId; }
    public void setId(String id) { this.id = id; this.exhibitId = id; }

    public String getExhibitId() { return exhibitId != null ? exhibitId : id; }
    public void setExhibitId(String exhibitId) { this.exhibitId = exhibitId; this.id = exhibitId; }

    public String getName() { return name != null ? name : title; }
    public void setName(String name) { this.name = name; this.title = name; }

    public String getTitle() { return title != null ? title : name; }
    public void setTitle(String title) { this.title = title; this.name = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; this.pricePerTicket = price; }

    public double getPricePerTicket() { return pricePerTicket > 0 ? pricePerTicket : price; }
    public void setPricePerTicket(double pricePerTicket) { this.pricePerTicket = pricePerTicket; this.price = pricePerTicket; }

    /** Alias for getPrice() — used by TestPhase2 and TestPhase7 */
    public double getEntryFee() { return getPrice(); }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    // TimeSlot management methods
    public void addTimeSlot(TimeSlot slot) {
        if (slot != null) {
            timeSlots.add(slot);
        }
    }

    public List<TimeSlot> getTimeSlots() {
        return new ArrayList<>(timeSlots);
    }

    public int getTotalTimeSlots() {
        return timeSlots.size();
    }

    public int getTotalCapacity() {
        return timeSlots.stream().mapToInt(TimeSlot::getCapacity).sum();
    }

    @Override
    public String toString() {
        return "Exhibit{" + "id='" + getId() + '\'' + ", name='" + getName() + '\'' + '}';
    }
}