package handlers;

import gui.GpsTrackerGUI;
import gui.LoadingFrame;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import tracks.Activity;

import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class contains all necessary methods to start the GPSTracker and keep the activities and properties up to date.
 */
public class Loader {
    /**
     * Contains the relative path in the jar to the properties.xml file
     */
    private static final String PROPERTIES = "files/properties.xml";
    /**
     * Contains the relative path in the jar to the demo.tcx file
     */
    private static final String DEMO = "files/demo.tcx";
    /**
     * Contains the absolute path to the properties.xml file in user directory
     */
    private static final String pathToPropertiesFile = System.getProperty("user.home") + File.separator + "GPSTracker" + File.separator + "properties.xml";
    /**
     * Contains the absolute standard path where properties.xml is located and standard path to tcx files.
     */
    private static final String standardDirectory = System.getProperty("user.home") + File.separator + "GPSTracker";
    /**
     * user interface
     */
    private static GpsTrackerGUI ui;
    /**
     * used for reading xml structured files
     */
    private static SAXParserFactory saxParserFactory;

    /**
     * This method initiates the loading of the application.
     *
     * @throws ParserConfigurationException if a parser cannot be created which satisfies the requested configuration
     * @throws IOException                  if needed files are not found
     * @throws SAXException                 for SAX errors
     * @throws XPathExpressionException     if the expression cannot be evaluated
     * @throws TransformerException         if an unrecoverable error occurs during the writing of properties.xml file
     */
    public static void initLoading() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException {
        Logging.print("Loading frame start");
        LoadingFrame lf = new LoadingFrame("Loading");
        lf.initFrame();
        if (propertiesNotExist()) {
            createFolderAndProperties();
            createDemoTcx(standardDirectory);
            updateRootDirectory(standardDirectory);
        }
        List<Path> filePaths = getFilePaths(readProperties());
        List<Activity> aList = loadData(filePaths);
        Logging.print(aList.size() + " Files read");
        ui = new GpsTrackerGUI("GPS-Viewer", aList);
        ui.setLocationRelativeTo(null);
        Loader l = new Loader();
        ui.setIconImage(new ImageIcon(Objects.requireNonNull(l.getClass().getResource("icons/icon.png"))).getImage());
        lf.deleteFrame();
        Logging.print("Loading frame stop");
        ui.setVisible(true);
    }

    /**
     * This method creates the GPSTracker folder in the standardDirectory and copies the properties.xml file into it.
     *
     * @throws IOException if properties.xml is not found
     */
    private static void createFolderAndProperties() throws IOException {
        if (new File(standardDirectory).mkdirs()) Logging.print("Standard directory: " + standardDirectory + "created");
        else Logging.print("Standard directory: " + standardDirectory + "was not created");
        Loader l = new Loader();
        l.copyResource(PROPERTIES, pathToPropertiesFile);
    }

    /**
     * This method checks if the properties.xml file does exist or not.
     *
     * @return true if the properties.xml does not exist. Otherwise, returns false.
     */
    private static boolean propertiesNotExist() {
        return !new File(pathToPropertiesFile).exists();
    }

