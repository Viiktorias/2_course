import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StudentFileReader {
    public static void read(StudentContainer container, String path) throws FileNotFoundException, IllegalArgumentException {
        Scanner scanner = new Scanner(new File(path), "UTF-8");
        Scanner tempScanner;
        String name;
        String establishment;
        Double mark;
        Integer somethingNumber;
        String behaviours;
        StringBuilder tempBuilder;
        try {
            while (scanner.hasNextLine()) {
                tempScanner = new Scanner(scanner.nextLine());
                tempBuilder = new StringBuilder(tempScanner.next() + " " + tempScanner.next());
                name = tempBuilder.toString();
                establishment = tempScanner.next();
                mark = Double.parseDouble(tempScanner.next());
                somethingNumber = Integer.parseInt(tempScanner.next());
                if (tempScanner.hasNext()) {
                    behaviours = tempScanner.next();
                    container.add(new Scoolkid(name, establishment, mark, somethingNumber, behaviours));
                } else
                    container.add(new Student(name, establishment, mark, somethingNumber));
            }
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect data");
        }
    }
}
