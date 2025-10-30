package org.develop.Calendar.Reader;

import org.develop.Calendar.DTO.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

public class CalendarReader {

    private final ObjectMapper objectMapper;

    public CalendarReader() {
        this.objectMapper = new ObjectMapper();
    }

    public CalendarEventDTO readCalenderEventFromFile(String fileName) {
        try {
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream("Calendar/" + fileName);
            if (resourceStream == null) {
                System.err.println("Feil: Kunne ikke finne ressurs Calendar/" + fileName);
                return null;
            }

            CalendarEventDTO dto = objectMapper.readValue(resourceStream, CalendarEventDTO.class);

            System.out.println("Leste kalenderhendelse: " + dto.getEventName() +
                               " (ID: " + dto.getEventId() + ") " +
                               "fra " + dto.getStartLocation() +
                               " til " + dto.getEndLocation() +
                               " på dato " + dto.getEventDate());
            return dto;

        } catch (IOException e) {
            System.err.println("Feil ved lesing av " + fileName + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public CalendarEventDTO getEpler() {
        return readCalenderEventFromFile("Ida_Epler.json");
    }

    public CalendarEventDTO getKatt() {
        return readCalenderEventFromFile("Ida_Katt.json");
    }

    public CalendarEventDTO getStromper() {
        return readCalenderEventFromFile("Ida_Stromper.json");
    }

    public CalendarEventDTO getKino() {
        return readCalenderEventFromFile("Ole_Kino.json");
    }

    public CalendarEventDTO getMonster() {
        return readCalenderEventFromFile("Ole_Monster.json");
    }

    public CalendarEventDTO getRoblox() {
        return readCalenderEventFromFile("Ole_Roblox.json");
    }
    
}
