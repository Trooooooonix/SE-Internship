package handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PropertiesHandler extends DefaultHandler {
    private static final String FILEPATH = "filePath";

    private String filePath;
    private StringBuilder elementValue;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        switch (qName) {
            case FILEPATH:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        switch (qName){
            case FILEPATH:
                filePath = elementValue.toString();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        if (elementValue == null) elementValue = new StringBuilder();
        else elementValue.append(ch, start, length);
    }

    public String getFilePath() {
        return filePath;
    }
}
