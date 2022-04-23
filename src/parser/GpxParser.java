package parser;

import org.w3c.dom.*;

import javax.swing.text.DateFormatter;
import javax.xml.parsers.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GpxParser {

    public void reader() {
        try {
            File inputFile = new File("koglerau.gpx");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("trkpt");
            DateFormat dateformater = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

            for (int counter = 0; counter < nList.getLength(); counter++) {
                double latitude;
                double longitute;
                double elevation;
                String timeString;
                Date time;
                Node nNode = nList.item(counter);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    latitude = Double.parseDouble(eElement.getAttribute("lat"));
                    longitute = Double.parseDouble(eElement.getAttribute("lon"));
                    elevation = Double.parseDouble(eElement.getElementsByTagName("ele").item(0).getTextContent());

                    timeString = eElement.getElementsByTagName("time").item(0).getTextContent();
                    timeString = timeString.replace("T"," ");
                    timeString = timeString.replace("Z","");
                    time = (Date) dateformater.parse(timeString);
                    TrackPoint tp = new TrackPoint(latitude, longitute, elevation, time);
                    System.out.println(tp);

                    if (counter == 0){


                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
