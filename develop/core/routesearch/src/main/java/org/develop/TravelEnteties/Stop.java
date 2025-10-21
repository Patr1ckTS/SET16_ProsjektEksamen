package org.develop.TravelEnteties;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stop {
    private String stopId;
    private String location;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String departureTime;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArrayList<String> departureTimes;
    private int minutesAfterDeparture;
    

    //Json konstruktør for Jackson
    public Stop() {}

    // Eksisterende konstruktør for stopp med relativ tid
    public Stop(String stopId, String location, String name, int minutesAfterDeparture) {
        this.stopId = stopId;
        this.location = location;
        this.name = name;
        this.minutesAfterDeparture = minutesAfterDeparture;
        this.departureTimes = null;
    }

    // Ny konstruktør for stopp med liste av avgangstider
    public Stop(String stopId, String location, String name, ArrayList<String> departureTimes) {
        this.stopId = stopId;
        this.location = location;
        this.name = name;
        this.departureTimes = departureTimes;
        this.minutesAfterDeparture = 0; 
    }
    


    @Override
    public String toString() {
        return String.format("Stop{stopId='%s', name='%s', location='%s', minutesAfterDeparture=%d}", 
                           stopId, name, location, minutesAfterDeparture);
    }
    
    public String toDetailedString() {
        if (departureTimes != null) {
            return String.format("Terminal Stop: %s (%s) - Departure Times: %s", 
                               name, location, departureTimes);
        } else {
            return String.format("Regular Stop: %s (%s) - %d minutes after terminal", 
                               name, location, minutesAfterDeparture);
        }
    }


    // Getters and setters
    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinutesAfterDeparture() {
        return minutesAfterDeparture;
    }

    public void setMinutesAfterDeparture(int minutesAfterDeparture) {
        this.minutesAfterDeparture = minutesAfterDeparture;
    }

    public List<String> getDepartureTimes() {
        return departureTimes;
    }

    public void setDepartureTimes(ArrayList<String> departureTimes) {
        this.departureTimes = departureTimes;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    
}