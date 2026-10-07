package museum.repository;

import museum.model.Exhibit;
import museum.util.FileUtil;

import java.io.File;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExhibitRepository {

    private String filePath = "data/exhibits.txt";
    private final List<Exhibit> exhibits = new ArrayList<>();
    private static final String URL = "jdbc:mysql://localhost:3306/museum_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public ExhibitRepository() {
        loadFromFile();
    }

    public ExhibitRepository(String filePath) {
        if (filePath != null && !filePath.trim().isEmpty()) {
            this.filePath = filePath;
        }
        loadFromFile();
    }

    public synchronized List<Exhibit> findAll() {
        return new ArrayList<>(exhibits);
    }

    public synchronized List<Exhibit> getAllExhibits() {
        return findAll();
    }

    public synchronized List<Exhibit> getOpenExhibits() {
        return findAll();
    }

    public synchronized List<Exhibit> findByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return new ArrayList<>();
        String query = keyword.toLowerCase().trim();
        return exhibits.stream()
                .filter(e -> (e.getName() != null && e.getName().toLowerCase().contains(query)) ||
                        (e.getTitle() != null && e.getTitle().toLowerCase().contains(query)) ||
                        (e.getDescription() != null && e.getDescription().toLowerCase().contains(query)) ||
                        (e.getCategory() != null && e.getCategory().toLowerCase().contains(query)))
                .collect(Collectors.toList());
    }

    public synchronized List<Exhibit> findByCategory(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return new ArrayList<>();
        String query = keyword.toLowerCase().trim();
        return exhibits.stream()
                .filter(e -> e.getCategory() != null && e.getCategory().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public synchronized Exhibit findById(String id) {
        if (id == null || id.trim().isEmpty()) return null;
        for (Exhibit e : exhibits) {
            if ((e.getId() != null && e.getId().equalsIgnoreCase(id)) ||
                    (e.getExhibitId() != null && e.getExhibitId().equalsIgnoreCase(id))) {
                return e;
            }
        }
        return null;
    }

    public synchronized void save(Exhibit exhibit) {
        if (exhibit == null) return;
        exhibits.removeIf(e -> (e.getId() != null && e.getId().equalsIgnoreCase(exhibit.getId())) ||
                (e.getExhibitId() != null && e.getExhibitId().equalsIgnoreCase(exhibit.getExhibitId())));
        exhibits.add(exhibit);
        saveToFile();
    }

    public synchronized boolean update(Exhibit exhibit) {
        save(exhibit);
        return true;
    }

    public synchronized boolean delete(String id) {
        if (id == null) return false;
        boolean removed = exhibits.removeIf(e -> (e.getId() != null && e.getId().equalsIgnoreCase(id)) ||
                (e.getExhibitId() != null && e.getExhibitId().equalsIgnoreCase(id)));
        if (removed) saveToFile();
        return removed;
    }

    public synchronized int count() {
        return exhibits.size();
    }

    public synchronized void clear() {
        exhibits.clear();
        saveToFile();
    }

    private void loadFromFile() {
        if (!FileUtil.fileExists(filePath)) return;
        try {
            List<String> lines = FileUtil.readLines(filePath);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String title = parts[1].trim();
                    String desc = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    String cat = parts.length > 4 ? parts[4].trim() : "General";
                    String loc = parts.length > 5 ? parts[5].trim() : "Main Hall";
                    Exhibit exhibit = new Exhibit(id, title, desc, cat, price, loc);
                    exhibits.add(exhibit);
                }
            }
        } catch (Exception ignored) {}
    }

    private synchronized void saveToFile() {
        try {
            List<String> lines = new ArrayList<>();
            for (Exhibit e : exhibits) {
                lines.add(e.getId() + "," + e.getTitle() + "," + e.getDescription() + "," + e.getPrice() + "," + e.getCategory() + "," + e.getLocation());
            }
            FileUtil.writeLines(filePath, lines);
        } catch (Exception ignored) {}
    }

    // Helper: Matches search keyword against all exhibit properties
    private boolean matchesKeyword(Exhibit e, String query) {
        String id = extractId(e).toLowerCase();
        String name = extractName(e).toLowerCase();
        String desc = extractDescription(e).toLowerCase();
        String loc = extractLocation(e).toLowerCase();

        return id.contains(query) || name.contains(query) || desc.contains(query) || loc.contains(query);
    }

    // Helper: Converts raw DB numeric IDs (e.g. "1") to formatted string IDs ("EXH1001")
    private String formatExhibitId(String rawId) {
        if (rawId == null || rawId.trim().isEmpty()) return "EXH1001";
        if (rawId.toUpperCase().startsWith("EXH")) return rawId.toUpperCase();
        try {
            int num = Integer.parseInt(rawId.replaceAll("\\D+", ""));
            return "EXH" + (1000 + num);
        } catch (Exception e) {
            return rawId;
        }
    }

    private String extractId(Exhibit e) {
        return extractProp(e, "getId", "id", "getExhibitId");
    }

    private String extractName(Exhibit e) {
        return extractProp(e, "getName", "name", "getExhibitName");
    }

    private String extractDescription(Exhibit e) {
        return extractProp(e, "getDescription", "description", "getDesc");
    }

    private String extractLocation(Exhibit e) {
        return extractProp(e, "getLocation", "location", "getLoc");
    }

    private String extractProp(Exhibit e, String... methodNames) {
        if (e == null) return "";
        for (String mName : methodNames) {
            try {
                Method m = e.getClass().getMethod(mName);
                Object res = m.invoke(e);
                return res != null ? String.valueOf(res) : "";
            } catch (Exception ignored) {}
        }
        for (String mName : methodNames) {
            try {
                Field f = e.getClass().getDeclaredField(mName.replace("get", "").toLowerCase());
                f.setAccessible(true);
                Object res = f.get(e);
                return res != null ? String.valueOf(res) : "";
            } catch (Exception ignored) {}
        }
        return "";
    }

    private Exhibit createExhibitInstance(String id, String name, String description, String location) {
        for (Constructor<?> c : Exhibit.class.getConstructors()) {
            Class<?>[] types = c.getParameterTypes();
            try {
                if (types.length == 4) {
                    Object p0 = (types[0] == int.class || types[0] == Integer.class) ? parseSafeInt(id) : id;
                    Object p1 = name;
                    Object p2 = description;
                    Object p3 = location;
                    return (Exhibit) c.newInstance(p0, p1, p2, p3);
                }
            } catch (Exception ignored) {}
        }

        try {
            Constructor<?> defaultCtor = Exhibit.class.getDeclaredConstructor();
            defaultCtor.setAccessible(true);
            Exhibit instance = (Exhibit) defaultCtor.newInstance();
            trySetField(instance, "setId", id);
            trySetField(instance, "setName", name);
            trySetField(instance, "setDescription", description);
            trySetField(instance, "setLocation", location);
            return instance;
        } catch (Exception ignored) {}

        return null;
    }

    private static int parseSafeInt(String val) {
        try {
            return Integer.parseInt(val.replaceAll("\\D+", ""));
        } catch (Exception e) {
            return 1;
        }
    }

    private void trySetField(Object obj, String methodName, String val) {
        try {
            Method m = obj.getClass().getMethod(methodName, String.class);
            m.invoke(obj, val);
        } catch (Exception e1) {
            try {
                int intVal = parseSafeInt(val);
                Method m = obj.getClass().getMethod(methodName, int.class);
                m.invoke(obj, intVal);
            } catch (Exception ignored) {}
        }
    }
}