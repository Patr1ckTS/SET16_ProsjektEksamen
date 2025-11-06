
package org.develop.Interface;

import java.util.ArrayList;

import org.develop.Entities.Route;

public interface RouteService {
    Route calculateRoute(ArrayList<Route> availableRoutes, String startLocation, String endLocation);
}