package org.develop.Entur.Mapper;

import org.develop.Entur.DTO.EnturRouteDTO;
import org.develop.Entur.DTO.EnturRouteDTO.StopData;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Transport;
import org.develop.TravelEnteties.Stop;
import java.util.ArrayList;

/**
 * Mapper for konvertering mellom EnturRouteDTO og domene-objekter
 * Håndterer mapping av Route, Transport og Stop
 */
public class EnturRouteMapper {

    /**
     * Konverterer EnturRouteDTO til Route domene-objekt
     * @param dto EnturRouteDTO fra Entur API
     * @return Route domene-objekt med Transport og Stops
     */
    public Route toDomainRoute(EnturRouteDTO dto) {
        // Bygg Transport-objekt fra DTO sine transport-felter
        Transport transport = toDomainTransport(dto);

        // Konverter StopData til Stop domene-objekter
        ArrayList<Stop> stops = toDomainStops(dto);

        // Bygg og returner Route
        return null;
    }

    /**
     * Konverterer transport-felter fra DTO til Transport domene-objekt
     * @param dto EnturRouteDTO med transport-data
     * @return Transport domene-objekt
     */
    public Transport toDomainTransport(EnturRouteDTO dto) {
        // Hent transport-felter fra DTO og bygg Transport-objekt
        return null;
    }

    /**
     * Konverterer StopData liste til Stop domene-objekter
     * @param dto EnturRouteDTO med stop-data
     * @return ArrayList med Stop domene-objekter
     */
    public ArrayList<Stop> toDomainStops(EnturRouteDTO dto) {
        ArrayList<Stop> stops = new ArrayList<>();

        // Iterer gjennom StopData og konverter til Stop
        for (StopData stopData : dto.getStops()) {
            Stop stop = toDomainStop(stopData);
            stops.add(stop);
        }

        return stops;
    }

    /**
     * Konverterer enkelt StopData til Stop domene-objekt
     * @param stopData StopData fra DTO
     * @return Stop domene-objekt
     */
    public Stop toDomainStop(StopData stopData) {
        // Bygg Stop fra StopData
        return null;
    }

    /**
     * Finner og returnerer avgangstider for et spesifikt stoppested
     * @param dto EnturRouteDTO med stop-data
     * @param stopName Navn på stoppested
     * @return Liste med avgangstider for stoppestedet
     */
    public ArrayList<String> extractDepartureTimesForStop(EnturRouteDTO dto, String stopName) {
        // Finn riktig stop og returner departureTimes
        for (StopData stopData : dto.getStops()) {
            if (stopData.getName().equals(stopName)) {
                return stopData.getDepartureTimes();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Konverterer Route domene-objekt tilbake til DTO
     * (Brukes hvis vi skal skrive data tilbake til Entur)
     * @param route Route domene-objekt
     * @return EnturRouteDTO
     */
    public EnturRouteDTO toDTO(Route route) {
        // Konverter Route + Transport + Stops til flat DTO-struktur
        return null;
    }
}
