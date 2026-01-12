package com.buscompany.controller;

import com.buscompany.service.RouteService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for client booking window.
 * 
 * TODO: Handle source city selection
 * TODO: Dynamically update destination cities
 * TODO: Display available routes
 * TODO: Handle ticket booking
 * TODO: Calculate total price
 * TODO: Implement observer pattern for real-time updates (BONUS)
 */
public class ClientWindowController {
    @FXML private ComboBox sourceCityComboBox;
    @FXML private ComboBox destinationCityComboBox;
    @FXML private ListView availableRoutesListView;
    @FXML private Spinner<Integer> ticketCountSpinner;
    @FXML private Label totalPriceLabel;
    @FXML private Button bookButton;

    private RouteService routeService;

    @FXML
    public void initialize() {
        // TODO: Configure spinner (1-50 tickets)
        // TODO: Add listeners for price calculation
        // TODO: Add listeners for route selection
    }

    public void setRouteService(RouteService routeService) {
        // TODO: Store route service
        // TODO: Load source cities
    }

    @FXML
    private void onSourceCitySelected() {
        // TODO: Load destination cities for selected source
        // TODO: Clear route list and details
    }

    @FXML
    private void onDestinationCitySelected() {
        // TODO: Load available routes for selected cities
        // TODO: Set up list cell factory for route display
    }

    @FXML
    private void onBookTickets() {
        // TODO: Validate route selection
        // TODO: Get ticket count from spinner
        // TODO: Call service.bookTickets()
        // TODO: Handle success (show alert, refresh list)
        // TODO: Handle failure (show error alert with reason)
    }

    private void updateTotalPrice() {
        // TODO: Calculate total = ticket count * route price
        // TODO: Update label
    }
}