package org.develop.Port;

import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import java.util.ArrayList;

/**
 * Entur Repository Port - definerer kontrakten for data-aksess til Entur
 * Heksagonal arkitektur - Port for data persistering/henting
 * 
 * Denne porten definerer hvordan domene-logikken kan hente data fra Entur
 */
public interface EnturRepository {
    
    /**
     * Henter alle stoppesteder
     * @return Liste med alle tilgjengelige stoppesteder
     */
    ArrayList<Stop> getAllStops();
    
    /**
     * Henter ruten
     * @return Den eneste ruten med alle stopp
     */
    Route getRoute();
    
    /**
     * Henter rute basert på start- og sluttsted
     * @param startLocation Startstoppested
     * @param endLocation Sluttstoppested
     * @return Ruten hvis den inneholder begge steder, ellers null
     */
    Route findRouteByLocations(String startLocation, String endLocation);
    
    /**
     * Henter avgangtider for et stoppested
     * @param stopName Navn på stoppested
     * @return Liste med avgangtider
     */
    ArrayList<String> findDepartureTimesForStop(String stopName);
}
