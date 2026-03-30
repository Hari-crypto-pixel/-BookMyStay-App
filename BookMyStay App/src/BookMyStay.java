

import java.util.*;

// 🔷 Add-On Service Class
/**
 * Represents an optional service that can be added to a reservation.
 */
class AddOnService {
    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

// 🔷 Add-On Service Manager
/**
 * Manages mapping between reservation IDs and selected services.
 */
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    /**
     * Add a service to a reservation.
     */
    public void addService(String reservationId, AddOnService service) {
        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service " + service + " to " + reservationId);
    }

    /**
     * Get services for a reservation.
     */
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    /**
     * Calculate total cost of add-on services.
     */
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        for (AddOnService service : getServices(reservationId)) {
            total += service.getPrice();
        }

        return total;
    }

    /**
     * Display services for a reservation.
     */
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation: " + reservationId);

        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s);
        }

        System.out.println("Total Add-On Cost: $" + calculateTotalCost(reservationId));
    }
}

// 🔷 Main Application
/**
 * UseCase7AddOnServicesApp
 *
 * Demonstrates how optional services can be added to a reservation
 * without modifying core booking or inventory logic.
 *
 * This showcases extensibility using Map + List and composition.
 *
 * @author Deepika Ramireddy
 * @version 1.0
 */
public class BookMyStay  {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App ======");

        // Simulated reservation IDs (from previous booking system)
        String res1 = "SI1";
        String res2 = "SU2";

        // Initialize service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 10);
        AddOnService wifi = new AddOnService("WiFi", 5);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 20);

        // Add services to reservations
        manager.addService(res1, breakfast);
        manager.addService(res1, wifi);

        manager.addService(res2, airportPickup);

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}