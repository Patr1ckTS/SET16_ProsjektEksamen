
package org.develop.Interface;

import java.util.List;

import org.develop.Entities.Stop;

public interface StopService {
    String calculateTransportAtStop(Stop stop, String departureTimeFromTerminal);
    Stop findStopByName(List<Stop> stops, String stopName);
    String findNextDepartureTime(Stop stop, String desiredDepartureTime);
    int calculateTravelTime(Stop startStop, String departureTime, Stop endStop);
}
