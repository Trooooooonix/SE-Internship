package handlers;

import app.GPSTracker;
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
    private static final String DEMO = "GPSTracker/src/Demo.tcx";
    private static GpsTrackerGUI ui;

    public static void initLoading() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        //loading window, so user does not open the app again
        LoadingFrame lf = new LoadingFrame("Loading");
        lf.initFrame();
        String rootDir = readProperties();
        //only used when user opens application first time OR User deleted directory (if directory is changed in application standard directory will be created)
        if (rootDir.equals("empty") || !new File(rootDir).exists())
            rootDir = initFolder();
        List<Path> filePaths = getFilePaths(rootDir);
        List<Activity> aList = loadData(filePaths);
        Logging.print(aList.size() + " Files eingelesen");
        ui = new GpsTrackerGUI("GPS-Viewer", aList);
        ui.setLocationRelativeTo(null);
        ui.setIconImage(new ImageIcon("icon.png").getImage());
        lf.deleteFrame();
        ui.setVisible(true);
    }

    public static void reloadData() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        String rootDir = readProperties();
        if (rootDir.equals("empty") || !new File(rootDir).exists())
            rootDir = initFolder();
        List<Path> filePaths = getFilePaths(rootDir);
        List<Activity> aList = loadData(filePaths);
        if (ui != null)
            ui.updateGUI(aList);
    }

    private static String initFolder() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException, TransformerException {
        String path = System.getProperty("user.home") + File.separator + "GPSTracker";

        /*String os = System.getProperty("os.name");
        StringBuilder path = new StringBuilder();
        if (os.contains("win")) {
            Logging.print("OS Windows detected");
            path.append("C:\\Users\\");
            path.append(System.getProperty("user.name"));
            path.append("\\Documents\\GPSTracker");
        } else if (os.contains("mac")) {
            Logging.print("OS Mac detected");
            path.append("~\\GPSTracker");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            Logging.print("OS Linux detected");
            path.append("\\opt\\GPSTracker");
        } else {
            Logging.print("not supported OS detected");
            Logging.print("initiate application exit");
        }*/
        File standardDir = new File(path);
        standardDir.mkdirs();
        createReadMe(path);
        createDemoTcx(path);
        updateRootDirectory(path);
        return path;
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

    private static String readProperties() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        PropertiesHandler ph = new PropertiesHandler();
        saxParser.parse(PROPERTIES, ph);
        return ph.getFilePath();
    }

    private static List<Activity> loadData(List<Path> filePathList) throws IOException, SAXException, ParserConfigurationException {
        List<Activity> aList = new ArrayList<>();
        for (Path p : filePathList) {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            if (p.toString().endsWith("tcx")) {
                ActivityHandler ah = new ActivityHandler();
                saxParser.parse(p.toString(), ah);
                aList.add(ah.getActivity());
                //TODO GPX Handler, may be implemented in further releases
            } else if (p.toString().endsWith("gpx")) {
                //GPXActivityHandler gh = new GPXActivityHandler();
                //saxParser.parse(p.toString(),gh);
                //aList.add((gh.getActivity()));
            }
        }
        return aList;
    }

    private static List<Path> getFilePaths(String rootDir) throws IOException {
        List<Path> filePathList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(rootDir))) {
            filePathList = paths.filter(Files::isRegularFile).toList();
            if (!dirContainsTcxFile(filePathList)) {
                createDemoTcx(rootDir);
                try (Stream<Path> path = Files.walk(Paths.get(rootDir))) {
                    filePathList = path.filter(Files::isRegularFile).toList();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePathList;
    }

    private static boolean dirContainsTcxFile(List<Path> filePathList) {
        Logging.print("Files werden eingelesen: ");
        for (Path p : filePathList)
            Logging.print("   -" + p.toString());
        if (filePathList.isEmpty())
            return false;
        for (Path p : filePathList) {
            if (p.toString().endsWith("tcx"))
                return true;
        }
        return false;
    }

    private static void createReadMe(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("\\ReadMe.txt");
        File readMe = new File(sb.toString());
        readMe.createNewFile();
        FileWriter fw = new FileWriter(sb.toString());
        fw.write("For further information look at user manuel. You can download it here : LINK\nYou can delete the Demo file after you have inserted your track files.\nFor now this application only supports tcx file format");
        fw.close();
    }

    private static void createDemoTcx(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("\\Demo.tcx");
        File demoTcx = new File(sb.toString());
        demoTcx.createNewFile();
        Files.copy(new File(DEMO).toPath(), new File(sb.toString()).toPath(), StandardCopyOption.REPLACE_EXISTING);

    }
}
