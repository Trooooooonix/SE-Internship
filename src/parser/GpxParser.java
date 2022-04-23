package parser;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDateTime;

public class GpxParser {

    public void reader() {
        try {
            File inputFile = new File("koglerau.gpx");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("trkpt");

            for (int counter = 0; counter < nList.getLength(); counter++) {
                double latitude;
                double longitute;
                double elevation;
                String timeString;
                LocalDateTime time;
                Node nNode = nList.item(counter);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    latitude = Double.parseDouble(eElement.getAttribute("lat"));
                    longitute = Double.parseDouble(eElement.getAttribute("lon"));
                    elevation = Double.parseDouble(eElement.getElementsByTagName("ele").item(0).getTextContent());

                    timeString = eElement.getElementsByTagName("time").item(0).getTextContent();
                    //timeString = timeString.replace("T"," ");
                    timeString = timeString.replace("Z","");
                    time = LocalDateTime.parse(timeString);
                    GpxTrackPoint tp = new GpxTrackPoint(latitude, longitute, elevation, time);
                    System.out.println(tp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
