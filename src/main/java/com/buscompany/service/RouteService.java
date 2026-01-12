package com.buscompany.service;

import com.buscompany.model.Route;
import com.buscompany.repository.RouteRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer providing business logic for route management.
 * 
 * This layer:
 * - Handles sorting using Java Streams (requirement: use streams for sorting)
 * - Manages booking logic and seat availability
 * - Coordinates between Controller and Repository layers
 * - Provides observable lists for JavaFX UI updates
 */
public class RouteService {
    private final RouteRepository repository;
    private final ObservableList<BookingObserver> observers = FXCollections.observableArrayList();

    public RouteService() {
        this.repository = new RouteRepository();
        this.repository.initializeDatabase();
    }

    /**
     * Gets all routes sorted by source city and departure time using Java Streams.
     * REQUIREMENT: Must use Java Streams for sorting (1 point)
     */
    public List<Route> getAllRoutesSorted() {
        return repository.getAllRoutes().stream()
                .sorted((r1, r2) -> {
                    // First sort by source city
                    int sourceCityComparison = r1.getSourceCity().compareTo(r2.getSourceCity());
                    if (sourceCityComparison != 0) {
                        return sourceCityComparison;
                    }
                    // Then sort by departure time
                    return r1.getDepartureTime().compareTo(r2.getDepartureTime());
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets routes for selected source and destination, sorted by departure time.
     */
    public ObservableList<Route> getRoutesBySourceAndDestination(String source, String destination) {
        List<Route> routes = repository.getRoutesBySourceAndDestination(source, destination);
        return FXCollections.observableArrayList(routes);
    }

    /**
     * Gets all source cities.
     */
    public ObservableList<String> getAllSourceCities() {
        List<String> cities = repository.getAllSourceCities();
        return FXCollections.observableArrayList(cities);
    }

    /**
     * Gets destination cities for a given source city.
     */
    public ObservableList<String> getDestinationCitiesForSource(String sourceCity) {
        List<String> cities = repository.getDestinationCitiesForSource(sourceCity);
        return FXCollections.observableArrayList(cities);
    }

    /**
     * Books tickets for a route and updates available seats.
     * Notifies all observers of the booking.
     */
    public boolean bookTickets(Route route, int ticketCount) {
        if (route.getAvailableSeats() < ticketCount) {
            return false; // Not enough seats
        }
        
        // Update available seats
        int newAvailableSeats = route.getAvailableSeats() - ticketCount;
        route.setAvailableSeats(newAvailableSeats);
        repository.updateAvailableSeats(route.getId(), newAvailableSeats);
        
        // Notify observers of the booking
        notifyObservers(route, ticketCount);
        
        return true;
    }

    /**
     * Registers a booking observer (Observer design pattern for bonus).
     */
    public void addObserver(BookingObserver observer) {
        observers.add(observer);
    }

    /**
     * Unregisters a booking observer.
     */
    public void removeObserver(BookingObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of a booking event.
     */
    private void notifyObservers(Route route, int ticketCount) {
        for (BookingObserver observer : observers) {
            observer.onBookingMade(route, ticketCount);
        }
    }

    /**
     * Observer interface for booking events.
     * Used for real-time updates across multiple client windows.
     */
    public interface BookingObserver {
        void onBookingMade(Route route, int ticketCount);
    }
}