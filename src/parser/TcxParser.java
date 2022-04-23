package parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TcxParser {
    public void reader() {
        try {
            File inputFile = new File("greece.tcx");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //delivers the id of a file. always a string at the start of the tcx file in <id> tag
            NodeList nList = doc.getElementsByTagName("Id");
            Node nId = nList.item(0);
            Element eId = (Element) nId;
            String id = nId.getTextContent();

            //delivers the type of sport
            nList = doc.getElementsByTagName("Activity");
            nId = nList.item(0);
            eId = (Element) nId;
            String activity = eId.getAttribute("Sport");

            nList = doc.getElementsByTagName("Lap");


            for (int counter = 0; counter < nList.getLength(); counter++) {
                double lapTime;
                double lapDistance;
                double maxLapSpeed;
                double lapcalories;
                double lapAvgHeartBPM;
                double lapMaxHeartBPM;
                //List<TcxTrackPoints> lapTrackPoints; wahrscheinlich nicht nötig
                List<Double> tpAltitudeList = new ArrayList<>();

                NodeList tList = doc.getElementsByTagName("Track").item(counter).getChildNodes();
                for (int i = 0; i < tList.getLength(); i++) {
                    if (i % 2 == 1) {
                        System.out.println(tList.item(i).toString());
                        Node tNode = tList.item(i);
                        Element tElement = (Element) tNode;
                        tpAltitudeList.add(Double.parseDouble(tElement.getElementsByTagName("AltitudeMeters").item(0).getTextContent()));
                        //System.out.println(tElement.getElementsByTagName("AltitudeMeters").item(0).getTextContent());
                    }
                }
            }
            System.out.println(id + activity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
