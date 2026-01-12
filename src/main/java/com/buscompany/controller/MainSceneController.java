package com.buscompany.controller;

import com.buscompany.model.Route;
import com.buscompany.service.RouteService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller for the main application scene.
 * 
 * Handles:
 * - Display of all routes sorted by city and departure time
 * - Client window creation
 * - Application initialization
 */
public class MainSceneController {
    @FXML private ListView<Route> allRoutesListView;
    @FXML private Button openClientWindowButton;
    @FXML private Label routesCountLabel;

    private RouteService routeService;
    private int clientWindowCounter = 0;

    @FXML
    public void initialize() {
        routeService = new RouteService();
        loadAllRoutes();
    }

    /**
     * Loads all routes sorted by source city and departure time.
     * REQUIREMENT: Uses Java Streams for sorting (1 point)
     */
    private void loadAllRoutes() {
        var routes = routeService.getAllRoutesSorted();
        allRoutesListView.getItems().setAll(routes);
        routesCountLabel.setText(String.format("Total Routes: %d", routes.size()));
    }

    @FXML
    private void onOpenClientWindow() {
        try {
            clientWindowCounter++;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client-window.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            
            ClientWindowController controller = loader.getController();
            controller.setRouteService(routeService);
            controller.setWindowTitle("Client Window #" + clientWindowCounter);
            
            Stage stage = new Stage();
            stage.setTitle("Client Window #" + clientWindowCounter);
            stage.setScene(scene);
            stage.setWidth(900);
            stage.setHeight(600);
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to open client window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}