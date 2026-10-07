package museum.jdbc;

/**
 * CRUDDemo.java
 * Demonstrates CRUD operations with JDBC (Academic Demo)
 *
 * Demonstrates:
 * - CREATE (INSERT)
 * - READ (SELECT)
 * - UPDATE
 * - DELETE
 * - Prepared statements for SQL injection prevention
 * - Transaction management
 *
 * @author Sriram S
 * @version 1.0
 */
public class CRUDDemo {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("CRUD OPERATIONS WITH JDBC (ACADEMIC DEMO)");
        System.out.println("=".repeat(70) + "\n");

        demonstrateCreate();
        demonstrateRead();
        demonstrateUpdate();
        demonstrateDelete();
        demonstrateTransactions();

        System.out.println("\n✓ CRUD demonstration complete\n");
    }

    /**
     * Demonstrate CREATE (INSERT) operation
     */
    private static void demonstrateCreate() {
        System.out.println("1. CREATE (INSERT) OPERATION");
        System.out.println("-".repeat(70));

        System.out.println("\nInserting new exhibit:\n");
        System.out.println("String sql = \"INSERT INTO exhibits (name, fee, category) \" +");
        System.out.println("             \"VALUES (?, ?, ?)\";");
        System.out.println("");
        System.out.println("try (PreparedStatement pstmt = conn.prepareStatement(sql)) {");
        System.out.println("    pstmt.setString(1, \"Ancient India\");");
        System.out.println("    pstmt.setDouble(2, 150.0);");
        System.out.println("    pstmt.setString(3, \"History\");");
        System.out.println("    ");
        System.out.println("    int rowsAffected = pstmt.executeUpdate();");
        System.out.println("    System.out.println(\"Rows inserted: \" + rowsAffected);");
        System.out.println("}");

        System.out.println("\nBenefits of PreparedStatement:");
        System.out.println("  - Prevents SQL injection");
        System.out.println("  - Parameters safely escaped");
        System.out.println("  - Better performance (query reuse)");
        System.out.println("  - Cleaner code");

        System.out.println();
    }

    /**
     * Demonstrate READ (SELECT) operation
     */
    private static void demonstrateRead() {
        System.out.println("2. READ (SELECT) OPERATION");
        System.out.println("-".repeat(70));

        System.out.println("\nFetching exhibits:\n");
        System.out.println("String sql = \"SELECT id, name, fee FROM exhibits WHERE category = ?\";");
        System.out.println("");
        System.out.println("try (PreparedStatement pstmt = conn.prepareStatement(sql)) {");
        System.out.println("    pstmt.setString(1, \"History\");");
        System.out.println("    ");
        System.out.println("    try (ResultSet rs = pstmt.executeQuery()) {");
        System.out.println("        while (rs.next()) {");
        System.out.println("            String id = rs.getString(\"id\");");
        System.out.println("            String name = rs.getString(\"name\");");
        System.out.println("            double fee = rs.getDouble(\"fee\");");
        System.out.println("            ");
        System.out.println("            System.out.println(id + \": \" + name + \" ₹\" + fee);");
        System.out.println("        }");
        System.out.println("    }");
        System.out.println("}");

        System.out.println("\nResultSet methods:");
        System.out.println("  - next(): Move to next row");
        System.out.println("  - getString(column): Get String value");
        System.out.println("  - getInt(column): Get Integer value");
        System.out.println("  - getDouble(column): Get Double value");
        System.out.println("  - getDate(column): Get Date value");
        System.out.println("  - getBoolean(column): Get Boolean value");

        System.out.println();
    }

    /**
     * Demonstrate UPDATE operation
     */
    private static void demonstrateUpdate() {
        System.out.println("3. UPDATE OPERATION");
        System.out.println("-".repeat(70));

        System.out.println("\nUpdating exhibit fee:\n");
        System.out.println("String sql = \"UPDATE exhibits SET fee = ? WHERE id = ?\";");
        System.out.println("");
        System.out.println("try (PreparedStatement pstmt = conn.prepareStatement(sql)) {");
        System.out.println("    pstmt.setDouble(1, 175.0);  // New fee");
        System.out.println("    pstmt.setString(2, \"EXH001\");  // Exhibit ID");
        System.out.println("    ");
        System.out.println("    int rowsAffected = pstmt.executeUpdate();");
        System.out.println("    System.out.println(\"Rows updated: \" + rowsAffected);");
        System.out.println("}");

        System.out.println("\nUpdate best practices:");
        System.out.println("  - Always use WHERE clause (except full table updates)");
        System.out.println("  - Use PreparedStatement to prevent SQL injection");
        System.out.println("  - Check rowsAffected to verify update");

        System.out.println();
    }

    /**
     * Demonstrate DELETE operation
     */
    private static void demonstrateDelete() {
        System.out.println("4. DELETE OPERATION");
        System.out.println("-".repeat(70));

        System.out.println("\nDeleting an exhibit:\n");
        System.out.println("String sql = \"DELETE FROM exhibits WHERE id = ?\";");
        System.out.println("");
        System.out.println("try (PreparedStatement pstmt = conn.prepareStatement(sql)) {");
        System.out.println("    pstmt.setString(1, \"EXH001\");");
        System.out.println("    ");
        System.out.println("    int rowsAffected = pstmt.executeUpdate();");
        System.out.println("    if (rowsAffected == 0) {");
        System.out.println("        System.out.println(\"Exhibit not found\");");
        System.out.println("    }");
        System.out.println("}");

        System.out.println("\nDelete best practices:");
        System.out.println("  - Always use WHERE clause");
        System.out.println("  - Verify record exists before deleting");
        System.out.println("  - Consider soft deletes (mark as deleted)");
        System.out.println("  - Check referential integrity");

        System.out.println();
    }

    /**
     * Demonstrate transaction management
     */
    private static void demonstrateTransactions() {
        System.out.println("5. TRANSACTION MANAGEMENT");
        System.out.println("-".repeat(70));

        System.out.println("\nMultiple operations in transaction:\n");
        System.out.println("try {");
        System.out.println("    conn.setAutoCommit(false);  // Begin transaction");
        System.out.println("    ");
        System.out.println("    // Operation 1: Debit from account");
        System.out.println("    pstmt1.executeUpdate();");
        System.out.println("    ");
        System.out.println("    // Operation 2: Credit to account");
        System.out.println("    pstmt2.executeUpdate();");
        System.out.println("    ");
        System.out.println("    conn.commit();  // Commit if all succeed");
        System.out.println("} catch (SQLException e) {");
        System.out.println("    conn.rollback();  // Rollback if any fails");
        System.out.println("    throw e;");
        System.out.println("} finally {");
        System.out.println("    conn.setAutoCommit(true);  // Reset auto-commit");
        System.out.println("}");

        System.out.println("\nTransaction benefits (ACID):");
        System.out.println("  - Atomicity: All-or-nothing");
        System.out.println("  - Consistency: Database stays valid");
        System.out.println("  - Isolation: Concurrent transactions don't interfere");
        System.out.println("  - Durability: Changes persist");

        System.out.println();
    }
}