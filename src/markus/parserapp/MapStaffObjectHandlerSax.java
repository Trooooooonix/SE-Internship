package markus.parserapp;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MapStaffObjectHandlerSax extends DefaultHandler {

    List<Activity> result;
    Activity currentActivity;
    Lap currentLap;
    private StringBuilder currentValue = new StringBuilder();

    public List<Activity> getResult() {
        return result;
    }

    @Override
    public void startDocument() {
        result = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        // reset the tag value
        currentValue.setLength(0);

        // start of loop
        if (qName.equalsIgnoreCase("Activity")) {

            // new activity
            currentActivity = new Activity();
            String sport = attributes.getValue("Sport");
            currentActivity.setSport(sport);

            // activity id
            String id = attributes.getValue("id");
            currentActivity.setId(id);

            // activity lapStartTime
            LocalDateTime lapStartTime = LocalDateTime.parse(attributes.getValue("Lap"));
            currentLap = new Lap();
            currentLap.
        }

        if (qName.equalsIgnoreCase("TotalTimeSeconds")) {
            // salary currency

            String currency = attributes.getValue("currency");
            currentStaff.setCurrency(currency);
        }
//TRACK IST TRIGGER FÜR TRACKPOINT ERSTELLUNG LISTE
    }

    public void endElement(String uri, String localName, String qName) {

        if (qName.equalsIgnoreCase("name")) {
            currentStaff.setName(currentValue.toString());
        }

        if (qName.equalsIgnoreCase("role")) {
            currentStaff.setRole(currentValue.toString());
        }

        if (qName.equalsIgnoreCase("salary")) {
            currentStaff.setSalary(new BigDecimal(currentValue.toString()));
        }

        if (qName.equalsIgnoreCase("bio")) {
            currentStaff.setBio(currentValue.toString());
        }

        // end of loop
        if (qName.equalsIgnoreCase("staff")) {
            result.add(currentStaff);
        }

    }

    public void characters(char ch[], int start, int length) {
        currentValue.append(ch, start, length);

    }

}