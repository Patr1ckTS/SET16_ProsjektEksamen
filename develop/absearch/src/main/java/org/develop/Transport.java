package org.develop;

import java.time.LocalTime;
import org.develop.Interface.LocationHandling;
import java.time.Duration;

public class Transport implements LocationHandling { // Legg til 'public' her
    String transportId;
    int capacity;
    String startLocation;
    String endLocation;
    String departureTime;
    String arrivalTime;

    public Transport(String transportId, int capacity, String startLocation, String endLocation, String departureTime, String arrivalTime) {
        this.transportId = transportId;
        this.capacity = capacity;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public double calculateDistance() {
        // TODO: Implementer logikk
        return 0;
    }

    @Override
    public double estimateTravelTime() {
        try {
            LocalTime departure = LocalTime.parse(departureTime);
            LocalTime arrival = LocalTime.parse(arrivalTime);
            
            Duration duration = Duration.between(departure, arrival);
            
            // Håndter reiser som går over midnatt
            if (duration.isNegative()) {
                duration = duration.plusDays(1);
            }
            
            // Returner tid i minutter
            return duration.toMinutes();
        } catch (Exception e) {
            // Fallback hvis tidsformat er feil
            return calculateDistance() * 2; // Antar 2 min per distanseenhet
        }
    }

    // Getters and Setters
    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    
}