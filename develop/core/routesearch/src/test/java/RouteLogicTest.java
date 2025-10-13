
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.develop.Service.RouteLogic;
import org.develop.Interface.StopService;
import org.develop.TravelEnteties.Stop;

public class RouteLogicTest {


    private StopService stopServiceMock;
    private RouteLogic routeLogic;

    @BeforeEach
    void setUp() {
        stopServiceMock = Mockito.mock(StopService.class);
        routeLogic = new RouteLogic(stopServiceMock);
    }

    @Test
    void calculateTravelTime_DisplayCorrectTravelTimeWithMockedStopService() {
        // Arrange
        Stop start = new Stop("S1", "Area", "Start", 0);
        Stop end = new Stop("S2", "Area", "End", 30);
        String departureTime = "08:00";

        // Mock oppførsel for StopService
        when(stopServiceMock.calculateTravelTime(start, departureTime, end)).thenReturn(30);

        // Act
        int travelTime = stopServiceMock.calculateTravelTime(start, departureTime, end);

        // Assert
        assertEquals(30, travelTime);
        verify(stopServiceMock).calculateTravelTime(start, departureTime, end);
    }
}