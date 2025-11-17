
package org.develop.Service;

import java.util.ArrayList;

import org.develop.Entities.Route;
import org.develop.Entities.Stop;
import org.develop.Interface.RouteService;
import org.develop.Interface.StopService;

/**
 * Domenelogikk for ruteberegninger og rutesøk.
 * 
 * Denne klassen håndterer forretningslogikken for ruter, og er designet med 
 * Dependency Injection av StopService fordi all rutelogikk er stopp-sentrert.
 * 
 * Implementerer RouteService-interfacet for å definere kontrakten for rute-relaterte operasjoner,
 * noe som gjør klassen testbar og utskiftbar i henhold til Dependency Inversion Principle.
 */
public class RouteLogic implements RouteService {
    private final StopService stopService;

    // Dependency Injection: StopService injiseres fordi rutelogikk er avhengig av stoppberegninger
    public RouteLogic(StopService stopService) {
        this.stopService = stopService;
    }

    @Override
    public Route calculateRoute(ArrayList<Route> availableRoutes, String startLocation, String endLocation) {
        // Itererer gjennom alle ruter og returnerer første som har begge stoppesteder
        for (Route route : availableRoutes) {
            boolean hasStart = false, hasEnd = false;
            for (Stop stop : route.getStops()) {
                if (stop.getName().equalsIgnoreCase(startLocation))
                    hasStart = true;
                if (stop.getName().equalsIgnoreCase(endLocation))
                    hasEnd = true;
            }
            if (hasStart && hasEnd)
                return route;
        }
        return null;
    }

    // API metoder for å finne beste transport og alternativer mellom to stopp

    // Overloaded metode som tar imot Route-objektet
    public Result findBestTransport(String desiredDepartureTime, Stop startStop, Stop endStop, Route route) {
        return findNextTransport(desiredDepartureTime, startStop, endStop, route.getStops(), route);
    }

    public Result findBestTransport(String desiredDepartureTime, Stop startStop, Stop endStop,
            ArrayList<Stop> allStops) {
        return findNextTransport(desiredDepartureTime, startStop, endStop, allStops, null);
    }

    public ArrayList<Result> findAllAlternatives(String desiredDepartureTime, Stop startStop, Stop endStop,
            ArrayList<Stop> allStops) {
        ArrayList<Result> alternatives = new ArrayList<>();
        alternatives.add(findBestTransport(desiredDepartureTime, startStop, endStop, allStops));
        return alternatives;
    }

    public Result searchRouteByName(String departureTime, String startLocation, String endLocation, Route route) {
        // Finn stoppesteder basert på navn, deretter søk etter transport mellom dem
        Stop startStop = stopService.findStopByName(route.getStops(), startLocation);
        Stop endStop = stopService.findStopByName(route.getStops(), endLocation);

        if (startStop == null || endStop == null) {
            return new Result(false, null, null,
                "Stoppested ikke funnet", null, null, null, null, null, 0);
        }

        return findBestTransport(departureTime, startStop, endStop, route);
    }

    public int calculateTravelTime(ArrayList<Stop> route, String startLocationName, String endLocationName, String departureTime) {
        Stop startStop = stopService.findStopByName(route, startLocationName);
        Stop endStop = stopService.findStopByName(route, endLocationName);

        if (startStop == null || endStop == null) {
            return -1;
        }

        return stopService.calculateTravelTime(startStop, departureTime, endStop);
    }

    // Metode for å beregne total reisetid for en rute
    public int calculateTotalTravelTime(ArrayList<Stop> route, String departureTime) {
        if (route == null || route.size() < 2) {
            return 0;
        }
        Stop startStop = route.get(0);
        Stop endStop = route.get(route.size() - 1);
        return stopService.calculateTravelTime(startStop, departureTime, endStop);
    }

