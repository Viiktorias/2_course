import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src\\input.txt"), "UTF-16")) {
            while (scanner.hasNext())
                list.add(scanner.next());
            View view = new View(list);
        }
        catch (IOException e) {
            System.err.println("Input not founded");
        }
    }
}
