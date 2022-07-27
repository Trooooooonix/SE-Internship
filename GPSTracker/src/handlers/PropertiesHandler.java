package handlers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is responsible for reading the filepath from the properties.xml file.
 */
public class PropertiesHandler extends DefaultHandler {
    /**
     * Contains the tag in which the filepath is saved
     */
    private static final String TAGTOFILEPATH = "filePath";


    private String filePath;
    private StringBuilder elementValue;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        // if is here also possible, but we stayed here using switch to keep space for further properties
        switch (qName) {
            case TAGTOFILEPATH -> elementValue = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        // if is here also possible, but we stayed here using switch to keep space for further properties
        switch (qName) {
            case TAGTOFILEPATH -> filePath = elementValue.toString();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) elementValue = new StringBuilder();
        else elementValue.append(ch, start, length);
    }

    /**
     * @return the filepath found in properties.xml
     */
    public String getFilePath() {
        return filePath;
    }
}
