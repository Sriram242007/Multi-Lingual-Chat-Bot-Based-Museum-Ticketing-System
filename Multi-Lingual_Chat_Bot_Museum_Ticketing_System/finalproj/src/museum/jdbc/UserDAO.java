package museum.jdbc;

import java.util.*;

/**
 * UserDAO.java
 * Data Access Object for User entities (Academic Demo)
 *
 * Demonstrates:
 * - DAO pattern
 * - Encapsulating database operations
 * - CRUD methods for User
 * - SQL query building
 * - PreparedStatement usage
 *
 * @author Sriram S
 * @version 1.0
 */
public class UserDAO {

    // Note: In real application, would use database connection
    // For this demo, we just show the structure

    private static final String TABLE_NAME = "users";
    private static final String[] COLUMNS = {"id", "email", "name", "password", "user_type"};

    /**
     * Create user in database
     *
     * Demonstrates:
     * - INSERT operation
     * - Encapsulation of SQL
     */
    public void create(String email, String name, String password, String userType) {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (email, name, password, user_type) VALUES (?, ?, ?, ?)";

        System.out.println("\nCreate User DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameters:");
        System.out.println("  email: " + email);
        System.out.println("  name: " + name);
        System.out.println("  password: [hidden]");
        System.out.println("  userType: " + userType);
    }

    /**
     * Read user from database
     *
     * Demonstrates:
     * - SELECT operation
     * - ResultSet processing
     */
    public void read(String email) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        System.out.println("\nRead User DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameter: email = " + email);
        System.out.println("\nExpected ResultSet:");
        System.out.println("  id | email | name | password | user_type");
    }

    /**
     * Update user in database
     *
     * Demonstrates:
     * - UPDATE operation
     */
    public void update(String email, String name, String newPassword) {
        String sql = "UPDATE " + TABLE_NAME +
                " SET name = ?, password = ? WHERE email = ?";

        System.out.println("\nUpdate User DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameters:");
        System.out.println("  name: " + name);
        System.out.println("  password: [hidden]");
        System.out.println("  email: " + email);
    }

    /**
     * Delete user from database
     *
     * Demonstrates:
     * - DELETE operation
     */
    public void delete(String email) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE email = ?";

        System.out.println("\nDelete User DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameter: email = " + email);
    }

    /**
     * Get all users
     *
     * Demonstrates:
     * - SELECT all with filtering
     */
    public void findAll(String userType) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_type = ?";

        System.out.println("\nFindAll Users DAO:\n");
        System.out.println("SQL: " + sql);
        System.out.println("\nParameter: userType = " + userType);
        System.out.println("\nReturns: List<User> of all users with given type");
    }

    /**
     * DAO Pattern Benefits
     */
    public static void printDAOBenefits() {
        System.out.println("\nDAO PATTERN BENEFITS:");
        System.out.println("-".repeat(70));
        System.out.println("\n1. Separation of Concerns");
        System.out.println("   - Database code isolated in DAO");
        System.out.println("   - Business logic separate");
        System.out.println("   - Presentation layer independent");

        System.out.println("\n2. Reusability");
        System.out.println("   - DAO methods used throughout application");
        System.out.println("   - DRY principle (Don't Repeat Yourself)");

        System.out.println("\n3. Maintainability");
        System.out.println("   - SQL changes only in DAO");
        System.out.println("   - Easy to find and fix database logic");

        System.out.println("\n4. Testability");
        System.out.println("   - Can mock DAO for unit testing");
        System.out.println("   - Database independent tests");

        System.out.println("\n5. Database Independence");
        System.out.println("   - Easy to switch databases");
        System.out.println("   - DAO abstracts database details");
    }
}