    /**
     * This method reloads the activity list when user took action
     *
     * @throws ParserConfigurationException if a parser cannot be created which satisfies the requested configuration
     * @throws IOException                  if properties.xml is not found
     * @throws SAXException                 for SAX errors
     * @throws XPathExpressionException     if the expression cannot be evaluated
     * @throws TransformerException         if an unrecoverable error occurs during the writing of properties.xml file
     */
    public static void reloadData() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        String rootDir = readProperties();
        if (propertiesNotExist()) {
            createFolderAndProperties();
            createDemoTcx(standardDirectory);
        }
        List<Path> filePaths = getFilePaths(rootDir);
        List<Activity> aList = loadData(filePaths);
        ui.updateGUI(aList);
    }

    /**
     * This method writes the properties.xml file with the given string.
     *
     * @param newRootDir this String will be written to the properties.xml file
     * @throws ParserConfigurationException if a parser cannot be created which satisfies the requested configuration
     * @throws IOException                  if properties.xml is not found
     * @throws SAXException                 for SAX errors
     * @throws XPathExpressionException     if the expression cannot be evaluated
     * @throws TransformerException         if an unrecoverable error occurs during the writing of properties.xml file
     */
    public static void updateRootDirectory(String newRootDir) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(pathToPropertiesFile));
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node filePath = (Node) xPath.compile("/filePath").evaluate(doc, XPathConstants.NODE);
        filePath.setTextContent(newRootDir);

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty(OutputKeys.METHOD, "xml");

        DOMSource domSource = new DOMSource(doc);
        StreamResult sr = new StreamResult((pathToPropertiesFile));
        tf.transform(domSource, sr);
    }

    /**
     * This method reads the file path from the properties.xml file.
     *
     * @return the file path which is in the properties.xml
     * @throws ParserConfigurationException if a parser cannot be created which satisfies the requested configuration
     * @throws SAXException                 for SAX errors
     * @throws IOException                  if properties.xml is not found
     */
    private static String readProperties() throws ParserConfigurationException, SAXException, IOException {
        saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        PropertiesHandler ph = new PropertiesHandler();
        saxParser.parse(new File(pathToPropertiesFile), ph);
        return ph.getFilePath();
    }

    /**
     * This method loads activities from a list of paths.
     *
     * @param filePathList contains a list of paths
     * @return a list of activities
     * @throws SAXException                 for SAX errors
     * @throws ParserConfigurationException if a parser cannot be created which satisfies the requested configuration
     * @throws IOException                  if file in list of paths is not found
     */
    public static List<Activity> loadData(List<Path> filePathList) throws SAXException, ParserConfigurationException, IOException {
        List<Activity> aList = new ArrayList<>();
        for (Path p : filePathList) {
            saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            if (p.toString().endsWith("tcx")) {
                ActivityHandler ah = new ActivityHandler();
                try {
                    saxParser.parse(p.toString(), ah);
                    aList.add(ah.getActivity());
                } catch (Exception e) {
                    Logging.print("File " + p + " is corrupted and not added to the list");
                }
            }
        }
        return aList;
    }

    /**
     * This method returns a list of paths from a given directory.
     *
     * @param rootDir contains the given directory
     * @return a list of paths from all files in the given directory.
     */
    private static List<Path> getFilePaths(String rootDir) {
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

    /**
     * This method checks if a list of paths contains a TCX file.
     *
     * @param filePathList list of paths
     * @return true if the given List of paths contain at least one tcx file
     */
    private static boolean dirContainsTcxFile(List<Path> filePathList) {
        Logging.print("Files read in: ");
        for (Path p : filePathList)
            Logging.print("   -" + p.toString());
        if (filePathList.isEmpty()) return false;
        for (Path p : filePathList) {
            if (p.toString().endsWith("tcx")) return true;
        }
        return false;
    }

    /**
     * This method creates a demo file in TCX format in the given path.
     *
     * @param path path where demo file will be created
     * @throws IOException when demo file is not found
     */
    private static void createDemoTcx(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("\\demo.tcx");
        File demoTcx = new File(sb.toString());
        if (demoTcx.createNewFile()) Logging.print("demo.tcx file created");
        else Logging.print("demo.tcx already exists");
        Loader l = new Loader();
        l.copyResource(DEMO, sb.toString());
    }

    /**
     * This method copies a resource from one path to another path.
     *
     * @param res  path from File which should be copied
     * @param dest destination where File should be copied
     * @throws IOException if res resource was not found
     */
    private void copyResource(String res, String dest) throws IOException {
        Logging.print("Start copying File from " + res + " to " + dest);
        InputStream src = this.getClass().getResourceAsStream(res);
        assert src != null;
        Files.copy(src, Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
        Logging.print("Completed copying File");
    }
}
