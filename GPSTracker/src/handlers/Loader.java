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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Loader {
    private static final String PROPERTIES = "files/properties.xml";
    private static final String DEMO = "files/demo.tcx";
    private static GpsTrackerGUI ui;
    private static SAXParserFactory saxParserFactory;
    private static String pathToProperties = System.getProperty("user.home") + File.separator + "GPSTracker" + "\\properties.xml";


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

        Loader l = new Loader();
        ui.setIconImage(l.getIcon().getImage());
        lf.deleteFrame();
        ui.setVisible(true);
    }

    public ImageIcon getIcon() {
        Logging.print(String.valueOf(this.getClass().getResource("icons/icon.png")));

        ImageIcon icon = new ImageIcon(this.getClass().getResource("icons/icon.png"));
        return icon;
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
        File standardDir = new File(path);
        standardDir.mkdirs();
        Loader l = new Loader();
        l.copyResource(PROPERTIES, pathToProperties);
        //createReadMe(path);
        createDemoTcx(path);
        updateRootDirectory(path);
        return path;
    }

    public String getPropertiesAsString() {
        String properties = (this.getClass().getResource(PROPERTIES).toString());
        return properties;
    }

    public File getProperties() {
        Logging.print(String.valueOf(this.getClass().getResource(PROPERTIES)));
        File properties = new File((this.getClass().getResource(PROPERTIES)).getFile());
        return properties;
    }

    public static void updateRootDirectory(String newRootDir) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Loader l = new Loader();
        Document doc = db.parse(new File(pathToProperties));
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node filePath = (Node) xPath.compile("/filePath").evaluate(doc, XPathConstants.NODE);
        filePath.setTextContent(newRootDir);

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty(OutputKeys.METHOD, "xml");
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource domSource = new DOMSource(doc);
        StreamResult sr = new StreamResult((pathToProperties));
        tf.transform(domSource, sr);
    }

    private static String readProperties() throws ParserConfigurationException, SAXException, IOException {
        saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        PropertiesHandler ph = new PropertiesHandler();
        Loader l = new Loader();
        System.out.println("-----------------" + l.getPropertiesAsString());
        System.out.println("-----------------" + pathToProperties);
        saxParser.parse(new File(pathToProperties), ph);
        return ph.getFilePath();
    }

    public static List<Activity> loadData(List<Path> filePathList) throws SAXException, ParserConfigurationException {
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
                    //If a File does not contain the expected syntax, the file will not be added to the aList
                    Logging.print("File is corrupted");
                }
                //TODO GPX Handler, may be implemented in further releases
            } else if (p.toString().endsWith("gpx")) {
                //GPXActivityHandler gh = new GPXActivityHandler();
                //saxParser.parse(p.toString(),gh);
                //aList.add((gh.getActivity()));
            }
        }
        return aList;
    }

    private static List<Path> getFilePaths(String rootDir){
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
        sb.append("\\demo.tcx");
        File demoTcx = new File(sb.toString());
        demoTcx.createNewFile();
        Loader l = new Loader();
        System.out.println(" +++++ neues Demo File:" + new File(sb.toString()).toPath());
        // Path demo = l.getDemo();

        System.out.println(" +++++ neues Demo File:" + new File(sb.toString()).toPath());
        //System.out.println(" +++++ altes Demo File:" + demo);

        l.copyResource(DEMO, sb.toString());
        //Files.copy(demo, new File(sb.toString()).toPath(), StandardCopyOption.REPLACE_EXISTING);

    }

    public void copyResource(String res, String dest) throws IOException {
        InputStream src = this.getClass().getResourceAsStream(res);
        Files.copy(src, Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
    }

    public Path getDemo() {
        // Logging.print(String.valueOf(this.getClass().getResource(DEMO)));
        // System.out.println(new File((this.getClass().getResource(DEMO)).getFile()).toPath());
        // System.out.println((this.getClass().getResource(DEMO)).getFile());
        // System.out.println(this.getClass().getResource(DEMO));

        Path demo = Path.of(this.getClass().getResource(DEMO).toString());

        return demo;
    }




}
