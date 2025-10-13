package org.develop.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.develop.Interface.StopService;
import org.develop.TravelEnteties.Stop;

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

    @Override
    public String findNextDepartureTime(Stop stop, String desiredDepartureTime) {
        List<String> departureTimes = stop.getDepartureTimes();
        if (departureTimes == null || departureTimes.isEmpty()) {
            return null;
        }
        try {
            for (String departureTime : departureTimes) {
                String transportAtStop = calculateTransportAtStop(stop, departureTime);
                if (transportAtStop != null) {
                    if (transportAtStop.compareTo(desiredDepartureTime) >= 0) {
                        return departureTime;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
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
