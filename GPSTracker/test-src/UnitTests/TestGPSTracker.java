package UnitTests;

import handlers.Loader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import tracks.Activity;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestGPSTracker {

    private List<Activity> listOfTestData;

    @BeforeEach
    public void init() {
        List<Path> l = new ArrayList<>();
        try {
            l.add(Path.of("test-src/TestData.tcx"));
            listOfTestData = Loader.loadData(l);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
    @AfterAll
    public void tearDown() {
        this.listOfTestData = null;
    }
    */

    //TODO: Test Methods and functions in Activity-class

    @Test
    public void testGetActivityTotalTimeSeconds() {
        Activity a = listOfTestData.get(0);
        assertEquals(2500, a.getActivityTotalTimeSeconds());
        assertNotEquals(2501, a.getActivityTotalTimeSeconds());
        assertNotEquals(2499, a.getActivityTotalTimeSeconds());
    }

    @Test
    void testGetActivityDistanceMeters() {
        Activity a = listOfTestData.get(0);
        assertEquals(4400, a.getActivityDistanceMeters());
        assertNotEquals(2401, a.getActivityDistanceMeters());
        assertNotEquals(4399, a.getActivityDistanceMeters());
    }

    @Test
    void testGetActivityTotalAltitude() {
        Activity a = listOfTestData.get(0);
        assertEquals(320.4, a.getActivityTotalAltitude());
        assertNotEquals(321, a.getActivityTotalAltitude());
        assertNotEquals(319, a.getActivityTotalAltitude());
    }

    @Test
    void testGetMaxBPM() {
        Activity a = listOfTestData.get(0);
        assertEquals(1500, a.getMaxBPM());
        assertNotEquals(1501, a.getMaxBPM());
        assertNotEquals(1499, a.getMaxBPM());
    }

    @Test
    void testGetAvgBPM() {
        Activity a = listOfTestData.get(0);
        assertEquals(135.6, a.getAvgBPM());
        assertNotEquals(134, a.getAvgBPM());
        assertNotEquals(136, a.getAvgBPM());
    }

    @Test
    void testGetId() {
        Activity a = listOfTestData.get(0);
        assertEquals("Bad Zell", a.getId());
        assertNotEquals("Eisenstadt", a.getId());
        assertNotEquals("Bad Zell1", a.getId());
        assertNotEquals("1Bad Zell", a.getId());
        assertNotEquals("Bad1 Zell", a.getId());
        assertNotEquals("Bad 1Zell", a.getId());
    }

}
