
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;



import org.develop.TravelEnteties.Stop;
import org.develop.TravelEnteties.Transport;
import org.develop.Data.RouteWriter;
import org.develop.TravelEnteties.dto.RouteDTO;

public class RouteWriterTest {

    //Test for å sjekke at fil kan skrives og at den inneholder riktig informasjon
    @Test
    void writeRouteToFile(@TempDir Path tempDir) throws IOException{
    
    //Arrange
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

    RouteDTO route = new RouteDTO("101", "Fredrikstad-Halden", new Transport("T101", "Buss"), 100.0, stops);
    
    // lager en fil i midlertidlig mappe.
    Path filePath = tempDir.resolve("route.json");
    File file = filePath.toFile();

    //Act
    boolean success = RouteWriter.writeRouteToFile(file.getAbsolutePath(),route);
    
    //Assert
    assertTrue(success,"writeRouteToFile() Should return true");
    assertTrue(Files.exists(filePath),"File should be created");

    // leser innhold
    String json = Files.readString(filePath);

    //sjekker at filen inneholder riktig informasjon
    assertTrue(json.contains("Fredrikstad-Halden"));
    assertTrue(json.contains("FS001"));
    assertTrue(json.contains("Buss"));
    }

    //Tester IOExeption, Denne prøver å skrive json data rett i en mappe.
    @Test
    void writeRouteToFile_returnsFalseOnIOExeption(@TempDir Path temDir){
        ArrayList<Stop> stops = new ArrayList<>();
        RouteDTO route = new RouteDTO("101", "Fredrikstad-Halden", new Transport("T101", "Buss"), 100.0, stops);

        File directoryAsFile = temDir.toFile();

        boolean success = RouteWriter.writeRouteToFile(directoryAsFile.getAbsolutePath(),route);

        assertFalse(success,"Should return false when an IOE exeption happens.");
    }


    
}
