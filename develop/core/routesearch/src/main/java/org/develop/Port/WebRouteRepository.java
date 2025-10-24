package org.develop.Port;

import org.develop.Service.dto.ResultDTO;
import org.develop.TravelEnteties.dto.RouteDTO;
import java.util.ArrayList;

/**
 * Web Route Port - definerer kontrakten mellom routesearch og web-frontend
 * Bruker DTOer (RouteDTO, ResultDTO) for data-overføring mellom lag
 */
public interface WebRouteRepository {

    // Henter en rute basert på start- og sluttstoppested
    RouteDTO getRoute(String startLocation, String endLocation);

    // Søker etter ruter med avgangtid og returnerer detaljerte resultater
    ResultDTO searchRoutes(String startLocation, String endLocation, String departureTime);

    // Henter alle tilgjengelige ruter
    ArrayList<RouteDTO> getAllRoutes();

    // Henter alle stoppesteder
    ArrayList<String> getAllStops();
}
