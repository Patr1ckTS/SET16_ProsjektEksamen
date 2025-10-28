package org.develop.Port;

import java.util.ArrayList;

import org.develop.TravelEnteties.Route;

public interface EnturRepository {

    /**
     * Henter standard rute (R20)
     * @return Standard ruten med alle stopp
     */
    Route getRoute();

    /**
     * Henter alle tilgjengelige ruter
     * @return Liste av alle ruter
     */
    ArrayList<Route> getAllRoutes();

}
