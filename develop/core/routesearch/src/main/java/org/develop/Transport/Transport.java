package org.develop.Transport;

import java.time.LocalTime;
import org.develop.Interface.LocationHandling;
import java.time.Duration;

public class Transport implements LocationHandling { 
    String transportId;
    String ruteId;
    String ruteNavn;
    String transportType;
    String startLocation;
    String endLocation;
    String departureTime;
    String arrivalTime;

    public Transport(String transportId, String transportType, String startLocation, String endLocation, String departureTime, String arrivalTime) {
        this.transportId = transportId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Transport(String transportId, String transportType) {
        this.transportId = transportId;
        this.transportType = transportType;
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
            return duration.toMinutes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public String getRuteId() {
        return ruteId;
    }

    public void setRuteId(String ruteId) {
        this.ruteId = ruteId;
    }

    public String getRuteNavn() {
        return ruteNavn;
    }

    public void setRuteNavn(String ruteNavn) {
        this.ruteNavn = ruteNavn;
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

       // Getters and Setters
   
}