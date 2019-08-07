import java.util.Scanner;
import java.util.Set;

public class ProgrammingTest {

    public static void main(String[] args) {
        String inputName;
        String inputMessage;
        Scanner scanner = new Scanner(System.in);
        System.out.println("<InputName>");
        inputName = scanner.next();
        System.out.println("<InputMessage>");
        inputMessage = scanner.next();
        Student[] students = {new Postgraduate("Kizenkov", "kizya", "kizya@mail.ru", new Academic("Komarovsky")),
                new Undergraduate("Kekelev", "kek12", "kek@gmail.com", new Academic("Petrovich")),
                new Postgraduate("Martynkov", "mvp", "mvp@gmail.com", new Academic("Sakovich")),
                new Undergraduate("Belousov", "otchislen", "belousov@gmail.com", new Academic("Komarovsky")),
                new Postgraduate("Kunitskaya", "saske", "saske@gmail.com", new Academic("Komarovsky"))};
        Course course = new Course("2", Set.of(students));

        Set<Postgraduate> postgraduates = course.getPostgraduates(inputName);
        Notifier notifier = new Notifier(postgraduates);
        notifier.doNotifyAll(inputMessage);
    }
}
