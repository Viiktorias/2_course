import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StudentsFileReader {
    public static <T extends AbstractStudent> void read(Container<T> container, String path) throws FileNotFoundException, IncorrectInputDataException {
        Scanner scanner = new Scanner(new File(path), "UTF-8");
        Scanner tempScanner;
        String firstName;
        String lastName;
        String temp;
        int course;
        double mark;
        if (!scanner.hasNext()) {
            throw new IncorrectInputDataException("File is empty");
        }
        while (scanner.hasNextLine()) {
            tempScanner = new Scanner(scanner.nextLine());
            try {
                firstName = tempScanner.next();
                lastName = tempScanner.next();
                temp = tempScanner.next();
                if (tempScanner.hasNext()) {
                    course = Integer.parseInt(temp);
                    mark = Double.parseDouble(tempScanner.next());
                    container.add((T) new Bachelor(firstName, lastName, course, mark));
                } else {
                    container.add((T) new Master(firstName, lastName, temp));
                }
            } catch (NoSuchElementException | NumberFormatException | ClassCastException e) {
                System.err.println("File contains incorrect data");
            } catch (IncorrectInputDataException e) {
                System.err.println("File contains incorrect data: " + e.getGetMessage());
            }
        }
    }
}
