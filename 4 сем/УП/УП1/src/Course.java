import java.util.Set;
import java.util.stream.Collectors;

public class Course {
    private Set<Student> students;
    private String name;

    public Course(String name, Set<Student> students) {
        this.students = students;
        this.name = name;
    }

    public Set<Postgraduate> getPostgraduates(String nameOfSupervisor) {
       return students.stream().filter((Student student) -> {
            if (student instanceof Postgraduate) {
                Postgraduate postgraduate = (Postgraduate) student;
                if (postgraduate.getSupervisor().getName().equals(nameOfSupervisor))
                    return true;
            }
            return false;
        }).map(student -> (Postgraduate) student).collect(Collectors.toSet());
    }
}