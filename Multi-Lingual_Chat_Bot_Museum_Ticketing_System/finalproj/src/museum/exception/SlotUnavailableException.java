package museum.exception;

/**
 * SlotUnavailableException.java
 * Exception thrown when a time slot has no available capacity
 *
 * Demonstrates:
 * - Custom exception for booking business logic
 * - Prevents overbooking
 *
 * @author Sriram S
 * @version 1.0
 */
public class SlotUnavailableException extends MuseumException {

    /**
     * Slot ID that is full
     */
    private String slotId;

    /**
     * Constructor with message
     * @param message the error message
     */
    public SlotUnavailableException(String message) {
        super(message);
    }

    /**
     * Constructor with message and slot ID
     * @param message the error message
     * @param slotId the slot that is unavailable
     */
    public SlotUnavailableException(String message, String slotId) {
        super(message);
        this.slotId = slotId;
    }

    /**
     * Get slot ID
     * @return the slot ID
     */
    public String getSlotId() {
        return slotId;
    }
}