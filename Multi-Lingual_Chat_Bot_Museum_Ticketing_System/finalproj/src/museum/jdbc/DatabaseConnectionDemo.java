package museum.jdbc;

/**
 * DatabaseConnectionDemo.java
 * Demonstrates JDBC connection setup (Academic Demo Only)
 *
 * NOTE: This is academic demonstration. Requires:
 * - MySQL/PostgreSQL installed
 * - JDBC driver JAR in classpath
 * - Database and tables created
 *
 * Demonstrates:
 * - Loading JDBC driver
 * - Creating connection
 * - Connection lifecycle
 * - Error handling
 * - Resource cleanup (try-with-resources)
 *
 * @author Sriram S
 * @version 1.0
 */
public class DatabaseConnectionDemo {

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/museum_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "200724";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("JDBC DATABASE CONNECTION DEMONSTRATION");
        System.out.println("=".repeat(70) + "\n");

        System.out.println("NOTE: This is academic demonstration of JDBC concepts.");
        System.out.println("To actually run:\n");
        System.out.println("1. Install MySQL: CREATE DATABASE museum_db;");
        System.out.println("2. Add JDBC driver JAR to classpath");
        System.out.println("3. Create tables (see below)");
        System.out.println("4. Run this demonstration\n");

        demonstrateConnectionSetup();
        demonstrateJDBCWorkflow();
        demonstrateResourceManagement();

        System.out.println("\n✓ JDBC demonstration complete\n");
    }

    /**
     * Demonstrate JDBC connection setup
     *
     * Demonstrates:
     * - Loading JDBC driver
     * - Establishing connection
     * - Connection properties
     */
    private static void demonstrateConnectionSetup() {
        System.out.println("1. JDBC CONNECTION SETUP");
        System.out.println("-".repeat(70));

        System.out.println("\nStep 1: Load JDBC Driver");
        System.out.println("  Code: Class.forName(\"com.mysql.cj.jdbc.Driver\");");
        System.out.println("  Purpose: Register driver with DriverManager");
        System.out.println("  Note: Often not needed in modern Java (auto-loading)");

        System.out.println("\nStep 2: Establish Connection");
        System.out.println("  Code: DriverManager.getConnection(url, user, password);");
        System.out.println("  URL: jdbc:mysql://localhost:3306/museum_db");
        System.out.println("  User: root");
        System.out.println("  Password: (password)");

        System.out.println("\nStep 3: Use Connection");
        System.out.println("  Create Statement or PreparedStatement");
        System.out.println("  Execute queries");
        System.out.println("  Process results");

        System.out.println("\nStep 4: Close Connection");
        System.out.println("  Code: connection.close();");
        System.out.println("  Or use try-with-resources (automatic)");

        System.out.println();
    }

    /**
     * Demonstrate JDBC workflow
     *
     * Demonstrates:
     * - Step-by-step JDBC operations
     * - SQL execution flow
     */
    private static void demonstrateJDBCWorkflow() {
        System.out.println("2. JDBC WORKFLOW");
        System.out.println("-".repeat(70));

        System.out.println("\nTypical JDBC workflow:\n");

        System.out.println("1. Load Driver");
        System.out.println("   Class.forName(JDBC_DRIVER);");

        System.out.println("\n2. Get Connection");
        System.out.println("   Connection conn = DriverManager.getConnection(");
        System.out.println("       DB_URL, DB_USER, DB_PASSWORD");
        System.out.println("   );");

        System.out.println("\n3. Create Statement");
        System.out.println("   Statement stmt = conn.createStatement();");
        System.out.println("   // OR PreparedStatement for better security");

        System.out.println("\n4. Execute Query");
        System.out.println("   ResultSet rs = stmt.executeQuery(SQL);");
        System.out.println("   // Use executeUpdate() for INSERT/UPDATE/DELETE");

        System.out.println("\n5. Process Results");
        System.out.println("   while (rs.next()) {");
        System.out.println("       String name = rs.getString(\"name\");");
        System.out.println("       double price = rs.getDouble(\"price\");");
        System.out.println("   }");

        System.out.println("\n6. Close Resources");
        System.out.println("   rs.close();");
        System.out.println("   stmt.close();");
        System.out.println("   conn.close();");

        System.out.println();
    }

    /**
     * Demonstrate resource management
     *
     * Demonstrates:
     * - Try-with-resources for automatic cleanup
     * - Best practices
     */
    private static void demonstrateResourceManagement() {
        System.out.println("3. RESOURCE MANAGEMENT (Try-With-Resources)");
        System.out.println("-".repeat(70));

        System.out.println("\nBEST PRACTICE: Use try-with-resources\n");

        System.out.println("try (");
        System.out.println("    Connection conn = DriverManager.getConnection(");
        System.out.println("        DB_URL, DB_USER, DB_PASSWORD");
        System.out.println("    );");
        System.out.println("    Statement stmt = conn.createStatement();");
        System.out.println("    ResultSet rs = stmt.executeQuery(SQL)");
        System.out.println(") {");
        System.out.println("    // Process results");
        System.out.println("    while (rs.next()) {");
        System.out.println("        // Use data");
        System.out.println("    }");
        System.out.println("} catch (SQLException e) {");
        System.out.println("    e.printStackTrace();");
        System.out.println("}");
        System.out.println("// Resources automatically closed");

        System.out.println("\nBenefits:");
        System.out.println("  - Automatic resource cleanup");
        System.out.println("  - No need for explicit close()");
        System.out.println("  - Prevents connection leaks");
        System.out.println("  - Cleaner code");
        System.out.println("  - Exception-safe");

        System.out.println();
    }
}