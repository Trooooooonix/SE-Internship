package parser;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GpxParser {

    public void reader() {
        try {
            File inputFile = new File("koglerau.gpx");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("trkpt");
            DateTimeFormatter dtm = DateTimeFormatter.ofPattern("YYYY-MM-DD'T'HH:mm:ss'Z'");

            for (int counter = 0; counter < nList.getLength(); counter++) {
                double latitude;
                double longitute;
                double elevation;
                String time;
                Node nNode = nList.item(counter);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    latitude = Double.parseDouble(eElement.getAttribute("lat"));
                    longitute = Double.parseDouble(eElement.getAttribute("lon"));
                    elevation = Double.parseDouble(eElement.getElementsByTagName("ele").item(0).getTextContent());
                    time = eElement.getElementsByTagName("time").item(0).getTextContent();

                    TrackPoint tp = new TrackPoint(latitude, longitute, elevation, time);
                    System.out.println(tp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
