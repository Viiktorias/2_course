import java.util.Objects;

public class Student implements Comparable<Student> {
    private String firstName;
    private String lastName;
    private int course;
    private double averageMark;

    public Student(String firstName, String lastName, int course, double averageMark) throws IncorrectInputDataException {
        if ((course <= 0) ||(course > 6))
            throw new IncorrectInputDataException("Incorrect course: " + course);
        if ((averageMark < 4) || (averageMark > 10))
            throw new IncorrectInputDataException("Incorrect average mark: " + averageMark);
        if ((firstName == null) || (firstName == ""))
            throw new IncorrectInputDataException("Incorrect first name");
        if ((lastName == null) || (lastName == ""))
            throw new IncorrectInputDataException("Incorrect last name");
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.averageMark = averageMark;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IncorrectInputDataException {
        if ((firstName == null) || (firstName == ""))
            throw new IncorrectInputDataException("Incorrect first name");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws IncorrectInputDataException {
        if ((lastName == null) || (lastName == ""))
            throw new IncorrectInputDataException("Incorrect last name");
        this.lastName = lastName;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) throws IncorrectInputDataException {
        if ((course <= 0) ||(course > 6))
            throw new IncorrectInputDataException("Incorrect course");
        this.course = course;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) throws IncorrectInputDataException {
        if ((averageMark < 4) || (averageMark > 10))
            throw new IncorrectInputDataException("Incorrect average mark");
        this.averageMark = averageMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return course == student.course &&
                Double.compare(student.averageMark, averageMark) == 0 &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, course, averageMark);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", course=").append(course);
        sb.append(", averageMark=").append(averageMark);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Student right) {
        int comp1 = lastName.compareTo(right.lastName);
        if (comp1 == 0)
            return firstName.compareTo(right.firstName);
        return comp1;
    }


}
