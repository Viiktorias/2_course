import java.util.Objects;

public class Student implements Comparable<Student> {
    private int course;
    private int group;
    private String surname;
    private String name;

    public Student(int course, int group, String surname, String name) {
        this.course = course;
        this.group = group;
        this.surname = surname;
        this.name = name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return surname + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return course == student.course &&
                group == student.group &&
                surname.equals(student.surname) &&
                name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, group, surname, name);
    }

    @Override
    public int compareTo(Student o) {
        int diff = surname.compareTo(o.surname);
        if (diff == 0)
            return name.compareTo(o.name);
        return diff;
    }
}
