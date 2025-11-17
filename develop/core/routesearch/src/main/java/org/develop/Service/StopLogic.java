package org.develop.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.develop.Entities.Stop;
import org.develop.Interface.StopService;

/**
 * Domenelogikk for stoppberegninger og avgangshåndtering.
 * 
 * Implementerer StopService-interfacet for å separere logikk fra datatilgang.
 * 
 * Hele systemet er bygget rundt metodene calculateTransportAtStop og findNextDepartureTime.
 * Dette var et bevisst designvalg for å få en fungerende prototype raskt, uten å bruke
 * for mye tid på avanserte algoritmer. Logikken kan senere utvides med mer sofistikerte 
 * rute- og tidsberegninger etter behov.
 */
public class StopLogic implements StopService {
    @Override
    public String calculateTransportAtStop(Stop stop, String departureTimeFromTerminal) {
        // Beregn når transport ankommer stoppet ved å legge til forsinkelse fra terminal
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

    @Override
    public String findNextDepartureTime(Stop stop, String desiredDepartureTime) {
        // Finn første avgang som er på eller etter ønsket tid
        List<String> departureTimes = stop.getDepartureTimes();

        if (departureTimes == null || departureTimes.isEmpty()) {
            return null;
        }

        LocalTime desiredTime = LocalTime.parse(desiredDepartureTime);

        // Parse alle tider, sorter, og finn første som er >= ønsket tid
        Optional<LocalTime> nextDeparture = departureTimes.stream()
                .map(LocalTime::parse)
                .sorted()
                .filter(departure -> !departure.isBefore(desiredTime))
                .findFirst();                                          

        return nextDeparture.map(LocalTime::toString).orElse(null);
}

    @Override
    public int calculateTravelTime(Stop startStop, String departureTime, Stop endStop) {
        // Beregn reisetid mellom to stopp basert på deres forsinkelse fra terminal
        try {
            LocalTime travelStart = LocalTime.parse(calculateTransportAtStop(startStop, departureTime));
            LocalTime travelEnd = LocalTime.parse(calculateTransportAtStop(endStop, departureTime));
            return (int) java.time.Duration.between(travelStart, travelEnd).toMinutes();
        } catch (Exception e) {
            return -1;
        }
    }
}
