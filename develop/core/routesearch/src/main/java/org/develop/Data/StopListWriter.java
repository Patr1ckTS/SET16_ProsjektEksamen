package org.develop.Data;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.develop.TravelEnteties.Stop;

public class StopListWriter {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter prettyWriter = mapper.writer(new DefaultPrettyPrinter());

    //metode for å skrive stops til json fil.
    public static boolean writeStopsToFile(String filePath, ArrayList<Stop> stops) {
        try {
            prettyWriter.writeValue(new File(filePath), stops);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    //hjelpeklasse for normalisering av data
    public static final class Normalization{
        private Normalization(){}


        //beholder avgangstider på første stopp, nullstiller på resten.
        public static void departureTimesOnlyOnFirst(ArrayList<Stop> stops){
            if(stops == null || stops.isEmpty()) return;
            for(int i = 1; i < stops.size(); i++){
                stops.get(i).setDepartureTimes(null);
            }
        }
    }
}
