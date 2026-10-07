package museum.model;

/**
 * Admin.java
 * Represents a museum administrator (extends User)
 *
 * Demonstrates:
 * - Inheritance (extends User)
 * - Constructor chaining (super())
 * - Concrete implementation of abstract methods
 * - Admin-specific functionality
 * - Access control and permissions
 *
 * @author Your Name
 * @version 1.0
 * @since Java 24
 */
public class Admin extends User {

    // ==================== PRIVATE FIELDS ====================

    /**
     * Unique admin ID
     */
    private String adminId;

    /**
     * Department the admin belongs to
     */
    private String department;

    /**
     * Permissions level (SUPER_ADMIN, ADMIN, MANAGER)
     */
    private String permissionLevel;

    /**
     * Whether admin is currently logged in
     */
    private boolean isLoggedIn;

    // ==================== STATIC COUNTER ====================

    /**
     * Static counter for generating unique admin IDs
     */
    private static int adminCounter = 1000;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor for Admin
     * Auto-generates admin ID
     *
     * @param name admin's name
     * @param email admin's email
     * @param password admin's password
     * @param department admin's department
     * @param permissionLevel admin's permission level
     */
    public Admin(String name, String email, String password,
                 String department, String permissionLevel) {
        super(email, name, password, "ADMIN");
        this.adminId = "ADM" + (++adminCounter);
        this.department = department;
        this.permissionLevel = permissionLevel;
        this.isLoggedIn = false;
    }

    /**
     * Minimal constructor for Admin
     *
     * @param name admin's name
     * @param email admin's email
     * @param password admin's password
     */
    public Admin(String name, String email, String password) {
        super(email, name, password, "ADMIN");
        this.adminId = "ADM" + (++adminCounter);
        this.department = "Operations";
        this.permissionLevel = "ADMIN";
        this.isLoggedIn = false;
    }

    // ==================== IMPLEMENT ABSTRACT METHODS ====================

    @Override
    public void login() {
        if (isAccountActive()) {
            this.isLoggedIn = true;
        }
    }

    @Override
    public void logout() {
        this.isLoggedIn = false;
    }

    @Override
    public String getUserRole() {
        return "ADMIN (" + permissionLevel + ") - Can manage exhibits, bookings, and analytics";
    }

    // ==================== ADMIN-SPECIFIC METHODS ====================

    /**
     * Check if admin has required permission
     *
     * @param requiredPermission permission to check
     * @return true if admin has permission
     */
    public boolean hasPermission(String requiredPermission) {
        if (permissionLevel.equals("SUPER_ADMIN")) {
            return true;
        }

        return switch (requiredPermission) {
            case "MANAGE_EXHIBITS" ->
                    permissionLevel.equals("ADMIN") || permissionLevel.equals("MANAGER");
            case "MANAGE_BOOKINGS" ->
                    permissionLevel.equals("ADMIN") || permissionLevel.equals("MANAGER");
            case "VIEW_ANALYTICS" ->
                    permissionLevel.equals("ADMIN");
            case "MANAGE_ADMINS" ->
                    permissionLevel.equals("SUPER_ADMIN");
            default -> false;
        };
    }

    /**
     * Check if admin is logged in
     * @return login status
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Verify admin credentials (email and password)
     *
     * @param email email to verify
     * @param password password to verify
     * @return true if credentials match
     */
    public boolean verifyCredentials(String email, String password) {
        return getEmail().equals(email) && verifyPassword(password);
    }

    /**
     * Convert admin to file format for persistence
     * @return comma-separated admin data
     */
    public String toFileFormat() {
        return super.toFileFormat() + "," + adminId + "," + department + "," + permissionLevel;
    }

    /**
     * Override toString
     * @return admin information
     */
    @Override
    public String toString() {
        return String.format("Admin{adminId='%s', name='%s', email='%s', department='%s', permissionLevel='%s'}",
                adminId, getName(), getEmail(), department, permissionLevel);
    }

    // ==================== GETTERS ====================

    public String getAdminId() { return adminId; }
    public String getDepartment() { return department; }
    public String getPermissionLevel() { return permissionLevel; }

    // ==================== SETTERS ====================

    public void setDepartment(String department) {
        if (department != null && !department.trim().isEmpty()) {
            this.department = department;
        }
    }

    public void setPermissionLevel(String permissionLevel) {
        if (permissionLevel != null && !permissionLevel.trim().isEmpty()) {
            this.permissionLevel = permissionLevel;
        }
    }
}