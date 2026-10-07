package museum.interfaces;

/**
 * Reportable.java
 * Interface defining reporting capability
 *
 * Demonstrates:
 * - Defining reporting contract
 * - Any class can implement reporting
 *
 * @author Sriram S
 * @version 1.0
 */
public interface Reportable {

    /**
     * Generate a report
     *
     * @return formatted report string
     */
    String generateReport();

    /**
     * Get total count of items
     *
     * @return count
     */
    int getTotal();
}