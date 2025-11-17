package org.develop.Data;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

import org.develop.Entities.dto.RouteDTO;

public class RouteWriter {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter prettyWriter = mapper.writer(new DefaultPrettyPrinter());

    //metode for å skrive route til json fil.
    public static boolean writeRouteToFile(String filePath, RouteDTO routeDTO) {
        try {
            prettyWriter.writeValue(new File(filePath), routeDTO);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
