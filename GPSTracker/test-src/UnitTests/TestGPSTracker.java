package UnitTests;

import handlers.Loader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import tracks.Activity;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGPSTracker {

    private List<Activity> listOfTestData;

    @BeforeEach
    public void init() {
        List<Path> l = new ArrayList<>();
        try{
            l.add(Path.of("test-src/TestData.tcx"));
            listOfTestData = Loader.loadData(l);
        } catch (IOException | ParserConfigurationException | SAXException e) {
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
    public void testGetActivityDistanceMeters() {
        Activity a = listOfTestData.get(0);
        assertEquals(4400, a.getActivityDistanceMeters());
        assertNotEquals(2401, a.getActivityDistanceMeters());
        assertNotEquals(4399, a.getActivityDistanceMeters());
    }

    @Test
    public void testGetActivityTotalAltitude() {
        Activity a = listOfTestData.get(0);
        assertEquals(320.4, a.getActivityTotalAltitude());
        assertNotEquals(321, a.getActivityTotalAltitude());
        assertNotEquals(319, a.getActivityTotalAltitude());
    }

    @Test
    public void testGetMaxBPM(){
        Activity a = listOfTestData.get(0);
        assertEquals(1500, a.getMaxBPM());
        assertNotEquals(1501, a.getMaxBPM());
        assertNotEquals(1499, a.getMaxBPM());
    }

    @Test
    public void testGetAvgBPM(){
        Activity a = listOfTestData.get(0);
        assertEquals(135.6, a.getAvgBPM());
        assertNotEquals(134, a.getAvgBPM());
        assertNotEquals(136, a.getAvgBPM());
    }

    @Test
    public void testSetDateOutput(){
        PrintStream prevCon = System.out;
        ByteArrayOutputStream stringCon = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stringCon));
        try {
            Activity a = listOfTestData.get(0);
            a.setDate();
            System.out.println(a);
            String printed = stringCon.toString().trim();
            System.setOut(prevCon);
            assertEquals("Activity{" +
                    "id='" + a.getId() + '\'' +
                    ", \nsport='" + a.getSport() + '\'' +
                    ", \nlaps=" + a.getLaps() +
                    '}', printed);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetId(){
        Activity a = listOfTestData.get(0);
        assertEquals("Bad Zell", a.getId());
        assertNotEquals("Eisenstadt", a.getId());
        assertNotEquals("Bad Zell1", a.getId());
        assertNotEquals("1Bad Zell", a.getId());
        assertNotEquals("Bad1 Zell", a.getId());
        assertNotEquals("Bad 1Zell", a.getId());
    }



}
