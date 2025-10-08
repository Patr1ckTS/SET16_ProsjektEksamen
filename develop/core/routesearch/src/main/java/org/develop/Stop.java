package org.develop;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;

public class Stop {
    private String stopId;
    private String location;
    private String name;
    private String avgangstid; 
    private ArrayList<String> avgangstider;
    private int tidEtterAvgang;

    // Eksisterende konstruktør for stopp med relativ tid
    public Stop(String stopId, String location, String name, int tidEtterAvgang) {
        this.stopId = stopId;
        this.location = location;
        this.name = name;
        this.tidEtterAvgang = tidEtterAvgang;
        this.avgangstider = null;
    }

    // Ny konstruktør for stopp med liste av avgangstider
    public Stop(String stopId, String location, String name, ArrayList<String> avgangstider) {
        this.stopId = stopId;
        this.location = location;
        this.name = name;
        this.avgangstider = avgangstider;
        this.tidEtterAvgang = 0; 
    }
    
    public String beregnTransportPåStopp(String avgangstidFraTerminal) {
        try {
            LocalTime terminal = LocalTime.parse(avgangstidFraTerminal);
            LocalTime transportPåStopp = terminal.plusMinutes(this.tidEtterAvgang);
            return transportPåStopp.toString();
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    public static Stop finnStoppMedNavn(ArrayList<Stop> stops, String navn) {
        for (Stop stop : stops) {
            if (stop.getName().equalsIgnoreCase(navn)) {
                return stop;
            }
        }
        return null;
    }
    
    // Hjelpemetode for å finne neste avgangstid fra dette stoppet
    public String finnNesteAvgangstid(String ønsketAvreisetid) {
        if (this.avgangstider == null || this.avgangstider.isEmpty()) {
            return null;
        }
        
        try {
            for (String avgangstid : this.avgangstider) {
                // avgangstid + tidEtterAvgang = transportPåStopp
                String transportPåStopp = this.beregnTransportPåStopp(avgangstid);

                if (transportPåStopp != null) {
                    if (transportPåStopp.compareTo(ønsketAvreisetid) >= 0) {
                        return avgangstid; 
                    }
                }
            }
            return null;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    // Hjelpemetode for å beregne reisetid mellom to stopp
    public int beregnReisetid(String avgangstid, Stop sluttStopp) {
        try {
            LocalTime reiseStart = LocalTime.parse(this.beregnTransportPåStopp(avgangstid));
            LocalTime reiseSlutt = LocalTime.parse(sluttStopp.beregnTransportPåStopp(avgangstid));
            
            // reiseslutt - reisestart = reisetid
            return (int) java.time.Duration.between(reiseStart, reiseSlutt).toMinutes();
            
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String toString() {
        return String.format("Stop{stopId='%s', name='%s', location='%s', tidEtterAvgang=%d}", 
                           stopId, name, location, tidEtterAvgang);
    }
    
    public String toDetailedString() {
        if (avgangstider != null) {
            return String.format("Terminal Stop: %s (%s) - Avgangstider: %s", 
                               name, location, avgangstider);
        } else {
            return String.format("Regular Stop: %s (%s) - %d minutter etter terminal", 
                               name, location, tidEtterAvgang);
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

    public int getTidEtterAvgang() {
        return tidEtterAvgang;
    }

    public void setTidEtterAvgang(int tidEtterAvgang) {
        this.tidEtterAvgang = tidEtterAvgang;
    }

    public List<String> getAvgangstider() {
        return avgangstider;
    }

    public void setAvgangstider(ArrayList<String> avgangstider) {
        this.avgangstider = avgangstider;
    }

    public String getAvgangstid() {
        return avgangstid;
    }

    public void setAvgangstid(String avgangstid) {
        this.avgangstid = avgangstid;
    }
    
}