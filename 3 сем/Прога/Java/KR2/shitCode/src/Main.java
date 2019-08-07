import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try{
            StudentContainer<AbstractStudent> container = new StudentContainer<>();
            StudentFileReader.read(container, "students.txt");
            System.out.println(container);
            System.out.println(container.count(new Student("Ульяницкий Владимир", "БГУ", 2.4, 3)));
            StudentContainer<AbstractStudent> sortedContainer = container.sorted(new StudentsComparator());
            System.out.println(sortedContainer);
            System.out.println(sortedContainer.binarySearch(new Student("Ульяницкий Владимир", "БГУ", 4.0, 2)));
            System.out.println(sortedContainer.binarySearch(new Student("Кизенков Владислав", "БГУ", 4.0, 2)));
            System.out.println(sortedContainer.min());
        }
        catch (FileNotFoundException | IllegalArgumentException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
