package org.develop;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {

    ArrayList<Stop> stops = StopListReader.lesStopFraFil("SET16_ProsjektEksamen\\\\develop\\\\core\\\\routesearch\\\\src\\\\main\\\\resources\\\\Holdeplasser1.json");
       
    Route rute101 = new Route("101", "Fredrikstad-Halden", new Transport("T101", "Buss"), 100.0, stops);

        // Eksempel på input fra bruker
        System.out.println(RouteLogic.finnBesteTransport("08:05", 
            Stop.finnStoppMedNavn(rute101.getStops(), "Cicignon skole"), 
            Stop.finnStoppMedNavn(rute101.getStops(), "Remmen"), 
            rute101));
    }
}