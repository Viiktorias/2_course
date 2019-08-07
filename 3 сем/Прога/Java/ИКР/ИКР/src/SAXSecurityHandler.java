import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class SAXSecurityHandler extends DefaultHandler {
    private List<Security> list;
    private String currentElement;
    private int position;
    private String surname;
    private int salary;
    private String guardedObject;

    public SAXSecurityHandler(List<Security> list) {
        this.list = list;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) {
        currentElement = qName;
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        try {
            if (qName.equals("Security")) {
                list.add(new Security(position, surname, salary, guardedObject));
                position = 0;
                surname = null;
                salary = 0;
                guardedObject = null;
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
                case "Surname": {
                    surname = new String(ch, start, length);
                    break;
                }
                case "Salary": {
                    salary = Integer.parseInt(new String(ch, start, length));
                    break;
                }
                case "GuardedObject": {
                    guardedObject = new String(ch, start, length);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            throw new SAXException(e);
        }
    }
}