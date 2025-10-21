
package org.develop.Service;

import java.util.ArrayList;
import org.develop.Interface.RouteService;
import org.develop.Interface.StopService;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;

// Utility- og serviceklasse for avanserte ruteberegninger og logikk
// Avhengig av Dependency Injection for StopService
public class RouteLogic implements RouteService {
    private final StopService stopService;

    // Injisering av Dependency Injection
    public RouteLogic(StopService stopService) {
        this.stopService = stopService;
    }

    @Override
    public Route calculateRoute(ArrayList<Route> availableRoutes, String startLocation, String endLocation) {
        // Eksempel på enkel logikk: returner første rute som har start og slutt
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

    
    // Hjelpemetoder for å finne neste transport og beregne tider
    private Result findNextTransport(String desiredDepartureTime, Stop startStop, Stop endStop,
            ArrayList<Stop> allStops, Route route) {

        // Finn terminal
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

        // Finn neste avgang basert på ønsket avgangstid
        String nextDeparture = stopService.findNextDepartureTime(terminal, desiredDepartureTime);
        if (nextDeparture == null) {
            return new Result(false, null, null, "Ingen passende transport funnet",
                    null, null, null, null, null, 0);
        }

        // Beregn ankomster og reisetid
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
