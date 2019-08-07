import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CarsFileReader {
    public static <T extends AbstractCar> void read(CarsContainer<T> container, String path) throws FileNotFoundException, IncorrectInputDataException {
        Scanner scanner = new Scanner(new File(path), "UTF-8");
        Scanner tempScanner;
        String name;
        String color;
        String fuel;
        String temp;
        if (!scanner.hasNext()) {
            throw new IncorrectInputDataException("File is empty");
        }
        while (scanner.hasNextLine()) {
            tempScanner = new Scanner(scanner.nextLine());
            try {
                name = tempScanner.next();
                color = tempScanner.next();
                fuel = tempScanner.next();
                temp = tempScanner.next();
                if (tempScanner.hasNext()) {
                    container.add((T) new Bus(name, color, fuel, Integer.parseInt(temp), Integer.parseInt(tempScanner.next())));
                } else {
                    container.add((T) new Car(name, color, fuel, temp));
                }
            } catch (NoSuchElementException | NumberFormatException | ClassCastException e) {
                System.err.println("File contains incorrect data");
            } catch (IncorrectInputDataException e) {
                System.err.println("File contains incorrect data: " + e.getGetMessage());
            }
        }
    }
}
