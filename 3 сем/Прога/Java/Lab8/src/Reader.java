import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Reader {
    public static void readInt(BinaryTree<Integer> tree, String path) throws IllegalArgumentException{
        try (Scanner scanner = new Scanner(new File(path), "UTF-16")) {
            while (scanner.hasNext()) {
                tree.add(Integer.parseInt(scanner.next()));
            }
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found");
        }
    }

    public static void delInt(BinaryTree<Integer> tree, String path) throws IllegalArgumentException{
        try (Scanner scanner = new Scanner(new File(path), "UTF-16")) {
            while (scanner.hasNext()) {
                tree.delete(Integer.parseInt(scanner.next()));
            }
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found");
        }
    }

    public static void readStud(BinaryTree<Student> tree, String path) throws IllegalArgumentException {
        try (Scanner scanner = new Scanner(new File(path), "UTF-16")) {
            while (scanner.hasNext()) {
                String firstName;
                String lastName;
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
                    tree.add(new Student(firstName, lastName, course, averageMark));
                } catch (NoSuchElementException e) {
                    System.err.println("File " + path + " contains incorrect data");
                } catch (IllegalArgumentException e) {
                    System.err.println("File " + path + " contains incorrect data: " + e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found");
        }
    }

    public static void delStud(BinaryTree<Student> tree, String path) throws IllegalArgumentException {
        try (Scanner scanner = new Scanner(new File(path), "UTF-16")) {
            while (scanner.hasNext()) {
                String firstName;
                String lastName;
                try {
                    firstName = scanner.next();
                    lastName = scanner.next();
                    tree.delete(new Student(firstName, lastName, 1, 1));
                } catch (NoSuchElementException e) {
                    System.err.println("File " + path + " contains incorrect data");
                } catch (IllegalArgumentException e) {
                    System.err.println("File " + path + " contains incorrect data: " + e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found");
        }
    }
}
