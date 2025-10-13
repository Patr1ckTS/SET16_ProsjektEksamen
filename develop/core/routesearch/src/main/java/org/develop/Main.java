package org.develop;

import java.util.ArrayList;
import java.util.Arrays;

import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import org.develop.TravelEnteties.Transport;
import org.develop.Data.StopListReader;
import org.develop.Data.StopListWriter;
import org.develop.Interface.StopService;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;

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

        boolean ok = StopListWriter.writeStopsToFile("develop\\core\\routesearch\\src\\main\\resources\\Holdeplasser1.json", stops);

        // Fjern kommentar for å lese fra fil og kommenter ut skrivingen og dataen over
        ArrayList<Stop> stops1 = StopListReader.readStopsFromFile("develop\\\\core\\\\routesearch\\\\src\\\\main\\\\resources\\\\Holdeplasser1.json");

        Route rute101 = new Route("101", "Fredrikstad-Halden", new Transport("T101", "Buss"), 100.0, stops);

        // Dependency Injection 
        StopService stopService = new StopLogic();

        // Eksempel på input fra bruker med DI
    Stop startStop = stopService.findStopByName(rute101.getStops(), "Cicignon skole");
    Stop endStop = stopService.findStopByName(rute101.getStops(), "Remmen");

        // Bruk RouteLogic sin statiske metode for å vise at DI også kan kombineres, men helst bør alt gå via interface
        System.out.println(RouteLogic.findBestTransport("08:05", startStop, endStop, rute101));
    }
}