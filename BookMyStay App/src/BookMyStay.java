import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3RoomInventoryApp
 *
 * This application demonstrates centralized inventory management
 * using a HashMap to maintain room availability in a Hotel Booking System.
 *
 * It replaces scattered variables with a single source of truth,
 * improving consistency, scalability, and maintainability.
 *
 * The RoomInventory class encapsulates all inventory-related logic.
 *
 * @author Deepika Ramireddy
 * @version 1.0
 */

// 🔷 Inventory Management Class
/**
 * RoomInventory is responsible for managing room availability.
 * It uses a HashMap to store and update room counts.
 */
class RoomInventory {

    // HashMap to store room type -> availability
    private Map<String, Integer> inventory;

    /**
     * Constructor initializes room availability.
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room types with counts
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    /**
     * Get availability for a specific room type.
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability for a room type.
     */
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    /**
     * Book a room (decrease count safely).
     */
    public void bookRoom(String roomType) {
        int current = getAvailability(roomType);

        if (current > 0) {
            inventory.put(roomType, current - 1);
            System.out.println(roomType + " booked successfully.");
        } else {
            System.out.println("No availability for " + roomType);
        }
    }

    /**
     * Display full inventory.
     */
    public void displayInventory() {
        System.out.println("\n------ Current Room Inventory ------");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// 🔷 Main Application
public class BookMyStay{

    /**
     * Entry point of the application.
     */
    public static void main(String[] args) {

        System.out.println("====== Book My Stay App ======");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Simulate booking
        System.out.println("\nBooking a Single Room...");
        inventory.bookRoom("Single Room");

        // Display updated inventory
        inventory.displayInventory();

        // Manual update
        System.out.println("\nUpdating Suite Room availability...");
        inventory.updateAvailability("Suite Room", 5);

        // Final state
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}