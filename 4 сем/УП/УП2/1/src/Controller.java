import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Controller {
    private Map<String, String> countries;
    private View view;

    public Controller() {
        countries = new TreeMap<>();
        try (Scanner scanner = new Scanner(new File("src\\countries.txt"))) {
            scanner.useDelimiter("[|]|[\r\n]+");
            String country, capital;
            while (scanner.hasNext()) {
                country = scanner.next();
                capital = scanner.next();
                countries.put(country, capital);
            }
            view = new View(this, countries);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