    // Metode for å validere rutedata
    public static boolean validateRoute(ArrayList<Stop> stops) {
        if (stops == null || stops.isEmpty()) {
            return false;
        }
        boolean hasTerminal = false;
        for (Stop stop : stops) {
            if (stop.getDepartureTimes() != null && !stop.getDepartureTimes().isEmpty()) {
                hasTerminal = true;
                break;
            }
        }
        return hasTerminal;
    }

    
    // Kjernelogikk: finn terminal, finn avgang, beregn ankomster (se StopLogic for detaljer)
    private Result findNextTransport(String desiredDepartureTime, Stop startStop, Stop endStop,
            ArrayList<Stop> allStops, Route route) {

        // Finn terminal (stoppested som har avgangstider)
        Stop terminal = null;
        for (Stop stop : allStops) {
            if (stop.getDepartureTimes() != null && !stop.getDepartureTimes().isEmpty()) {
                terminal = stop;
                break;
            }
        }

        if (terminal == null) {
            return new Result(false, null, null, "Ingen terminal funnet",
                    null, null, null, null, null, 0);
        }

        // Finn første avgang fra terminal som er på eller etter ønsket tid (StopLogic.findNextDepartureTime)
        String nextDeparture = stopService.findNextDepartureTime(terminal, desiredDepartureTime);
        if (nextDeparture == null) {
            return new Result(false, null, null, "Ingen passende transport funnet",
                    null, null, null, null, null, 0);
        }

        // Beregn når transport ankommer start- og sluttstoppested (StopLogic.calculateTransportAtStop)
        String arrivalAtStart = stopService.calculateTransportAtStop(startStop, nextDeparture);
        String arrivalAtEnd = stopService.calculateTransportAtStop(endStop, nextDeparture);
        int travelTime = stopService.calculateTravelTime(startStop, nextDeparture, endStop);

        // Hent rute-info
        String transportType = "Ukjent transport";
        String routeName = "Ukjent rute";

        if (route != null) {
            if (route.getTransport() != null) {
                transportType = route.getTransport().getTransportType();
            }
            routeName = route.getRouteName();
        }

        return new Result(true, transportType, routeName, "Transport funnet",
                nextDeparture, startStop.getName(), endStop.getName(),
                arrivalAtStart, arrivalAtEnd, travelTime);
    }

    // Indre klasse for å returnere transportinformasjon (resultatet av søket)
    public static class Result {
        private boolean success;
        private String transportType;
        private String routeName;
        private String message;
        private String departureFromTerminal;
        private String startLocation;
        private String endLocation;
        private String arrivalAtStartStop;
        private String arrivalAtEndStop;
        private int travelTime;

        public Result(boolean success, String transportType, String routeName,
                String message, String departureFromTerminal, String startLocation,
                String endLocation, String arrivalAtStartStop, String arrivalAtEndStop,
                int travelTime) {
            this.success = success;
            this.transportType = transportType;
            this.routeName = routeName;
            this.message = message;
            this.departureFromTerminal = departureFromTerminal;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.arrivalAtStartStop = arrivalAtStartStop;
            this.arrivalAtEndStop = arrivalAtEndStop;
            this.travelTime = travelTime;
        }

        // Getters
        public boolean isSuccess() {
            return success;
        }

        public String getDepartureFromTerminal() {
            return departureFromTerminal;
        }

        public int getTravelTime() {
            return travelTime;
        }

        public int getTotalTravelTime() {
            return travelTime;
        }

        @Override
        public String toString() {
            if (!success) {
                return String.format("\n=== Ingen Transport Funnet ===\n%s\n============================\n", message);
            }
            try {
                return String.format(
                        "\n=== Transport Informasjon ===\n" +
                                "Type: %s\n" +
                                "Rute Navn: %s\n" +
                                "Fra: %s - Avgangstid: %s\n" +
                                "Til: %s - Ankomsttid: %s\n" +
                                "Reisetid: %d minutter\n" +
                                "============================\n",
                        transportType,
                        routeName,
                        startLocation, arrivalAtStartStop,
                        endLocation, arrivalAtEndStop,
                        travelTime);
            } catch (Exception e) {
                return String.format("\n Feil: %s\n", e.getMessage());
            }
        }
    }

}
