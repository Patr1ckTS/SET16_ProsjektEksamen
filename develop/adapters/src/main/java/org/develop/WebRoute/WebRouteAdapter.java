package org.develop.WebRoute;

import org.develop.Port.WebRouteRepository;
import org.develop.Port.EnturRepository;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import org.develop.Service.dto.ResultDTO;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import org.develop.TravelEnteties.dto.RouteDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WebRouteAdapter implements WebRouteRepository {
    
    private final RouteLogic routeLogic;
    private final StopLogic stopLogic;
    private final EnturRepository enturRepository;
    
    public WebRouteAdapter(RouteLogic routeLogic, StopLogic stopLogic, EnturRepository enturRepository) {
        this.routeLogic = routeLogic;
        this.stopLogic = stopLogic;
        this.enturRepository = enturRepository;
    }
    
    // Henter en rute basert på start- og sluttstoppested
    @Override
    public RouteDTO getRoute(String startLocation, String endLocation) {
        Route route = enturRepository.findRouteByLocations(startLocation, endLocation);
        return route != null ? convertRouteToDTO(route) : null;
    }
    
    // Søker etter ruter og returnerer detaljerte resultater
    @Override
    public ResultDTO searchRoutes(String startLocation, String endLocation, String departureTime) {
        Route route = enturRepository.findRouteByLocations(startLocation, endLocation);
        if (route == null) {
            return createFailureResult("Rute ikke funnet");
        }
        
        // Beregn beste rute via RouteLogic
        ArrayList<Route> availableRoutes = new ArrayList<>();
        availableRoutes.add(route);
        Route calculatedRoute = routeLogic.calculateRoute(availableRoutes, startLocation, endLocation);
        
        if (calculatedRoute == null) {
            return createFailureResult("Kunne ikke beregne rute");
        }
        
        String transportType = calculatedRoute.getTransport() != null ? 
                calculatedRoute.getTransport().getTransportType() : "Ukjent";
        String arrivalAtStart = stopLogic.calculateTransportAtStop(
                calculatedRoute.getStops().get(0), departureTime);
        
        return new ResultDTO(
                true,
                transportType,
                calculatedRoute.getRouteName(),
                "Rute funnet",
                departureTime,
                startLocation,
                endLocation,
                arrivalAtStart,
                "Beregnet",
                30
        );
    }
    
    // Henter alle tilgjengelige ruter
    @Override
    public ArrayList<RouteDTO> getAllRoutes() {
        ArrayList<RouteDTO> routeDTOs = new ArrayList<>();
        Route route = enturRepository.getRoute();
        if (route != null) {
            routeDTOs.add(convertRouteToDTO(route));
        }
        return routeDTOs;
    }
    
    // Henter alle stoppesteder
    @Override
    public ArrayList<String> getAllStops() {
        ArrayList<Stop> stops = enturRepository.getAllStops();
        return stops.stream()
                .map(Stop::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    private RouteDTO convertRouteToDTO(Route route) {
        return new RouteDTO(
                route.getRouteId(),
                route.getRouteName(),
                route.getTransport(),
                route.getPrice(),
                route.getStops()
        );
    }
    
    private ResultDTO createFailureResult(String message) {
        return new ResultDTO(
                false,
                "N/A",
                "N/A",
                message,
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                "N/A",
                0
        );
    }
}
