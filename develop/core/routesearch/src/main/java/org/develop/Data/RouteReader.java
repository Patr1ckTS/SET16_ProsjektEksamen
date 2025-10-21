package org.develop.Data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import org.develop.TravelEnteties.Route;

public class RouteReader {

    //metode for å lese fra .json fil
    public static Route readRouteFromFile(String filepath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filepath), Route.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } 
}
