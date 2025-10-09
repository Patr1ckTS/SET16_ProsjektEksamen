package org.develop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StopListReader {

    //metode for å lese fra json fil.
    public static ArrayList<Stop> lesStopFraFil(String filbane) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filbane), new TypeReference<ArrayList<Stop>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        
    }
    
}
