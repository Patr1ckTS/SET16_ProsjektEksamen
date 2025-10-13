
package org.develop.Interface;

import org.develop.TravelEnteties.Stop;
import java.util.List;

public interface StopService {
    String calculateTransportAtStop(Stop stop, String departureTimeFromTerminal);
    Stop findStopByName(List<Stop> stops, String stopName);
    String findNextDepartureTime(Stop stop, String desiredDepartureTime);
    int calculateTravelTime(Stop startStop, String departureTime, Stop endStop);
}
