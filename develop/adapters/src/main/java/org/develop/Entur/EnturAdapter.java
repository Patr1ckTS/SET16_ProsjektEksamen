package org.develop.Entur;

import org.develop.Port.EnturPort;
import org.develop.Entur.Reader.EnturRouteReader;
import org.develop.Entur.Mapper.EnturRouteMapper;
import org.develop.Entities.Route;
import org.develop.Entur.DTO.EnturRouteDTO;

import java.util.ArrayList;

/**
 * Adapter for Entur-systemet (ruteinformasjon).
 * Modulær oppdeling av Reader/Mapper og DTO
 *
 * Denne adapteren orkestrerer kommunikasjonen med Entur-datakilden:
 * 1. EnturRouteReader henter rute-data (fra JSON/fil)
 * 2. EnturRouteMapper konverterer DTOer til domeneobjekter
 * 3. Adapteren presenterer et rent interface for rutesøk-systemet
 */
public class EnturAdapter implements EnturPort {
    private final EnturRouteReader routeReader;
    private final EnturRouteMapper routeMapper;

    // Konstruktør injection for testing
    public EnturAdapter(EnturRouteReader routeReader, EnturRouteMapper routeMapper) {
        this.routeReader = routeReader;
        this.routeMapper = routeMapper;
    }

    // Default-konstruktør: Brukes av Main-klasser og integrasjonstester for å instansiere med ekte avhengigheter
    public EnturAdapter() {
        this(new EnturRouteReader(), new EnturRouteMapper());
    }

    // Hjelpemetode: konverter DTO til domeneobjekt
    private Route mapRoute(EnturRouteDTO dto) {
        return dto != null ? routeMapper.mapToRoute(dto) : null;
    }

    // Hent spesifikk rute etter navn (Reader og Mapper gjør det faktiske arbeidet)
    public Route getRoute(String routeName) {
        EnturRouteDTO dto = switch(routeName) {
            case "R101" -> routeReader.getRute101();
            case "R103" -> routeReader.getRute103();
            case "R201" -> routeReader.getRute201();
            case "R203" -> routeReader.getRute203();
            case "R20" -> routeReader.getRuteR20();
            case "R40" -> routeReader.getRuteR40();
            default -> null;
        };
        return mapRoute(dto);
    }

    // Hent alle ruter: Reader henter fra data, Mapper konverterer til domeneobjekter
    @Override
    public ArrayList<Route> getAllRoutes() {
        ArrayList<Route> routes = new ArrayList<>();

        EnturRouteDTO[] dtos = {
            routeReader.getRute101(),
            routeReader.getRute103(),
            routeReader.getRute201(),
            routeReader.getRute203(),
            routeReader.getRuteR20(),
            routeReader.getRuteR40()
        };

        for (EnturRouteDTO dto : dtos) {
            if (dto != null) {
                routes.add(routeMapper.mapToRoute(dto));
            }
        }

        return routes;
    }

}
