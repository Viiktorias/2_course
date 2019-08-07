import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SecurityReader {
    public static void readXml(List<Security> list, String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SAXSecurityHandler handler = new SAXSecurityHandler(list);
        parser.parse(new File(path), handler);
    }

    public static void readTxt(List<Security> list, String path) throws IOException {
        try (Scanner scanner = new Scanner(new File(path))) {
            Scanner tempScanner;
            Integer position;
            String surname;
            int salary;
            String guardedObject;
            try {
                while (scanner.hasNextLine()) {
                    tempScanner = new Scanner(scanner.nextLine());
                    tempScanner.useDelimiter("[|]");
                    position = Integer.parseInt(tempScanner.next());
                    surname = tempScanner.next();
                    salary = Integer.parseInt(tempScanner.next());
                    guardedObject = tempScanner.next();
                    list.add(new Security(position, surname, salary, guardedObject));
                }
            } catch (NoSuchElementException | NumberFormatException | IncorrectInputDataException e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}