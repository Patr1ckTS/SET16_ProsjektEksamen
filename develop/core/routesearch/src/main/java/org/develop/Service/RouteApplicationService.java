package org.develop.Service;

import java.util.ArrayList;

import org.develop.Port.EnturPort;
import org.develop.Service.dto.ResultDTO;
import org.develop.TravelEnteties.Route;

/**
 * Application Service for rutesøk
 * Koordinerer rutesøk-logikk mellom web-laget og domenelaget
 * Bruker Dependency Injection for loose coupling
 */
public class RouteApplicationService {
    private final RouteLogic routeLogic;
    private final StopLogic stopLogic;
    private final EnturPort enturPort;

    /**
     * Constructor med Dependency Injection
     * Dette er den anbefalte måten å opprette servicen på
     */
    public RouteApplicationService(RouteLogic routeLogic, StopLogic stopLogic, EnturPort enturPort) {
        this.routeLogic = routeLogic;
        this.stopLogic = stopLogic;
        this.enturPort = enturPort;
    }

    /**
     * Alternativ constructor med kun EnturPort
     * Oppretter RouteLogic og StopLogic automatisk
     */
    public RouteApplicationService(EnturPort enturPort) {
        this.stopLogic = new StopLogic();
        this.routeLogic = new RouteLogic(stopLogic);
        this.enturPort = enturPort;
    }

    /**
     * Hovedmetode for rutesøk
     * Søker etter beste rute basert på start, slutt og avgangstid
     * 
     * @param startLocation Startsted (f.eks. "Fredrikstad")
     * @param endLocation Sluttsted (f.eks. "Sarpsborg")
     * @param departureTime Ønsket avgangstid (format "HH:mm")
     * @return ResultDTO med ruteinformasjon eller feilmelding
     */
    public ResultDTO searchRoute(String startLocation, String endLocation, String departureTime) {
        try {
            // Hent alle tilgjengelige ruter fra Entur
            ArrayList<Route> availableRoutes = enturPort.getAllRoutes();
            
            if (availableRoutes == null || availableRoutes.isEmpty()) {
                return createErrorResult("Ingen ruter tilgjengelig");
            }

            // Finn beste rute basert på start og slutt
            Route bestRoute = routeLogic.calculateRoute(availableRoutes, startLocation, endLocation);
            
            if (bestRoute == null) {
                return createErrorResult("Ingen rute funnet mellom " + startLocation + " og " + endLocation);
            }

            // Søk etter transport på den valgte ruten
            RouteLogic.Result result = routeLogic.searchRouteByName(
                departureTime, 
                startLocation, 
                endLocation, 
                bestRoute
            );

            // Konverter Result til ResultDTO
            return convertToDTO(result);

        } catch (Exception e) {
            return createErrorResult("Feil ved rutesøk: " + e.getMessage());
        }
    }

    /**
     * Søk etter rute på en spesifikk rutenavn
     */
    public ResultDTO searchRouteByName(String routeName, String startLocation, String endLocation, String departureTime) {
        try {
            Route route = enturPort.getRoute(routeName);
            
            if (route == null) {
                return createErrorResult("Rute med navn '" + routeName + "' ikke funnet");
            }

            RouteLogic.Result result = routeLogic.searchRouteByName(
                departureTime, 
                startLocation, 
                endLocation, 
                route
            );

            return convertToDTO(result);

        } catch (Exception e) {
            return createErrorResult("Feil ved rutesøk: " + e.getMessage());
        }
    }

    /**
     * Hent alle tilgjengelige alternativer for en reise
     */
    public ArrayList<ResultDTO> findAllAlternatives(String startLocation, String endLocation, String departureTime) {
        ArrayList<ResultDTO> alternatives = new ArrayList<>();
        
        try {
            ArrayList<Route> availableRoutes = enturPort.getAllRoutes();
            
            for (Route route : availableRoutes) {
                RouteLogic.Result result = routeLogic.searchRouteByName(
                    departureTime, 
                    startLocation, 
                    endLocation, 
                    route
                );
                
                if (result.isSuccess()) {
                    alternatives.add(convertToDTO(result));
                }
            }
            
        } catch (Exception e) {
            alternatives.add(createErrorResult("Feil ved søk etter alternativer: " + e.getMessage()));
        }
        
        return alternatives;
    }

    /**
     * Hjelpemetode for å konvertere RouteLogic.Result til ResultDTO
     */
    private ResultDTO convertToDTO(RouteLogic.Result result) {
        return new ResultDTO(
            result.isSuccess(),
            result.toString().contains("Type: ") ? extractValue(result.toString(), "Type: ") : "Ukjent",
            result.toString().contains("Rute Navn: ") ? extractValue(result.toString(), "Rute Navn: ") : "Ukjent",
            result.isSuccess() ? "Transport funnet" : extractValue(result.toString(), "\n"),
            result.getDepartureFromTerminal(),
            extractLocation(result.toString(), "Fra: "),
            extractLocation(result.toString(), "Til: "),
            extractTime(result.toString(), "Fra: ", "Avgangstid: "),
            extractTime(result.toString(), "Til: ", "Ankomsttid: "),
            result.getTravelTime()
        );
    }

    /**
     * Opprett en ResultDTO for feilmeldinger
     */
    private ResultDTO createErrorResult(String errorMessage) {
        return new ResultDTO(
            false,
            null,
            null,
            errorMessage,
            null,
            null,
            null,
            null,
            null,
            0
        );
    }

    // Hjelpemetoder for parsing av Result.toString()
    private String extractValue(String text, String key) {
        int startIndex = text.indexOf(key);
        if (startIndex == -1) return null;
        startIndex += key.length();
        int endIndex = text.indexOf("\n", startIndex);
        if (endIndex == -1) return text.substring(startIndex).trim();
        return text.substring(startIndex, endIndex).trim();
    }

    private String extractLocation(String text, String prefix) {
        int startIndex = text.indexOf(prefix);
        if (startIndex == -1) return null;
        startIndex += prefix.length();
        int endIndex = text.indexOf(" - ", startIndex);
        if (endIndex == -1) return null;
        return text.substring(startIndex, endIndex).trim();
    }

    private String extractTime(String text, String locationPrefix, String timePrefix) {
        int locIndex = text.indexOf(locationPrefix);
        if (locIndex == -1) return null;
        int timeIndex = text.indexOf(timePrefix, locIndex);
        if (timeIndex == -1) return null;
        timeIndex += timePrefix.length();
        int endIndex = text.indexOf("\n", timeIndex);
        if (endIndex == -1) return null;
        return text.substring(timeIndex, endIndex).trim();
    }
}
