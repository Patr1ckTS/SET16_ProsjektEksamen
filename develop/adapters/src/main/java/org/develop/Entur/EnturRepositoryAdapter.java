package org.develop.Entur;

import org.develop.Port.EnturRepository;
import org.develop.Entur.Reader.EnturRouteReader;
import org.develop.Entur.Mapper.EnturRouteMapper;
import org.develop.Entur.DTO.EnturRouteDTO;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import java.util.ArrayList;

/**
 * Adapter for Entur Repository
 * Implementerer EnturRepository port-interface
 * Bruker Reader for å hente data fra Entur API
 * Bruker Mapper for å konvertere DTOer til domene-objekter
 */
public class EnturRepositoryAdapter implements EnturRepository {

    private final EnturRouteReader reader;
    private final EnturRouteMapper mapper;

    public EnturRepositoryAdapter() {
        this.reader = new EnturRouteReader();
        this.mapper = new EnturRouteMapper();
    }

    /**
     * Henter alle stoppesteder fra Entur
     * @return Liste med alle Stop domene-objekter
     */
    @Override
    public ArrayList<Stop> getAllStops() {
        // 1. Reader henter EnturRouteDTO fra Entur API
        EnturRouteDTO dto = reader.readRouteData();

        // 2. Mapper ekstraherer og konverterer stops fra DTO
        ArrayList<Stop> stops = mapper.toDomainStops(dto);

        return stops;
    }

    /**
     * Henter den eneste ruten med alle stopp
     * @return Route domene-objekt
     */
    @Override
    public Route getRoute() {
        // 1. Reader henter EnturRouteDTO fra Entur API
        EnturRouteDTO dto = reader.readRouteData();

        // 2. Mapper konverterer hele DTO til Route (med Transport og Stops)
        Route route = mapper.toDomainRoute(dto);

        return route;
    }

    /**
     * Henter rute basert på start- og sluttsted
     * @param startLocation Startstoppested
     * @param endLocation Sluttstoppested
     * @return Route hvis den inneholder begge steder, ellers null
     */
    @Override
    public Route findRouteByLocations(String startLocation, String endLocation) {
        // 1. Reader søker etter rute mellom to lokasjoner
        EnturRouteDTO dto = reader.searchRoute(startLocation, endLocation, null);

        // 2. Hvis ingen rute funnet, returner null
        if (dto == null) {
            return null;
        }

        // 3. Mapper konverterer DTO til Route
        Route route = mapper.toDomainRoute(dto);

        return route;
    }

    /**
     * Henter avgangtider for et stoppested
     * @param stopName Navn på stoppested
     * @return Liste med avgangtider
     */
    @Override
    public ArrayList<String> findDepartureTimesForStop(String stopName) {
        // 1. Reader henter EnturRouteDTO med alle stops
        EnturRouteDTO dto = reader.readRouteData();

        // 2. Mapper ekstraherer avgangstider for spesifikt stop
        ArrayList<String> departureTimes = mapper.extractDepartureTimesForStop(dto, stopName);

        return departureTimes;
    }
}
