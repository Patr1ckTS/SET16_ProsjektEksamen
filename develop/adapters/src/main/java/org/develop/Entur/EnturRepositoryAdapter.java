package org.develop.Entur;

import org.develop.Port.EnturRepository;
import org.develop.Entur.Reader.EnturRouteReader;
import org.develop.Entur.Mapper.EnturRouteMapper;
import org.develop.Entur.DTO.EnturRouteDTO;
import org.develop.TravelEnteties.Route;
import java.util.ArrayList;

public class EnturRepositoryAdapter implements EnturRepository {
    private final EnturRouteReader routeReader;
    private final EnturRouteMapper routeMapper;

    public EnturRepositoryAdapter() {
        this.routeReader = new EnturRouteReader();
        this.routeMapper = new EnturRouteMapper();
    }

    private Route mapRoute(EnturRouteDTO dto) {
        return dto != null ? routeMapper.mapToRoute(dto) : null;
    }

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
