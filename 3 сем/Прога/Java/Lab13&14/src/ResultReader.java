import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ResultReader {
    public static void readXml(List<Result> list, String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SAXResultHandler handler = new SAXResultHandler(list);
        parser.parse(new File(path), handler);
    }

    public static void readTxt(List<Result> list, String path) throws IOException {
        try (Scanner scanner = new Scanner(new File(path))) {
            Scanner tempScanner;
            String number;
            String name;
            String subject;
            int mark;
            try {
                while (scanner.hasNextLine()) {
                    tempScanner = new Scanner(scanner.nextLine());
                    tempScanner.useDelimiter("[|]");
                    number = tempScanner.next();
                    name = tempScanner.next();
                    subject = tempScanner.next();
                    mark = Integer.parseInt(tempScanner.next());
                    list.add(new Result(number, name, subject, mark));
                }
            } catch (NoSuchElementException | IllegalArgumentException e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}