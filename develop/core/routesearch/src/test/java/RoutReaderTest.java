import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.develop.Data.RouteReader;
import org.develop.Entities.Route;
import org.develop.Entities.Stop;
import org.develop.Entities.Transport;
import org.develop.Entities.dto.RouteDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RoutReaderTest {
    
    //Test for å sjekke at innhold er skrevet riktig og har riktig indeksering.
    @Test
    void ReadRoutFromFile(@TempDir Path tempDir) throws IOException {
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

    RouteDTO testRoute = new RouteDTO("101", "Fredrikstad-Halden", new Transport("T101", "Buss"), 100.0, stops);

    //lager en fil i en midlertidlig mappe
    Path filePath = tempDir.resolve("route.json");
    File file = filePath.toFile();

    //skriver til json. simulerer en eksisterende fil
    ObjectMapper mapper = new ObjectMapper();
    mapper.writerWithDefaultPrettyPrinter().writeValue(file, testRoute);

    //Act
    Route routeFromFile = RouteReader.readRouteFromFile(file.getAbsolutePath());

    //Assert
    assertNotNull(routeFromFile,"Route should not be null");
    assertEquals(testRoute.getRouteId(), routeFromFile.getRouteId());
    assertEquals(testRoute.getRouteName(), routeFromFile.getRouteName());
    assertEquals(testRoute.getPrice(), routeFromFile.getPrice());
    assertNotNull(routeFromFile.getTransport());
    assertEquals(9,routeFromFile.getStops().size());
    assertEquals("FS001",routeFromFile.getStops().get(0).getStopId());

    }

    // Test for fil uten riktig innhold eller format. Ikke json format.
    @Test
    void readRouteFromFile_ReturnsNullOnInvalidFile(@TempDir Path tempDir) throws IOException{
        //Arrange
        Path feilFil = tempDir.resolve("Ikke riktig format eller inhold");
        Files.writeString(feilFil,"Ikke gyldig innhold for en Json");
        
        //Act
        Route resultat = RouteReader.readRouteFromFile(feilFil.toString());
        
        //Assert
        assertNull(resultat,"Should be NULL when file is in wrong format");
    }


    //Test for ingen fil med dette navnet eller manglende fil.
    @Test
    void readRouteFromFile_returnsNullOnMissingFile(){
        //Arrange
        // Ingen arrangement nødvendig da filen ikke eksisterer.

        //Act
        Route resultat = RouteReader.readRouteFromFile("Ingen_fil_her.json");

        //Assert
        assertNull(resultat,"Should return null when no file is located");
    }
    
}
