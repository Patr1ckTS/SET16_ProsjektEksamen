package org.develop.Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.develop.Entities.Stop;

public class StopListReader {

    //metode for å lese fra json fil.
    public static ArrayList<Stop> readStopsFromFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), new TypeReference<ArrayList<Stop>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        
    }
    
}
