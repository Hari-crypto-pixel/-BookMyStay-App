import java.util.*;

// 🔷 Reservation Class
/**
 * Represents a guest's booking request.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return guestName + " -> " + roomType;
    }
}

// 🔷 Booking Request Queue
/**
 * Manages incoming booking requests using FIFO principle.
 */
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    /**
     * Add booking request to queue.
     */
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    /**
     * View all queued requests (without removal).
     */
    public void displayQueue() {
        System.out.println("\n------ Booking Request Queue ------");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            System.out.println(r);
        }
    }

    /**
     * Peek next request (FIFO order).
     */
    public Reservation peekNext() {
        return queue.peek();
    }
}

// 🔷 Main Application
/**
 * UseCase5BookingQueueApp
 *
 * Demonstrates how booking requests are collected and ordered
 * using a Queue to ensure fairness (FIFO).
 *
 * No inventory updates or room allocation occurs here.
 *
 * @author Deepika Ramireddy
 * @version 1.0
 */
public class  BookMyStay {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App ======");

        // Initialize queue
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate guest booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double Room"));

        // Display queue
        bookingQueue.displayQueue();

        // Peek next request
        System.out.println("\nNext request to process: " + bookingQueue.peekNext());

        System.out.println("\nRequests stored in FIFO order. No allocation done.");
    }
}