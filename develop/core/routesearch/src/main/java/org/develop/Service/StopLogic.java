package org.develop.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.develop.Interface.StopService;
import org.develop.TravelEnteties.Stop;

// Utility- og serviceklasse for stoppestedsrelatert logikk
// Egner seg ikke for Dependency Injection, da den ikke har noen avhengigheter
public class StopLogic implements StopService {
    @Override
    public String calculateTransportAtStop(Stop stop, String departureTimeFromTerminal) {
        try {
            LocalTime terminal = LocalTime.parse(departureTimeFromTerminal);
            LocalTime transportAtStop = terminal.plusMinutes(stop.getMinutesAfterDeparture());
            return transportAtStop.toString();
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public Stop findStopByName(List<Stop> stops, String stopName) {
        for (Stop stop : stops) {
            if (stop.getName().equalsIgnoreCase(stopName)) {
                return stop;
            }
        }
        return null;
    }

    // Bruk av Java Streams for å finne neste avgangstid
    // Første itterasjon var veldig nøstet og vanskelig å lese
    @Override
    public String findNextDepartureTime(Stop stop, String desiredDepartureTime) {
        List<String> departureTimes = stop.getDepartureTimes();

        if (departureTimes == null || departureTimes.isEmpty()) {
            return null; // Returner tidlig slik at resten av koden ikke kjøres
        }

        LocalTime desiredTime = LocalTime.parse(desiredDepartureTime);

        // Stream for å sortere og samle logikken i én flyt
        Optional<LocalTime> nextDeparture = departureTimes.stream()    
                .map(LocalTime::parse)                                 
                .sorted()                                              
                .filter(departure -> !departure.isBefore(desiredTime)) 
                .findFirst();                                          

        return nextDeparture.map(LocalTime::toString).orElse(null);
}

    @Override
    public int calculateTravelTime(Stop startStop, String departureTime, Stop endStop) {
        try {
            LocalTime travelStart = LocalTime.parse(calculateTransportAtStop(startStop, departureTime));
            LocalTime travelEnd = LocalTime.parse(calculateTransportAtStop(endStop, departureTime));
            return (int) java.time.Duration.between(travelStart, travelEnd).toMinutes();
        } catch (Exception e) {
            return -1;
        }
    }
}
