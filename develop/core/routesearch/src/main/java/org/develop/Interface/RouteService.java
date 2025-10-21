
package org.develop.Interface;

import org.develop.TravelEnteties.Route;
import java.util.ArrayList;

public interface RouteService {
    Route calculateRoute(ArrayList<Route> availableRoutes, String startLocation, String endLocation);
}