import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Comparator;
import java.util.List;

public class SAXBeastHandler extends DefaultHandler {
    private List<Beast> list;
    private String currentElement;
    private int position;
    private String name;
    private String food;

    public SAXBeastHandler(List<Beast> list) {
        this.list = list;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) {
        currentElement = qName;
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        try {
            if (qName.equals("Beast")) {
                list.add(new Beast(name, position, food));
                position = 0;
                name = null;
                food = null;
            }
        } catch (IncorrectInputDataException e) {
            throw new SAXException(e);
        }
        currentElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            switch (currentElement) {
                case "Position": {
                    position = Integer.parseInt(new String(ch, start, length));
                    break;
                }
                case "Name": {
                    name = new String(ch, start, length);
                    break;
                }
                case "Food": {
                    food = new String(ch, start, length);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            throw new SAXException(e);
        }
    }
}