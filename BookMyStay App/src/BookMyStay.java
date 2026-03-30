import java.util.*;

// 🔷 Reservation Class (Enhanced for history)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// 🔷 Booking History (Storage Layer)
/**
 * Stores confirmed reservations in insertion order.
 */
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    /**
     * Add confirmed reservation to history.
     */
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    /**
     * Retrieve all reservations (read-only usage expected).
     */
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(history); // defensive copy
    }
}

// 🔷 Reporting Service
/**
 * Generates reports from booking history.
 */
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    /**
     * Display all bookings (chronological order).
     */
    public void displayAllBookings() {
        System.out.println("\n------ Booking History ------");

        List<Reservation> list = history.getAllReservations();

        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : list) {
            System.out.println(r);
        }
    }

    /**
     * Generate summary report (count by room type).
     */
    public void generateSummaryReport() {
        System.out.println("\n------ Booking Summary Report ------");

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            summary.put(r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        for (String type : summary.keySet()) {
            System.out.println(type + " : " + summary.get(type) + " bookings");
        }
    }

    /**
     * Total bookings count
     */
    public void totalBookings() {
        int count = history.getAllReservations().size();
        System.out.println("\nTotal Bookings: " + count);
    }
}

// 🔷 Main Application
/**
 * UseCase8BookingHistoryApp
 *
 * Demonstrates historical tracking of confirmed bookings
 * and reporting without modifying stored data.
 *
 * Prepares system for persistence (DB/file) in future.
 *
 * @author Deepika Ramireddy
 * @version 1.0
 */
public class  BookMyStay {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App ======");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from allocation system)
        history.addReservation(new Reservation("SI1", "Alice", "Single Room"));
        history.addReservation(new Reservation("SI2", "Bob", "Single Room"));
        history.addReservation(new Reservation("SU3", "David", "Suite Room"));

        // Reporting service
        BookingReportService reportService = new BookingReportService(history);

        // Admin views data
        reportService.displayAllBookings();
        reportService.generateSummaryReport();
        reportService.totalBookings();

        System.out.println("\nReporting completed. No data modified.");
    }
}