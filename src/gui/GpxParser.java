package gui;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;

public class GpxParser {

    public void reader() {
        try {
            File inputFile = new File("koglerau.gpx");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root Element:" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("trkpt");


            for (int counter = 0; counter < nList.getLength(); counter++) {
                Node nNode = nList.item(counter);
                System.out.println("Curr elem:" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("latitude: " + eElement.getAttribute("lat"));
                    System.out.println("longitude: " + eElement.getAttribute("lon"));
                    System.out.println("ele: " + eElement.getElementsByTagName("ele").item(0).getTextContent());
                    System.out.println("time: " + eElement.getElementsByTagName("time").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
