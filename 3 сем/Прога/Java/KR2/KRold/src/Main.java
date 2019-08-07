import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("numbers.txt"));
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
            Scanner scanner = new Scanner(new File("students.txt"));
            if (!scanner.hasNext()) {
                throw new IncorrectInputDataException("File \"students.txt\" is empty");
            }
            Container<Student> students = new Container<>();
            while (scanner.hasNext()) {
                String firstName = "";
                String lastName = "";
                double averageMark = 0;
                int course = 0;
                try {
                    firstName = scanner.next();
                    lastName = scanner.next();
                    try {
                        course = Integer.parseInt(scanner.next());
                    } catch (NumberFormatException e) {
                    }
                    try {
                        averageMark = Double.parseDouble(scanner.next());
                    } catch (NumberFormatException e) {
                    }
                    students.add(new Student(firstName, lastName, course, averageMark));
                } catch (NoSuchElementException e) {
                    System.err.println("File \"students.txt\" contains incorrect data");
                } catch (IncorrectInputDataException e) {
                    System.err.println("File \"students.txt\" contains incorrect data: " + e.getGetMessage());
                }
            }
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