package com.buscompany.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for database operations.
 * 
 * Manages SQLite database connections and provides
 * utility methods for database management.
 */
public class DatabaseUtils {
    private static final String DB_URL = "jdbc:sqlite:bus_booking.db";

    /**
     * Gets a new database connection.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Ensures the SQLite JDBC driver is loaded.
     */
    public static void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found");
            e.printStackTrace();
        }
    }
}