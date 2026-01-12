package com.buscompany;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the Bus Booking System JavaFX application.
 * 
 * This application initializes the JavaFX stage and loads the primary scene
 * from the FXML file. The application demonstrates a layered architecture:
 * - View Layer (FXML files and JavaFX nodes)
 * - Controller Layer (JavaFX controllers handling UI events)
 * - Service Layer (business logic)
 * - Repository Layer (database access)
 * 
 * Database: SQLite (automatically created on first run)
 * Dependencies: Managed via Maven (pom.xml)
 */
public class BusBookingApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main scene from FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-scene.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 700);
            
            primaryStage.setTitle("Bus Booking System");
            primaryStage.setScene(scene);
            primaryStage.setWidth(1000);
            primaryStage.setHeight(700);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}