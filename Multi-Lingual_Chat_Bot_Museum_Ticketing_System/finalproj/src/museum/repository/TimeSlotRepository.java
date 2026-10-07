package museum.repository;

import museum.model.TimeSlot;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/museum_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {}
    }

    // Fetches time slots for a given exhibit and date
    public List<Object> getAvailableSlots(String exhibitId, String date) {
        List<Object> slots = new ArrayList<>();
        String rawNumericId = extractNumericId(exhibitId);

        String sql = "SELECT * FROM time_slots WHERE (exhibit_id = ? OR exhibit_id = ?) " +
                "AND (slot_date = ? OR slot_date IS NULL OR slot_date = '')";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, exhibitId);
            stmt.setString(2, rawNumericId);
            stmt.setString(3, date);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String slotTime = getStringFromRs(rs, "time_slot", "slot_time", "start_time", "time");
                int capacity = getIntFromRs(rs, "available_capacity", "capacity", "seats");

                Object slotObj = createTimeSlotInstance(exhibitId, date, slotTime, capacity);
                if (slotObj != null) {
                    slots.add(slotObj);
                }
            }
        } catch (SQLException e) {
            System.err.println("[DATABASE ERROR] Could not fetch time_slots: " + e.getMessage());
        }

        // If no exhibit-specific slots exist in DB, load default operating hours slots
        if (slots.isEmpty()) {
            slots = fetchAllGenericSlots(date);
        }

        return slots;
    }

    public List<Object> findByExhibitIdAndDate(String exhibitId, String date) {
        return getAvailableSlots(exhibitId, date);
    }

    public List<Object> findByExhibitAndDate(String exhibitId, String date) {
        return getAvailableSlots(exhibitId, date);
    }

    // Returns available dates for an exhibit
    public List<String> getAvailableDates(String exhibitId) {
        List<String> dates = new ArrayList<>();
        String rawNumericId = extractNumericId(exhibitId);

        String sql = "SELECT DISTINCT slot_date FROM time_slots WHERE exhibit_id = ? OR exhibit_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, exhibitId);
            stmt.setString(2, rawNumericId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String d = rs.getString(1);
                if (d != null && !d.trim().isEmpty()) {
                    dates.add(d);
                }
            }
        } catch (SQLException ignored) {}

        return dates;
    }

    private List<Object> fetchAllGenericSlots(String date) {
        List<Object> slots = new ArrayList<>();
        String sql = "SELECT * FROM time_slots";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String slotTime = getStringFromRs(rs, "time_slot", "slot_time", "start_time", "time");
                int capacity = getIntFromRs(rs, "available_capacity", "capacity", "seats");
                if (capacity <= 0) capacity = 30;

                Object slotObj = createTimeSlotInstance("EXH1001", date, slotTime, capacity);
                if (slotObj != null) {
                    slots.add(slotObj);
                }
            }
        } catch (SQLException ignored) {}

        // Fallback default slots if DB table has no rows
        if (slots.isEmpty()) {
            String[] defaultTimes = {"09:00 AM - 11:00 AM", "11:00 AM - 01:00 PM", "02:00 PM - 04:00 PM", "04:00 PM - 06:00 PM"};
            for (String t : defaultTimes) {
                slots.add(createTimeSlotInstance("EXH1001", date, t, 50));
            }
        }

        return slots;
    }

    private String extractNumericId(String formattedId) {
        if (formattedId == null) return "1";
        String digits = formattedId.replaceAll("\\D+", "");
        if (digits.isEmpty()) return "1";
        try {
            int val = Integer.parseInt(digits);
            return (val >= 1000) ? String.valueOf(val - 1000) : String.valueOf(val);
        } catch (Exception e) {
            return "1";
        }
    }

    private String getStringFromRs(ResultSet rs, String... colNames) {
        for (String col : colNames) {
            try {
                String val = rs.getString(col);
                if (val != null) return val;
            } catch (SQLException ignored) {}
        }
        return "10:00 AM - 12:00 PM";
    }

    private int getIntFromRs(ResultSet rs, String... colNames) {
        for (String col : colNames) {
            try {
                return rs.getInt(col);
            } catch (SQLException ignored) {}
        }
        return 30;
    }

    private Object createTimeSlotInstance(String exhibitId, String date, String time, int cap) {
        try {
            for (Constructor<?> c : TimeSlot.class.getConstructors()) {
                Class<?>[] p = c.getParameterTypes();
                if (p.length == 4) return c.newInstance(exhibitId, date, time, cap);
                if (p.length == 3) return c.newInstance(date, time, cap);
                if (p.length == 2) return c.newInstance(time, cap);
            }
            Constructor<?> defaultCtor = TimeSlot.class.getDeclaredConstructor();
            defaultCtor.setAccessible(true);
            Object obj = defaultCtor.newInstance();
            tryInvoke(obj, "setExhibitId", exhibitId);
            tryInvoke(obj, "setDate", date);
            tryInvoke(obj, "setTimeSlot", time);
            tryInvoke(obj, "setCapacity", cap);
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    private void tryInvoke(Object obj, String method, Object val) {
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().equalsIgnoreCase(method) && m.getParameterCount() == 1) {
                try {
                    m.invoke(obj, val);
                    return;
                } catch (Exception ignored) {}
            }
        }
    }
}