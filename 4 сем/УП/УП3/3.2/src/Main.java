import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("src\\data.txt"), StandardCharsets.UTF_8)) {
            scanner.useDelimiter("[|]|[\r\n]+");
            String size = scanner.next();
            String title = scanner.next();
            String[] names = new String[Integer.parseInt(size)];
            double[] values = new double[Integer.parseInt(size)];
            int i = 0;
            while (scanner.hasNext()) {
                names[i] = scanner.next();
                values[i] = scanner.nextDouble();
                if (values[i] <= 0)
                    throw new NumberFormatException();
                i++;
            }
            View view = new View(values, names, title);
        } catch (NoSuchElementException | NumberFormatException e) {
            System.err.println("Incorrect data");
        } catch (IOException e) {
            System.err.println("No such file");
        }
    }
}
