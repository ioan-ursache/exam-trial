package com.buscompany.repository;

import com.buscompany.model.Route;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository layer for database access to Route entities.
 * 
 * Handles all CRUD operations and queries for routes:
 * - Connection management
 * - Database initialization
 * - Route retrieval and updates
 * 
 * Uses SQLite with JDBC driver.
 */
public class RouteRepository {
    private static final String DB_URL = "jdbc:sqlite:bus_booking.db";
    private static final String ROUTES_TABLE = "routes";

    /**
     * Initializes the database with tables and sample data on first run.
     */
    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Create routes table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + ROUTES_TABLE + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "source_city TEXT NOT NULL," +
                    "destination_city TEXT NOT NULL," +
                    "departure_time TEXT NOT NULL," +
                    "arrival_time TEXT NOT NULL," +
                    "total_seats INTEGER NOT NULL," +
                    "available_seats INTEGER NOT NULL," +
                    "price REAL NOT NULL" +
                    ")";
            
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }
            
            // Insert sample data if table is empty
            String checkSQL = "SELECT COUNT(*) FROM " + ROUTES_TABLE;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(checkSQL)) {
                if (rs.next() && rs.getInt(1) == 0) {
                    insertSampleData(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts sample route data for testing.
     */
    private void insertSampleData(Connection conn) {
        String insertSQL = "INSERT INTO " + ROUTES_TABLE + 
                " (source_city, destination_city, departure_time, arrival_time, total_seats, available_seats, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            // Sample routes
            insertRoute(pstmt, "Bucharest", "Brașov", "08:00", "10:30", 40, 40, 50.0);
            insertRoute(pstmt, "Bucharest", "Brașov", "14:00", "16:30", 40, 40, 50.0);
            insertRoute(pstmt, "Bucharest", "Cluj", "07:00", "12:00", 50, 50, 100.0);
            insertRoute(pstmt, "Bucharest", "Constanța", "09:00", "11:30", 45, 45, 65.0);
            insertRoute(pstmt, "Brașov", "Cluj", "10:00", "14:00", 35, 35, 75.0);
            insertRoute(pstmt, "Brașov", "Bucharest", "11:00", "13:30", 40, 40, 50.0);
            insertRoute(pstmt, "Cluj", "Bucharest", "08:00", "13:00", 50, 50, 100.0);
            insertRoute(pstmt, "Cluj", "Brașov", "15:00", "19:00", 35, 35, 75.0);
            insertRoute(pstmt, "Constanța", "Bucharest", "12:00", "14:30", 45, 45, 65.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRoute(PreparedStatement pstmt, String source, String dest,
                            String departure, String arrival, int total, int available, double price)
            throws SQLException {
        pstmt.setString(1, source);
        pstmt.setString(2, dest);
        pstmt.setString(3, departure);
        pstmt.setString(4, arrival);
        pstmt.setInt(5, total);
        pstmt.setInt(6, available);
        pstmt.setDouble(7, price);
        pstmt.executeUpdate();
    }

    /**
     * Retrieves all routes from the database.
     */
    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT * FROM " + ROUTES_TABLE;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                routes.add(mapResultSetToRoute(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return routes;
    }

    /**
     * Retrieves routes matching source and destination cities.
     */
    public List<Route> getRoutesBySourceAndDestination(String source, String destination) {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT * FROM " + ROUTES_TABLE + 
                " WHERE source_city = ? AND destination_city = ?" +
                " ORDER BY departure_time";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    routes.add(mapResultSetToRoute(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return routes;
    }

    /**
     * Retrieves a route by its ID.
     */
    public Route getRouteById(int id) {
        String query = "SELECT * FROM " + ROUTES_TABLE + " WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoute(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Updates the available seats for a route.
     */
    public void updateAvailableSeats(int routeId, int newAvailableSeats) {
        String update = "UPDATE " + ROUTES_TABLE + 
                " SET available_seats = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(update)) {
            
            pstmt.setInt(1, newAvailableSeats);
            pstmt.setInt(2, routeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all unique source cities.
     */
    public List<String> getAllSourceCities() {
        List<String> cities = new ArrayList<>();
        String query = "SELECT DISTINCT source_city FROM " + ROUTES_TABLE + 
                " ORDER BY source_city";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                cities.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cities;
    }

    /**
     * Gets all destination cities for a given source city.
     */
    public List<String> getDestinationCitiesForSource(String sourceCity) {
        List<String> cities = new ArrayList<>();
        String query = "SELECT DISTINCT destination_city FROM " + ROUTES_TABLE + 
                " WHERE source_city = ? ORDER BY destination_city";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, sourceCity);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cities.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cities;
    }

    /**
     * Maps a ResultSet row to a Route object.
     */
    private Route mapResultSetToRoute(ResultSet rs) throws SQLException {
        return new Route(
                rs.getInt("id"),
                rs.getString("source_city"),
                rs.getString("destination_city"),
                LocalTime.parse(rs.getString("departure_time")),
                LocalTime.parse(rs.getString("arrival_time")),
                rs.getInt("total_seats"),
                rs.getInt("available_seats"),
                rs.getDouble("price")
        );
    }
}