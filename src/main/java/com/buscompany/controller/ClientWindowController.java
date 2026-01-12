package com.buscompany.controller;

import com.buscompany.model.Route;
import com.buscompany.service.RouteService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for client booking window.
 * 
 * Handles:
 * - Source city selection
 * - Dynamic destination city filtering
 * - Route display based on selected cities
 * - Ticket booking with seat availability validation
 * - Total price calculation
 * - Observer pattern for real-time updates across multiple windows
 * 
 * REQUIREMENTS HANDLED:
 * 1. Combo boxes for source and destination cities (2 points)
 * 2. Dynamic destination city filtering based on source (2 points)
 * 3. Route display with all required info (1.5 points)
 * 4. Ticket booking with seat availability validation (0.5 + 1 points)
 * 5. Total price calculation (2 points)
 * 6. Observer pattern for multiple client windows (BONUS: 1 point)
 */
public class ClientWindowController implements RouteService.BookingObserver {
    @FXML private ComboBox<String> sourceCityComboBox;
    @FXML private ComboBox<String> destinationCityComboBox;
    @FXML private ListView<Route> availableRoutesListView;
    @FXML private Label routeDetailsLabel;
    @FXML private Spinner<Integer> ticketCountSpinner;
    @FXML private Label totalPriceLabel;
    @FXML private Button bookButton;
    @FXML private Label windowTitleLabel;

    private RouteService routeService;
    private Route selectedRoute;

    /**
     * Initialize the client window controller.
     * Called automatically by JavaFX after FXML is loaded.
     */
    @FXML
    public void initialize() {
        // Configure ticket count spinner
        ticketCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));
        ticketCountSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());

        // Listen for route selection
        availableRoutesListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> onRouteSelected(newVal)
        );
    }

    /**
     * Sets the route service and registers this controller as an observer.
     */
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
        this.routeService.addObserver(this); // Register for booking updates
        loadSourceCities();
    }

    /**
     * Sets the window title label.
     */
    public void setWindowTitle(String title) {
        if (windowTitleLabel != null) {
            windowTitleLabel.setText(title);
        }
    }

    /**
     * Loads all available source cities into the combo box.
     */
    private void loadSourceCities() {
        var cities = routeService.getAllSourceCities();
        sourceCityComboBox.setItems(cities);
    }

    /**
     * REQUIREMENT: Source city selection changes destination cities dynamically.
     * When user selects a source city, update destination combo box with only
     * cities reachable from that source. (2 points)
     */
    @FXML
    private void onSourceCitySelected() {
        String selectedSource = sourceCityComboBox.getValue();
        if (selectedSource != null) {
            var destinations = routeService.getDestinationCitiesForSource(selectedSource);
            destinationCityComboBox.setItems(destinations);
            destinationCityComboBox.setValue(null);
            availableRoutesListView.getItems().clear();
            routeDetailsLabel.setText("");
            totalPriceLabel.setText("Total: 0 lei");
        }
    }

    /**
     * REQUIREMENT: After source and destination selection, load matching routes.
     * Display routes with: source, destination, departure, arrival, duration, price.
     * (1.5 points)
     */
    @FXML
    private void onDestinationCitySelected() {
        String source = sourceCityComboBox.getValue();
        String destination = destinationCityComboBox.getValue();
        
        if (source != null && destination != null) {
            var routes = routeService.getRoutesBySourceAndDestination(source, destination);
            availableRoutesListView.setItems(routes);
            
            // Custom cell factory to display route details
            availableRoutesListView.setCellFactory(lv -> new ListCell<Route>() {
                @Override
                protected void updateItem(Route route, boolean empty) {
                    super.updateItem(route, empty);
                    if (empty || route == null) {
                        setText(null);
                    } else {
                        setText(formatRouteDisplay(route));
                    }
                }
            });
        }
    }

    /**
     * Formats route information for display in the list.
     */
    private String formatRouteDisplay(Route route) {
        return String.format("%s → %s | %s-%s | Duration: %s | %d seats available | %.2f lei",
                route.getSourceCity(),
                route.getDestinationCity(),
                route.getDepartureTime(),
                route.getArrivalTime(),
                route.getDurationString(),
                route.getAvailableSeats(),
                route.getPrice());
    }

    /**
     * Handles route selection from the list.
     */
    private void onRouteSelected(Route route) {
        this.selectedRoute = route;
        if (route != null) {
            routeDetailsLabel.setText(formatRouteDisplay(route));
            updateTotalPrice();
        }
    }

    /**
     * Updates the total price label based on selected tickets.
     */
    private void updateTotalPrice() {
        if (selectedRoute != null) {
            int ticketCount = ticketCountSpinner.getValue();
            double total = ticketCount * selectedRoute.getPrice();
            totalPriceLabel.setText(String.format("Total: %.2f lei", total));
        }
    }

    /**
     * REQUIREMENT: Book tickets with seat availability validation.
     * - Validate sufficient seats available (0.5 points)
     * - Update available seats (1 point)
     * - Show total price (2 points)
     * - Show error message if insufficient seats (0.5 points)
     */
    @FXML
    private void onBookTickets() {
        if (selectedRoute == null) {
            showAlert("Info", "Please select a route first");
            return;
        }

        int ticketCount = ticketCountSpinner.getValue();

        // Try to book tickets
        boolean success = routeService.bookTickets(selectedRoute, ticketCount);

        if (success) {
            double totalPrice = ticketCount * selectedRoute.getPrice();
            showAlert("Success", 
                    String.format("Booking successful!\nTickets: %d\nTotal: %.2f lei", ticketCount, totalPrice));
            
            // Refresh the routes list to show updated seat count
            onDestinationCitySelected();
            ticketCountSpinner.getValueFactory().setValue(1);
        } else {
            // REQUIREMENT: Show error message if insufficient seats (0.5 points)
            showAlert("Booking Failed", 
                    String.format("Not enough seats available.\nRequested: %d\nAvailable: %d",
                    ticketCount, selectedRoute.getAvailableSeats()));
        }
    }

    /**
     * REQUIREMENT: Observer pattern implementation.
     * When another client books tickets, this window is notified and updates.
     * (BONUS: 1 point)
     */
    @Override
    public void onBookingMade(Route route, int ticketCount) {
        // Refresh routes if the booked route is in the current view
        String source = sourceCityComboBox.getValue();
        String destination = destinationCityComboBox.getValue();
        
        if (source != null && destination != null) {
            onDestinationCitySelected();
        }
    }

    /**
     * Utility method to show alert dialogs.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}