package com.buscompany.model;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Domain model representing a bus route.
 * 
 * A route contains information about:
 * - Source and destination cities
 * - Departure and arrival times
 * - Available seats and ticket price
 */
public class Route {
    private int id;
    private String sourceCity;
    private String destinationCity;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private double price;

    public Route(int id, String sourceCity, String destinationCity, 
                 LocalTime departureTime, LocalTime arrivalTime,
                 int totalSeats, int availableSeats, double price) {
        this.id = id;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    /**
     * Calculates the duration of the route in minutes.
     */
    public long getDurationMinutes() {
        return java.time.temporal.ChronoUnit.MINUTES.between(departureTime, arrivalTime);
    }

    /**
     * Formats duration as HH:mm string.
     */
    public String getDurationString() {
        long minutes = getDurationMinutes();
        long hours = minutes / 60;
        long mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s -> %s at %s (%d seats)",
                sourceCity, destinationCity, departureTime, availableSeats);
    }
}