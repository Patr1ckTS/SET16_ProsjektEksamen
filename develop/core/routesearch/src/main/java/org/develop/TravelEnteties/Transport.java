package org.develop.TravelEnteties;

import java.time.LocalTime;
import java.time.Duration;

// Denne kan gjøres abstrakt om vi skal ha flere transportmidler og da definere egne klasser for de
    // F.eks. Buss, Tog, T-bane, Trikk, Ferge osv.
// For MVP så holder det med en generell Transport klasse, siden fokuset ikke er på de ulike transportmidlene, men på logikken og data håndteringen
public class Transport  { 
    private String transportId;
    private String routeId;
    private String routeName;
    private String transportType;
    private String startLocation;
    private String endLocation;
    private String departureTime;
    private String arrivalTime;

    //Json konstruktør for Jackson
    public Transport() {}

    public Transport(String transportId, String transportType, String startLocation, String endLocation, String departureTime, String arrivalTime) {
        this.transportId = transportId;
        this.transportType = transportType;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Transport(String transportId, String transportType) {
        this.transportId = transportId;
        this.transportType = transportType;
    }

    public double estimateTravelTime() {
        try {
            LocalTime departure = LocalTime.parse(departureTime);
            LocalTime arrival = LocalTime.parse(arrivalTime);
            
            Duration duration = Duration.between(departure, arrival);
            
            // Håndter reiser som går over midnatt
            if (duration.isNegative()) {
                duration = duration.plusDays(1);
                    
            }
            return duration.toMinutes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    
    // Getters og Setters
    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
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