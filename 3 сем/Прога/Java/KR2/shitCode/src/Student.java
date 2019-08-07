import java.util.Objects;

public class Student extends AbstractStudent {
    private int course;

    public Student(String name, String establishment, double mark, int course) throws IllegalArgumentException {
        super(name, establishment, mark);
        if ((course < 0) || (course > 6))
            throw new IllegalArgumentException("Incorrect course");
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return course == student.course;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), course);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ");
        sb.append(establishment).append(" ");
        sb.append(mark).append(" ");
        sb.append(course);
        return sb.toString();
    }
}
