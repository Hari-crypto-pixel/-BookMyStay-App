/**
 * UseCase2HotelBookingApp
 *
 * This application demonstrates basic object-oriented design
 * using abstraction, inheritance, and polymorphism in a Hotel Booking System.
 *
 * It defines different room types and displays their static availability.
 * The focus is on domain modeling rather than data structures.
 *
 * @author Deepika Ramireddy
 * @version 1.0
 */

// 🔷 Abstract Class
/**
 * Abstract class representing a generic Room.
 * It defines common attributes and behavior shared by all room types.
 */
abstract class Room {
    private int beds;
    private double size;
    private double price;

    /**
     * Constructor to initialize room attributes.
     */
    public Room(int beds, double size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    /**
     * Displays room details.
     */
    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: $" + price);
    }

    /**
     * Abstract method to define room type.
     */
    public abstract String getRoomType();
}

// 🔷 Single Room
/**
 * Represents a Single Room type.
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 50);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

// 🔷 Double Room
/**
 * Represents a Double Room type.
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 90);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

// 🔷 Suite Room
/**
 * Represents a Suite Room type.
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 200);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

// 🔷 Main Application Class
public class BookMyStay {

    /**
     * Entry point of the application.
     */
    public static void main(String[] args) {

        System.out.println("====== Book My Stay App ======");
        System.out.println("Available Room Types:\n");

        // 🔹 Static Availability Variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // 🔹 Polymorphism: Using Room reference
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // 🔹 Display Details
        displayRoom(single, singleAvailability);
        displayRoom(doubleRoom, doubleAvailability);
        displayRoom(suite, suiteAvailability);

        System.out.println("\nApplication terminated.");
    }

    /**
     * Utility method to display room details and availability.
     */
    public static void displayRoom(Room room, int availability) {
        System.out.println("----------------------------");
        System.out.println("Room Type: " + room.getRoomType());
        room.displayDetails();
        System.out.println("Available Rooms: " + availability);
    }
}