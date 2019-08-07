import java.util.Objects;

public class Student implements Comparable<Student> {
    private String firstName;
    private String lastName;
    private int course;
    private double averageMark;

    public Student(String firstName, String lastName, int course, double averageMark) throws IllegalArgumentException {
        if ((course <= 0) ||(course > 6))
            throw new IllegalArgumentException("Incorrect course: " + course);
        if ((averageMark < 0) || (averageMark > 10))
            throw new IllegalArgumentException("Incorrect average mark: " + averageMark);
        if ((firstName == null) || (firstName == ""))
            throw new IllegalArgumentException("Incorrect first name");
        if ((lastName == null) || (lastName == ""))
            throw new IllegalArgumentException("Incorrect last name");
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.averageMark = averageMark;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IllegalArgumentException {
        if ((firstName == null) || (firstName == ""))
            throw new IllegalArgumentException("Incorrect first name");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws IllegalArgumentException {
        if ((lastName == null) || (lastName == ""))
            throw new IllegalArgumentException("Incorrect last name");
        this.lastName = lastName;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) throws IllegalArgumentException {
        if ((course <= 0) ||(course > 6))
            throw new IllegalArgumentException("Incorrect course");
        this.course = course;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) throws IllegalArgumentException {
        if ((averageMark < 4) || (averageMark > 10))
            throw new IllegalArgumentException("Incorrect average mark");
        this.averageMark = averageMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return  Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, course, averageMark);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(firstName).append(' ');
        sb.append(lastName).append(' ');
        sb.append(course).append(' ');
        sb.append(averageMark);
        return sb.toString();
    }

    @Override
    public int compareTo(Student right) {
        int comp1 = lastName.compareToIgnoreCase(right.lastName);
        if (comp1 == 0)
            return firstName.compareToIgnoreCase(right.firstName);
        return comp1;
    }


}