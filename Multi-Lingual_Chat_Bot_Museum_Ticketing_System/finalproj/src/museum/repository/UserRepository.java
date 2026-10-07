package museum.repository;

import museum.model.User;
import museum.model.Visitor;
import museum.model.Admin;
import museum.util.FileUtil;
import museum.generic.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserRepository.java
 * Data access layer for User entities
 *
 * Demonstrates:
 * - Implementing Repository<T> interface
 * - Collections: HashMap for fast lookup, ArrayList for iteration
 * - File I/O for persistence
 * - Encapsulation of data access logic
 *
 * Unit 5: Collections Framework (HashMap, ArrayList)
 * Unit 3: File I/O
 *
 * @author Your Name
 * @version 1.0
 */
public class UserRepository implements Repository<User> {

    /**
     * In-memory storage: HashMap for fast O(1) lookup by email
     * Demonstrates: HashMap for key-value pair storage
     */
    private Map<String, User> users = new HashMap<>();

    /**
     * File path for persistence
     */
    private String filePath;

    /**
     * Constructor
     * @param filePath the file path for persistence
     */
    public UserRepository(String filePath) throws IOException {
        this.filePath = filePath;
        loadFromFile(); // Load existing data on startup
    }

    /**
     * Save a user to in-memory storage
     *
     * Demonstrates:
     * - HashMap.put() for storing entities
     * - Encapsulation of storage logic
     *
     * @param user the user to save
     * @return the saved user
     */
    @Override
    public User save(User user) {
        if (user != null) {
            users.put(user.getEmail(), user);
            try {
                saveToFile(); // Persist to file
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    /**
     * Find user by email (ID)
     *
     * Demonstrates:
     * - HashMap.get() for O(1) lookup
     *
     * @param email the user's email
     * @return the user if found, null otherwise
     */
    @Override
    public User findById(String email) {
        return users.get(email);
    }

    /**
     * Get all users
     *
     * Demonstrates:
     * - HashMap.values() to get all values
     * - ArrayList for iteration and return
     *
     * @return list of all users
     */
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Update a user
     *
     * Demonstrates:
     * - Checking if entity exists
     * - Updating in HashMap
     *
     * @param user the user to update
     * @return true if update successful
     */
    @Override
    public boolean update(User user) {
        if (user != null && users.containsKey(user.getEmail())) {
            users.put(user.getEmail(), user);
            try {
                saveToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    /**
     * Delete a user by email
     *
     * Demonstrates:
     * - HashMap.remove() for deletion
     *
     * @param email the user's email
     * @return true if deletion successful
     */
    @Override
    public boolean delete(String email) throws IOException {
        if (users.containsKey(email)) {
            users.remove(email);
            saveToFile();
            return true;
        }
        return false;
    }

    /**
     * Check if user exists
     *
     * Demonstrates:
     * - HashMap.containsKey() for existence check
     *
     * @param email the user's email
     * @return true if user exists
     */
    @Override
    public boolean exists(String email) {
        return users.containsKey(email);
    }

    /**
     * Get total count of users
     *
     * Demonstrates:
     * - HashMap.size() for count
     *
     * @return number of users
     */
    @Override
    public int count() {
        return users.size();
    }

    /**
     * Find user by email (convenience method)
     * @param email the email to search for
     * @return user if found, null otherwise
     */
    public User findByEmail(String email) {
        return users.get(email);
    }

    /**
     * Find visitor by email
     * @param email the visitor's email
     * @return visitor if found and is a Visitor, null otherwise
     */
    public Visitor findVisitorByEmail(String email) {
        User user = users.get(email);
        if (user instanceof Visitor) {
            return (Visitor) user;
        }
        return null;
    }

    /**
     * Find admin by email
     * @param email the admin's email
     * @return admin if found and is an Admin, null otherwise
     */
    public Admin findAdminByEmail(String email) {
        User user = users.get(email);
        if (user instanceof Admin) {
            return (Admin) user;
        }
        return null;
    }

    /**
     * Get all visitors
     *
     * Demonstrates:
     * - Filtering collection using enhanced for loop
     * - instanceof operator for type checking
     *
     * @return list of all visitors
     */
    public List<Visitor> findAllVisitors() {
        List<Visitor> visitors = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Visitor) {
                visitors.add((Visitor) user);
            }
        }
        return visitors;
    }

    /**
     * Get all admins
     *
     * Demonstrates:
     * - Filtering collection
     *
     * @return list of all admins
     */
    public List<Admin> findAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Admin) {
                admins.add((Admin) user);
            }
        }
        return admins;
    }

    /**
     * Load users from file
     *
     * Demonstrates:
     * - File I/O reading (FileUtil.readLines)
     * - Parsing comma-separated data
     * - Creating objects from file data
     *
     * Unit 3: File reading
     */
    private void loadFromFile() throws IOException {
        if (!FileUtil.fileExists(filePath)) {
            return; // File doesn't exist yet
        }

        List<String> lines = FileUtil.readLines(filePath);
        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) continue;

            // Format:
            // Visitor: email,name,password,VISITOR,date,isActive,phone,address,language
            // Admin:   email,name,password,ADMIN,date,isActive,adminId,department,permissionLevel
            String[] parts = line.split(",", -1);
            if (parts.length < 6) continue;

            try {
                String email = parts[0].trim();
                String name = parts[1].trim();
                String password = parts[2];
                String userType = parts[3].trim();

                User user;
                if ("ADMIN".equalsIgnoreCase(userType)) {
                    String department = parts.length > 7 ? parts[7].trim() : "Operations";
                    String permission = parts.length > 8 ? parts[8].trim() : "ADMIN";
                    user = new Admin(name, email, password, department, permission);
                } else {
                    String phone = parts.length > 6 ? parts[6].trim() : "";
                    String address = parts.length > 7 ? parts[7].trim() : "";
                    Visitor visitor = new Visitor(email, name, password, phone, address);
                    if (parts.length > 8) {
                        try {
                            visitor.setPreferredLanguage(museum.model.Language.getByDisplayName(parts[8].trim()));
                        } catch (Exception ignored) {
                            // Keep English if the stored language is invalid.
                        }
                    }
                    user = visitor;
                }

                users.put(email, user);
            } catch (RuntimeException ignored) {
                // Ignore malformed records and continue loading other users.
            }
        }
    }

    /**
     * Save users to file
     *
     * Demonstrates:
     * - File I/O writing (FileUtil.writeLines)
     * - Converting objects to file format
     *
     * Unit 3: File writing
     */
    private void saveToFile() throws IOException {
        List<String> lines = new ArrayList<>();
        for (User user : users.values()) {
            lines.add(user.toFileFormat());
        }
        FileUtil.writeLines(filePath, lines);
    }

    /**
     * Clear all users (for testing)
     */
    public void clear() {
        users.clear();
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}