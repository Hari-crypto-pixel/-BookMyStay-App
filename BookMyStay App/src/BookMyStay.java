import java.util.*;

// 🔷 Abstract Room Class
abstract class Room {
    private int beds;
    private double size;
    private double price;

    public Room(int beds, double size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: $" + price);
    }

    public abstract String getRoomType();
}

// 🔷 Room Implementations
class SingleRoom extends Room {
    public SingleRoom() { super(1, 200, 50); }
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 350, 90); }
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(3, 600, 200); }
    public String getRoomType() { return "Suite Room"; }
}

// 🔷 Inventory (Read + Write handled separately)
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // intentionally unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Set<String> getAllRoomTypes() {
        return inventory.keySet();
    }
}

// 🔷 Search Service (Read-Only)
class RoomSearchService {

    private RoomInventory inventory;
    private Map<String, Room> roomCatalog;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;

        // Room domain objects (catalog)
        roomCatalog = new HashMap<>();
        roomCatalog.put("Single Room", new SingleRoom());
        roomCatalog.put("Double Room", new DoubleRoom());
        roomCatalog.put("Suite Room", new SuiteRoom());
    }

    /**
     * Displays only available rooms without modifying inventory.
     */
    public void searchAvailableRooms() {
        System.out.println("\n------ Available Rooms ------");

        for (String type : inventory.getAllRoomTypes()) {
            int available = inventory.getAvailability(type);

            // 🔴 Validation: Skip unavailable rooms
            if (available <= 0) continue;

            Room room = roomCatalog.get(type);

            System.out.println("\nRoom Type: " + type);
            room.displayDetails();
            System.out.println("Available: " + available);
        }
    }
}

// 🔷 Main Application
/**
 * UseCase4RoomSearchApp
 *
 * Demonstrates read-only search functionality in a Hotel Booking System.
 * Ensures safe data access and prevents unintended state modification.
 *
 * @author Deepika Ramireddy
 * @version 1.0
 */
public class   BookMyStay {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App ======");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search (read-only)
        searchService.searchAvailableRooms();

        System.out.println("\nSearch completed. No changes made to inventory.");
    }
}