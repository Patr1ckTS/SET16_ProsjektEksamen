package org.develop.Entur.Reader;

import org.develop.Entur.DTO.EnturRouteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.IOException;

public class EnturRouteReader {

    private final ObjectMapper objectMapper;

    public EnturRouteReader() {
        this.objectMapper = new ObjectMapper();
    }

    public EnturRouteDTO readRouteFromFile(String fileName) {
        try {
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream("Entur/" + fileName);
            if (resourceStream == null) {
                System.err.println(" Feil: Kunne ikke finne ressurs Entur/" + fileName);
                return null;
            }
            EnturRouteDTO dto = objectMapper.readValue(resourceStream, EnturRouteDTO.class);
            System.out.println(" Leste rute: " + dto.getRouteName() +
                             " (ID: " + dto.getRouteId() + ") med " +
                             dto.getStops().size() + " stopp");
            return dto;
        } catch (IOException e) {
            System.err.println(" Feil ved lesing av " + fileName + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public EnturRouteDTO getRute101() {
        return readRouteFromFile("Rute101_Stops_Fredrikstad_Sarpsborg.json");
    }

    public EnturRouteDTO getRute103() {
        return readRouteFromFile("Rute103_Stops_Halden_Fredrikstad.json");
    }

    public EnturRouteDTO getRute201() {
        return readRouteFromFile("Rute201_Stops_Sarpsborg_Fredrikstad.json");
    }

    public EnturRouteDTO getRute203() {
        return readRouteFromFile("Rute203_Stops_Fredrikstad_Halden.json");
    }

    public EnturRouteDTO getRuteR20() {
        return readRouteFromFile("RuteR20_Stops_Moss_Halden.json");
    }

    public EnturRouteDTO getRuteR40() {
        return readRouteFromFile("RuteR40_stops_Halden_Moss.json");
    }

}
