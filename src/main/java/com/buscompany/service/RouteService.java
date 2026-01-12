package com.buscompany.service;

import com.buscompany.model.Route;
import com.buscompany.repository.RouteRepository;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Service layer providing business logic for route management.
 * 
 * TODO: Implement sorting using Java Streams (REQUIREMENT)
 * TODO: Implement booking logic
 * TODO: Implement Observer pattern for multi-window updates (BONUS)
 */
public class RouteService {
    private final RouteRepository repository;
    // TODO: Add observer list for Observer pattern

    public RouteService() {
        this.repository = new RouteRepository();
        this.repository.initializeDatabase();
    }

    /**
     * TODO: Get all routes sorted by source city and departure time
     * IMPORTANT: Must use Java Streams for sorting!
     */
    public List<Route> getAllRoutesSorted() {
        // TODO: Implement using streams
        return repository.getAllRoutes();
    }

    /**
     * TODO: Get routes for selected source and destination
     */
    public ObservableList<Route> getRoutesBySourceAndDestination(String source, String destination) {
        // TODO: Implement filtering and convert to ObservableList
        return null;
    }

    /**
     * TODO: Get all source cities
     */
    public ObservableList<String> getAllSourceCities() {
        // TODO: Implement city retrieval
        return null;
    }

    /**
     * TODO: Get destination cities for a source
     */
    public ObservableList<String> getDestinationCitiesForSource(String sourceCity) {
        // TODO: Implement filtered cities
        return null;
    }

    /**
     * TODO: Book tickets for a route
     * TODO: Validate seat availability
     * TODO: Update database
     * TODO: Notify observers (if implementing bonus)
     */
    public boolean bookTickets(Route route, int ticketCount) {
        // TODO: Implement booking with validation
        return false;
    }

    // TODO: Add observer interface
    // TODO: Add observer management methods (addObserver, removeObserver, notifyObservers)
}