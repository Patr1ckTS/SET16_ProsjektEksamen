import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.develop.Entities.Stop;
import org.develop.Service.StopLogic;

public class StopLogicTest {

    private StopLogic stopLogic;

    //oppretter en ny instanse av stoplogoc før hver enhetstest.
    @BeforeEach
    void setUp() {
        stopLogic = new StopLogic();
    }

    @Test
    void calculateTravelTime_DisplayCorrectTravelTime() {
        // Arrange
        Stop start = new Stop("S1", "Area", "Start", 0);
        Stop end = new Stop("S2", "Area", "End", 30);

        // Act
        int travelTime = stopLogic.calculateTravelTime(start, "10:00", end);

        // Assert
        assertEquals(30, travelTime);
    }

    @Test
    void calculateTransportAtStop_ValidTime_ReturnsCorrectTime() {
        // Arrange
        Stop stop = new Stop("S1", "Area", "Stop1", 15);
        String departureTime = "10:00";

        // Act
        String result = stopLogic.calculateTransportAtStop(stop, departureTime);

        // Assert
        assertEquals("10:15", result);
    }
}
