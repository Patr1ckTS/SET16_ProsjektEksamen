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
        this.tidEtterAvgang = 0; // Startpunkt har 0 minutter etter avgang
    }

    public String beregnBussPåStopp(String avgangstidFraTerminal) {
        try {
            LocalTime terminal = LocalTime.parse(avgangstidFraTerminal);
            LocalTime bussPåStopp = terminal.plusMinutes(this.tidEtterAvgang);
            return bussPåStopp.toString();
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    public String finnNesteBuss(String avreisetid, Stop terminalStopp) {
        if (terminalStopp.getAvgangstider() == null) {
            return null;
        }
        
        try {
            for (String avgangstid : terminalStopp.getAvgangstider()) {
                // avgangstid + tidEtterAvgang = bussPåStopp
                String bussPåStopp = this.beregnBussPåStopp(avgangstid);
                
                if (bussPåStopp != null) {
                    // bussPåStopp compare avreisetid (>=0 betyr bussen kommer etter ønsket tid)
                    if (bussPåStopp.compareTo(avreisetid) >= 0) {
                        return avgangstid; // Returner avgangstid fra terminal
                    }
                }
            }
            return null; // Ingen buss funnet
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public int beregnReisetid(String avgangstid, Stop sluttStopp) {
        try {
            LocalTime reiseStart = LocalTime.parse(this.beregnBussPåStopp(avgangstid));
            LocalTime reiseSlutt = LocalTime.parse(sluttStopp.beregnBussPåStopp(avgangstid));
            
            // reiseslutt - reisestart = reisetid
            return (int) java.time.Duration.between(reiseStart, reiseSlutt).toMinutes();
            
        } catch (Exception e) {
            return -1;
        }
    }

    public static Stop finnStoppMedNavn(ArrayList<Stop> stops, String navn) {
        for (Stop stop : stops) {
            if (stop.getName().equalsIgnoreCase(navn)) {
                return stop;
            }
        }
        return null; // Returnerer null hvis stoppet ikke finnes
    }

    public static Stop.BussResultat finnNesteBuss(String ønsketAvreisetid, Stop startStopp, Stop sluttStopp, ArrayList<Stop> alleStops) {
        // Finn terminalstoppet (det som har avgangstider)
        Stop terminalStopp = null;
        for (Stop stop : alleStops) {
            if (stop.getAvgangstider() != null && !stop.getAvgangstider().isEmpty()) {
                terminalStopp = stop;
                break;
            }
        }
        
        if (terminalStopp == null) {
            return new Stop.BussResultat(false, "Ingen terminal funnet", null, null, null, 0);
        }
        
        // Finn neste avgangstid fra terminal
        String nesteAvgang = startStopp.finnNesteBuss(ønsketAvreisetid, terminalStopp);
        
        if (nesteAvgang == null) {
            return new Stop.BussResultat(false, "Ingen passende buss funnet", null, null, null, 0);
        }
        
        // Beregn ankomsttider
        String ankomstStartStopp = startStopp.beregnBussPåStopp(nesteAvgang);
        String ankomstSluttStopp = sluttStopp.beregnBussPåStopp(nesteAvgang);
        
        // Beregn reisetid
        int reisetid = startStopp.beregnReisetid(nesteAvgang, sluttStopp);
        
        return new Stop.BussResultat(true, "Buss funnet", nesteAvgang, ankomstStartStopp, ankomstSluttStopp, reisetid);
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
    
    // Klasse for å returnere bussinformasjon
    public static class BussResultat {
        private boolean success;
        private String melding;
        private String avgangFraTerminal;
        private String ankomstStartStopp;
        private String ankomstSluttStopp;
        private int reisetid;

        public BussResultat(boolean success, String melding, String avgangFraTerminal,
                            String ankomstStartStopp, String ankomstSluttStopp, int reisetid) {
            this.success = success;
            this.melding = melding;
            this.avgangFraTerminal = avgangFraTerminal;
            this.ankomstStartStopp = ankomstStartStopp;
            this.ankomstSluttStopp = ankomstSluttStopp;
            this.reisetid = reisetid;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getMelding() { return melding; }
        public String getAvgangFraTerminal() { return avgangFraTerminal; }
        public String getAnkomstStartStopp() { return ankomstStartStopp; }
        public String getAnkomstSluttStopp() { return ankomstSluttStopp; }
        public int getReisetid() { return reisetid; }

        @Override
        public String toString() { 
            if (success) {
                return String.format(
                    "\nBuss ankommer startstopp kl: %s\n" +
                    "Buss ankommer sluttstopp kl: %s\n" +
                    "Reisetid: %d minutter\n",
                    ankomstStartStopp, ankomstSluttStopp, reisetid
                );
            } else {
                return "Feil: " + melding;
            }
        }
    }
}