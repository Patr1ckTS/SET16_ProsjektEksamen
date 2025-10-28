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

    @Override
    public Route getRoute() {
        EnturRouteDTO dto = routeReader.getRuteR20();
        if (dto != null) {
            return routeMapper.mapToRoute(dto);
        } else {
            return null;
        }
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

    public Route getRute101() {
        EnturRouteDTO dto = routeReader.getRute101();
        if (dto != null) {
            return routeMapper.mapToRoute(dto);
        } else {
            return null;
        }
    }
}
