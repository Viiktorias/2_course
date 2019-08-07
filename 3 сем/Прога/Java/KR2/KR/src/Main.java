import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("strings.txt"), "UTF-8");
            if (!scanner.hasNext()) {
                throw new IncorrectInputDataException("File \"strings.txt\" is empty");
            }
            Container<String> strings = new Container<>();
            while (scanner.hasNext()) {
                strings.add(scanner.next());
            }
            System.out.print("Strings: ");
            System.out.println(strings);
            StringComparator comparator = new StringComparator();
            System.out.print("Strings, sorted by length: ");
            System.out.println(strings.sorted(comparator));
            System.out.println("Maximum: " + strings.max(comparator));
            System.out.println("Minimum: " + strings.min(comparator));
            int position = strings.indexOf("Java");
            if (position == -1)
                System.out.println("\"Java\" isn't founded");
            else
                System.out.println("\"Java\" is founded on " + position + " position");

        } catch (FileNotFoundException e) {
            System.err.println("File \"strings.txt\" isn't exists");
        } catch (IncorrectInputDataException e) {
            System.err.println(e.getGetMessage());
        }

        try {
            Scanner scanner = new Scanner(new File("numbers.txt"), "UTF-8");
            if (!scanner.hasNext()) {
                throw new IncorrectInputDataException("File \"numbers.txt\" is empty");
            }
            Container<Double> numbers = new Container<>();
            while (scanner.hasNext()) {
                try {
                    numbers.add(Double.parseDouble(scanner.next()));
                } catch (NumberFormatException e) {
                    System.err.println("File \"numbers.txt\" contains incorrect data");
                }
            }
            System.out.print("Numbers: ");
            System.out.println(numbers);
            System.out.print("Sorted numbers: ");
            System.out.println(numbers.sorted());
            System.out.println("Maximum: " + numbers.max());
            System.out.println("Minimum: " + numbers.min());

        } catch (FileNotFoundException e) {
            System.err.println("File \"numbers.txt\" isn't exists");
        } catch (IncorrectInputDataException e) {
            System.err.println(e.getGetMessage());
        }

        try {
            Container<AbstractStudent> students = new Container<>();
            StudentsFileReader.read(students, "students.txt");
            System.out.print("Students: ");
            System.out.println(students);
            System.out.print("Students, sorted in the natural order: ");
            System.out.println(students.sorted());
            System.out.println("Maximum: " + students.max());
            System.out.println("Minimum: " + students.min());
            StudentsComparator comparator = new StudentsComparator();
            System.out.print("Students, sorted by average mark: ");
            System.out.println(students.sorted(comparator));
            System.out.println("Maximum: " + students.max(comparator));
            System.out.println("Minimum: " + students.min(comparator));
        } catch (FileNotFoundException e) {
            System.err.println("File \"students.txt\" isn't exists");
        } catch (IncorrectInputDataException e) {
            System.err.println(e.getGetMessage());
        }
    }
}