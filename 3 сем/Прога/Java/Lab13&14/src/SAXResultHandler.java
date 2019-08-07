import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class SAXResultHandler extends DefaultHandler {
    private List<Result> list;
    private String currentElement;
    private String number;
    private String name;
    private String subject;
    private int mark;

    public SAXResultHandler(List<Result> list) {
        this.list = list;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) {
        currentElement = qName;
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        try {
            if (qName.equals("Result")) {
                list.add(new Result(number, name, subject, mark));
                number = null;
                name = null;
                subject = null;
                mark = -1;
            }
        } catch (IllegalArgumentException e) {
            throw new SAXException(e);
        }
        currentElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            switch (currentElement) {
                case "Number": {
                    number = new String(ch, start, length);
                    break;
                }
                case "Name": {
                    name = new String(ch, start, length);
                    break;
                }
                case "Subject": {
                    subject = new String(ch, start, length);
                    break;
                }
                case "Mark": {
                    mark = Integer.parseInt(new String(ch, start, length));
                    break;
                }
            }
        } catch (NumberFormatException e) {
            throw new SAXException(e);
        }
    }
}