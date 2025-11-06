package org.develop.Entur.Mapper;

import org.develop.Entities.Route;
import org.develop.Entities.Stop;
import org.develop.Entities.Transport;
import org.develop.Entur.DTO.EnturRouteDTO;
import org.develop.Entur.DTO.EnturRouteDTO.StopData;

import java.util.ArrayList;

public class EnturRouteMapper {

    public Route mapToRoute(EnturRouteDTO dto) {
        if (dto == null) {
            return null;
        }

        Transport transport = mapToTransport(dto.getTransport());
        ArrayList<Stop> stops = mapToStops(dto.getStops());

        return new Route(
            dto.getRouteId(),
            dto.getRouteName(),
            transport,
            dto.getPrice(),
            stops
        );
    }

    private Transport mapToTransport(EnturRouteDTO.TransportData transportData) {
        if (transportData == null) {
            return null;
        }

        return new Transport(
            transportData.getTransportId(),
            transportData.getTransportType()
        );
    }

    private ArrayList<Stop> mapToStops(ArrayList<StopData> stopDataList) {
        ArrayList<Stop> stops = new ArrayList<>();

        if (stopDataList == null || stopDataList.isEmpty()) {
            return stops;
        }

        for (StopData stopData : stopDataList) {
            Stop stop = mapToStop(stopData);
            stops.add(stop);
        }

        return stops;
    }

    private Stop mapToStop(StopData stopData) {
        if (stopData.getDepartureTimes() != null && !stopData.getDepartureTimes().isEmpty()) {
            return new Stop(
                stopData.getStopId(),
                stopData.getLocation(),
                stopData.getName(),
                stopData.getDepartureTimes()
            );
        } else {
            return new Stop(
                stopData.getStopId(),
                stopData.getLocation(),
                stopData.getName(),
                stopData.getMinutesAfterDeparture()
            );
        }
    }
}
