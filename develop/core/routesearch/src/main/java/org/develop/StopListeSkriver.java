package org.develop;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StopListeSkriver {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter prettyWriter = mapper.writer(new DefaultPrettyPrinter());

    //meode for å skrive til json fil.
    public static boolean skrivStopTilFil(String filbane, ArrayList<Stop> stops) {
        try {
            prettyWriter.writeValue(new File(filbane), stops);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    //hjelpeklasse for normalisering av data
    public static final class Normalisering{
        private Normalisering(){}


        //beholder avgangstider på første stopp, nullstiller på resten.
        public static void avgangstiderBarePåForste(ArrayList<Stop> stops){
            if(stops == null || stops.isEmpty()) return;
            for(int i = 1; i < stops.size(); i++){
                stops.get(i).setAvgangstider(null);
            }
        }
    }
}
