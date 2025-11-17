import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.develop.Service.RouteLogic;
import org.develop.Entities.Stop;
import org.develop.Interface.StopService;

import java.util.ArrayList;
import java.util.Arrays;

public class RouteLogicTest {

    private StopService mockStopService;
    private RouteLogic routeLogic;

    @BeforeEach
    void setUp() {
        mockStopService = Mockito.mock(StopService.class);
        routeLogic = new RouteLogic(mockStopService);
    }

    // Tester for findBestTransport metoden i RouteLogic, da denne er hele essensen av klassen 
    // Bruker mocking for å isolere avhengigheter og fokusere på logikken i RouteLogic
    @Test
    void findBestTransport_ValidInput_ReturnsSuccessfulResult() {
        // Arrange
        Stop terminal = new Stop("T1", "Area", "Terminal",
                new ArrayList<>(Arrays.asList("08:00", "09:00", "10:00")));
        Stop startStop = new Stop("S1", "Area", "Start", 5);
        Stop endStop = new Stop("S2", "Area", "End", 30);

        ArrayList<Stop> allStops = new ArrayList<>(Arrays.asList(terminal, startStop, endStop));

        // Mock StopService atferd
        when(mockStopService.findNextDepartureTime(terminal, "08:05")).thenReturn("09:00");
        when(mockStopService.calculateTransportAtStop(startStop, "09:00")).thenReturn("09:05");
        when(mockStopService.calculateTransportAtStop(endStop, "09:00")).thenReturn("09:30");
        when(mockStopService.calculateTravelTime(startStop, "09:00", endStop)).thenReturn(25);

        // Act
        RouteLogic.Result result = routeLogic.findBestTransport("08:05", startStop, endStop, allStops);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("09:00", result.getDepartureFromTerminal());
        assertEquals(25, result.getTravelTime());

        verify(mockStopService).findNextDepartureTime(terminal, "08:05");
        verify(mockStopService).calculateTransportAtStop(startStop, "09:00");
        verify(mockStopService).calculateTransportAtStop(endStop, "09:00");
        verify(mockStopService).calculateTravelTime(startStop, "09:00", endStop);
    }

    // Tester for scenario uten terminal, at feilmelding returneres er også et viktig aspekt av logikken
    @Test
    void findBestTransport_NoTerminal_ReturnsFailure() {
        // Arrange 
        Stop startStop = new Stop("S1", "Area", "Start", 5);
        Stop endStop = new Stop("S2", "Area", "End", 30);
        ArrayList<Stop> allStops = new ArrayList<>(Arrays.asList(startStop, endStop));

        // Act
        RouteLogic.Result result = routeLogic.findBestTransport("08:05", startStop, endStop, allStops);

        // Assert
        assertFalse(result.isSuccess());
        // Ingen mock-kall skal skje siden det ikke finnes terminal
        verify(mockStopService, never()).findNextDepartureTime(any(), any());
    }

    @Test
    void findBestTransport_NoAvailableTransport_ReturnsFailureResult() {
        // Arrange
        Stop terminal = new Stop("T1", "Area", "Terminal",
                new ArrayList<>(Arrays.asList("08:00", "09:00")));
        Stop startStop = new Stop("S1", "Area", "Start", 5);
        Stop endStop = new Stop("S2", "Area", "End", 30);

        ArrayList<Stop> allStops = new ArrayList<>(Arrays.asList(terminal, startStop, endStop));

        // Mock - ingen passende avgang
        when(mockStopService.findNextDepartureTime(terminal, "22:00")).thenReturn(null);

        // Act
        RouteLogic.Result result = routeLogic.findBestTransport("22:00", startStop, endStop, allStops);

        // Assert 
        assertFalse(result.isSuccess(), "Result skal ikke være success");
        String resultString = result.toString();
        assertTrue(resultString.contains("Ingen"), "Feilmelding skal indikere ingen transport");
    }
}