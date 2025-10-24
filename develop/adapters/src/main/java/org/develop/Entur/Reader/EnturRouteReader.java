package org.develop.Entur.Reader;

import org.develop.Entur.DTO.EnturRouteDTO;
import org.develop.Entur.DTO.EnturRouteDTO.StopData;
import java.util.ArrayList;

/**
 * Reader for Entur API
 * Henter route-data fra Entur API og bygger EnturRouteDTO
 * Håndterer HTTP-kall, JSON parsing, etc.
 */
public class EnturRouteReader {

    private static final String ENTUR_API_URL = "https://api.entur.io/journey-planner/v3/graphql";

    /**
     * Leser route-data fra Entur API
     * @return EnturRouteDTO med all route, transport og stop data
     */
    public EnturRouteDTO readRouteData() {
        // 1. Send HTTP request til Entur API (GraphQL eller REST)
        // 2. Parse JSON response
        // 3. Bygg EnturRouteDTO med alle felter
        // 4. Bygg StopData-objekter for hvert stop
        // 5. Returner komplett DTO

        return null;
    }

    /**
     * Søker etter ruter mellom to lokasjoner med avgangstid
     * @param fromLocation Start-lokasjon
     * @param toLocation Slutt-lokasjon
     * @param departureTime Ønsket avgangstid
     * @return EnturRouteDTO med rutedata
     */
    public EnturRouteDTO searchRoute(String fromLocation, String toLocation, String departureTime) {
        // 1. Bygg GraphQL query for ruteplanlegging
        // 2. Send request med parametere
        // 3. Parse response og bygg EnturRouteDTO

        return null;
    }

    /**
     * Henter sanntids-data for et stoppested
     * @param stopId ID for stoppested
     * @return EnturRouteDTO med oppdaterte avgangstider
     */
    public EnturRouteDTO getRealTimeData(String stopId) {
        // 1. Send request for sanntidsdata
        // 2. Parse response
        // 3. Oppdater StopData med sanntids avgangstider

        return null;
    }

    /**
     * Privat hjelpemetode for å bygge HTTP request
     */
    private String buildGraphQLQuery(String fromLocation, String toLocation, String departureTime) {
        // Bygg GraphQL query string
        return null;
    }

    /**
     * Privat hjelpemetode for å parse JSON response
     */
    private EnturRouteDTO parseJsonResponse(String jsonResponse) {
        // Bruk Jackson ObjectMapper til å parse JSON til EnturRouteDTO
        return null;
    }
}
