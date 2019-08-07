import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BeastReader {
    public static void readXml(List<Beast> list, String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SAXBeastHandler handler = new SAXBeastHandler(list);
        parser.parse(new File(path), handler);
    }

    public static void readTxt(List<Beast> list, String path) throws IOException {
        try (Scanner scanner = new Scanner(new File(path))) {
            Scanner tempScanner;
            Integer position;
            String name;
            String food;
            try {
                while (scanner.hasNextLine()) {
                    tempScanner = new Scanner(scanner.nextLine());
                    tempScanner.useDelimiter("[|]");
                    position = Integer.parseInt(tempScanner.next());
                    name = tempScanner.next();
                    food = tempScanner.next();
                    list.add(new Beast(name, position, food));
                }
            } catch (NoSuchElementException | NumberFormatException | IncorrectInputDataException e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}