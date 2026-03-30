import java.util.*;

// 🔷 Reservation Class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return guestName + " -> " + roomType;
    }
}

// 🔷 Booking Queue (FIFO)
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // removes from queue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// 🔷 Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, getAvailability(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory: " + inventory);
    }
}

// 🔷 Booking Service (Core Logic)
/**
 * Handles room allocation safely using Set and HashMap.
 */
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs globally (uniqueness)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Track allocations per room type
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Process booking queue
     */
    public void processBookings(BookingQueue queue) {

        while (!queue.isEmpty()) {
            Reservation request = queue.getNextRequest();

            System.out.println("\nProcessing: " + request);

            String type = request.getRoomType();

            // 🔴 Check availability
            if (inventory.getAvailability(type) <= 0) {
                System.out.println("Booking failed: No rooms available for " + type);
                continue;
            }

            // 🔵 Generate unique room ID
            String roomId = generateRoomId(type);

            // 🟢 Ensure uniqueness using Set
            if (allocatedRoomIds.contains(roomId)) {
                System.out.println("Error: Duplicate Room ID detected!");
                continue;
            }

            // 🟣 Atomic Allocation
            allocatedRoomIds.add(roomId);

            roomAllocations
                    .computeIfAbsent(type, k -> new HashSet<>())
                    .add(roomId);

            inventory.decrement(type);

            // ✅ Confirm booking
            System.out.println("Booking Confirmed!");
            System.out.println("Guest: " + request.getGuestName());
            System.out.println("Room Type: " + type);
            System.out.println("Assigned Room ID: " + roomId);
        }
    }

    /**
     * Generate unique room ID
     */
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").substring(0, 2).toUpperCase() + roomCounter++;
    }

    /**
     * Display allocations
     */
    public void displayAllocations() {
        System.out.println("\n------ Room Allocations ------");
        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " -> " + roomAllocations.get(type));
        }
    }
}

// 🔷 Main Application
/**
 * UseCase6BookingAllocationApp
 *
 * Demonstrates safe booking allocation using Queue, HashMap, and Set.
 * Prevents double booking and ensures inventory consistency.
 *
 * @author Deepika Ramireddy
 * @version 1.0
 */
public class  BookMyStay  {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App ======");

        // Initialize components
        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // Process bookings
        service.processBookings(queue);

        // Show results
        service.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely.");
    }
}