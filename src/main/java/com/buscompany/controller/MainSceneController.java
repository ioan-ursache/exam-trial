package com.buscompany.controller;

import com.buscompany.service.RouteService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for the main application scene.
 * 
 * TODO: Handle display of all routes sorted by city and departure time
 * TODO: Handle client window creation
 */
public class MainSceneController {
    @FXML private ListView allRoutesListView;
    @FXML private Button openClientWindowButton;
    @FXML private Label routesCountLabel;

    private RouteService routeService;

    @FXML
    public void initialize() {
        // TODO: Initialize RouteService
        // TODO: Load and display all routes
    }

    @FXML
    private void onOpenClientWindow() {
        // TODO: Create new client window stage
        // TODO: Load client-window.fxml
        // TODO: Set up controller and service
        // TODO: Show window
    }
}