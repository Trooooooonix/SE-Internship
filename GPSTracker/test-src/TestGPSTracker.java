import app.GPSTracker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGPSTracker {

    private GPSTracker gpsTracker;

    @BeforeEach
    public void init() {
        this.gpsTracker = new GPSTracker();
    }

    @AfterAll
    public void tearDown() {
        this.gpsTracker = null;
    }

    //TODO: Test Methods and functions in Activity-class

    @Test
    public void testAnything() {
        //assertThrows()   -> wenn Exception geworfen wird
        //assertEquals(expectedValue, actualValue) -> prüft auf gleichheit
    }
}
