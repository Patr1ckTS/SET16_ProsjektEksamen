package org.develop.Entur;

import org.develop.Port.EnturRepository;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Entur Repository Implementation - implementerer EnturRepository
 * Heksagonal arkitektur - repository for Entur data-aksess
 * 
 * Henter transportdata fra Entur (via JSON-filer)
 * Hver JSON-fil inneholder EN rute med MANGE stopp
 */
public class EnturRepositoryAdapter implements EnturRepository {
    
    private final ArrayList<Stop> stops;
    private final Route route; 
    
    public EnturRepositoryAdapter(String stopsFilePath, String routeFilePath) {
        this.stops = loadStops(stopsFilePath);
        this.route = loadRoute(routeFilePath);
    }
    
    /**
     * Laster inn liste med stopp fra JSON-fil
     */
    private ArrayList<Stop> loadStops(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), new TypeReference<ArrayList<Stop>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Laster inn enkelt rute fra JSON-fil
     */
    private Route loadRoute(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), Route.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    @Override
    public ArrayList<Stop> getAllStops() {
        return new ArrayList<>(stops);
    }
    
    @Override
    public Route getRoute() {
        return route;
    }
    
    @Override
    public Route findRouteByLocations(String startLocation, String endLocation) {
        if (route != null) {
            boolean hasStart = route.getStops().stream()
                    .anyMatch(stop -> stop.getName().equalsIgnoreCase(startLocation));
            boolean hasEnd = route.getStops().stream()
                    .anyMatch(stop -> stop.getName().equalsIgnoreCase(endLocation));
            
            if (hasStart && hasEnd) {
                return route;
            }
        }
        
        return null;
    }
    
    @Override
    public ArrayList<String> findDepartureTimesForStop(String stopName) {
        ArrayList<String> departureTimes = new ArrayList<>();
        
        if (route != null) {
            for (Stop stop : route.getStops()) {
                if (stop.getName().equalsIgnoreCase(stopName)) {
                    // Hent avgangtider fra departureTimes listen
                    if (stop.getDepartureTimes() != null) {
                        departureTimes.addAll(stop.getDepartureTimes());
                    }
                }
            }
        }
        
        return departureTimes;
    }
}
