package org.develop;

import java.util.ArrayList;
import java.util.Arrays;

import org.develop.FileHandlers.StopListWriter;
import org.develop.Transport.Transport;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.RouteLogic;
import org.develop.TravelEnteties.Stop;

public class Main {
    
    public static void main(String[] args) {

       ArrayList<Stop> stops = new ArrayList<>();
       stops.add(new Stop("FS001", "Fredrikstad sentrum", "Fredrikstad bussterminal", 
           new ArrayList<>(Arrays.asList("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"))));
       stops.add(new Stop("FS002", "Fredrikstad øst", "Cicignon skole", 3));
       stops.add(new Stop("FS003", "Fredrikstad øst", "Kråkerøy terminal", 7));
       stops.add(new Stop("FS004", "Borg kommune", "Borgehavn", 12));
       stops.add(new Stop("FS005", "Borg kommune", "Tune stasjon", 18));
       stops.add(new Stop("FS006", "Sarpsborg vest", "Grålum", 25));
       stops.add(new Stop("FS007", "Sarpsborg sentrum", "Sarpsborg bussterminal", 30));
       stops.add(new Stop("FS008", "Sarpsborg øst", "Sarpsborg sykehus", 35));
       stops.add(new Stop("FS009", "Halden", "Remmen", 90));

        boolean ok = StopListWriter.skrivStopTilFil("develop\\core\\routesearch\\src\\main\\resources\\Holdeplasser1.json", stops);

        // Fjern kommentar for å lese fra fil og kommenter ut skrivingen og dataen over
        //ArrayList<Stop> stops = StopListReader.lesStopFraFil("develop\\\\core\\\\routesearch\\\\src\\\\main\\\\resources\\\\Holdeplasser1.json");

        Route rute101 = new Route("101", "Fredrikstad-Halden", new Transport("T101", "Buss"), 100.0, stops);

        // Eksempel på input fra bruker
        System.out.println(RouteLogic.finnBesteTransport("08:05", 
            Stop.finnStoppMedNavn(rute101.getStops(), "Cicignon skole"), 
            Stop.finnStoppMedNavn(rute101.getStops(), "Remmen"), 
            rute101));
    }
}