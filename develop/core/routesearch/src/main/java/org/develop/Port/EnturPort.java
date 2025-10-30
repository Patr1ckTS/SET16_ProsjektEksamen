package org.develop.Port;

import java.util.ArrayList;

import org.develop.TravelEnteties.Route;

public interface EnturPort {

    // Definerer metode for å hente en spesifikk rute basert på rutenavnet (bruker reader og mapper internt)
    Route getRoute(String routeName);

    ArrayList<Route> getAllRoutes();

}
