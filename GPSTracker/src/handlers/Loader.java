package handlers;

import gui.LoadingFrame;
import gui.GpsTrackerGUI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import tracks.Activity;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Loader {
    private static final String PROPERTIES = "GPSTracker/src/properties.xml";

    public static void initLoading() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        //loading window, so user does not open the app again
        LoadingFrame lf = new LoadingFrame("Loading");
        lf.setLocationRelativeTo(null);
        lf.pack();
        lf.setVisible(true);
        String rootDir = readProperties();
        if (rootDir.equals("empty"))
            rootDir = initFolder();
        List<Path> filePaths = getFilePaths(rootDir);
        List<Activity> aList = loadData(filePaths);
        System.out.println(aList.size());
        GpsTrackerGUI ui = new GpsTrackerGUI("GPS-Viewer", aList);
        ui.setLocationRelativeTo(null);
        ui.setIconImage(new ImageIcon("icon.png").getImage());
        lf.setVisible(false);
        ui.setVisible(true);
    }

    public static boolean dirContainsTcxFile(List<Path> filePathList) {
        if (filePathList.isEmpty())
            return false;
        for (Path p : filePathList) {
            if (p.toString().endsWith("tcx"))
                return true;
        }
        return false;
    }

    public static String initFolder() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException, TransformerException {
        StringBuilder path = new StringBuilder();
        path.append("C:\\Users\\");
        path.append(System.getProperty("user.name"));
        path.append("\\Documents\\GPSTracker");
        File standardDir = new File(path.toString());
        standardDir.mkdirs();
        createReadMe(path.toString());
        createDemoTcx(path.toString());
        updateRootDirectory(path.toString());
        return path.toString();
    }

    public static void updateRootDirectory(String newRootDir) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(PROPERTIES));
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node filePath = (Node) xPath.compile("/filePath").evaluate(doc, XPathConstants.NODE);
        filePath.setTextContent(newRootDir);

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty(OutputKeys.METHOD, "xml");
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource domSource = new DOMSource(doc);
        StreamResult sr = new StreamResult(new File(PROPERTIES));
        tf.transform(domSource, sr);

    }

    public static void createReadMe(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("\\README.txt");
        File readMe = new File(sb.toString());
        readMe.createNewFile();
        FileWriter fw = new FileWriter(sb.toString());
        fw.write("For further information look at user manuel. You can download it here : LINK\nYou can delete the Demo file after you have inserted your track files.\nFor now this application only supports tcx file format");
        fw.close();
    }

    public static void createDemoTcx(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("\\Demo.tcx");
        File demoTcx = new File(sb.toString());
        demoTcx.createNewFile();
        Files.copy(new File("GPSTracker/src/Demo.tcx").toPath(), new File(sb.toString()).toPath(), StandardCopyOption.REPLACE_EXISTING);

    }

    public static String readProperties() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        PropertiesHandler ph = new PropertiesHandler();
        saxParser.parse("GPSTracker/src/properties.xml", ph);
        return ph.getFilePath();
    }

    public static List<Activity> loadData(List<Path> filePathList) throws IOException, SAXException, ParserConfigurationException {
        List<Activity> aList = new ArrayList<>();
        for (Path p : filePathList) {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            if (p.toString().endsWith("tcx")) {
                ActivityHandler ah = new ActivityHandler();
                saxParser.parse(p.toString(), ah);
                aList.add(ah.getActivity());
                //TODO GPX Handler
            } else if (p.toString().endsWith("gpx")) {
                //GPXActivityHandler gh = new GPXActivityHandler();
                //saxParser.parse(p.toString(),gh);
                //aList.add((gh.getActivity()));
            }
        }
        return aList;
    }

    public static List<Path> getFilePaths(String rootDir) throws IOException {
        List<Path> filePathList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(rootDir))) {
            filePathList = paths.filter(Files::isRegularFile).toList();
            if (!dirContainsTcxFile(filePathList))
                createDemoTcx(rootDir);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return filePathList;
    }
}
