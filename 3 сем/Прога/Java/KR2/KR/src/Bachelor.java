import java.util.Objects;

public class Bachelor extends AbstractStudent {
    private int course;
    private double averageMark;
    private Performance performance;

    protected enum Performance {
        EXCELLENT("Excellent"),
        GOOD("Good"),
        BACKWARD("Backward"),
        NONSATISFACTORY("Non-satisfactory");
        protected String description;

        Performance(String description) {
            this.description = description;
        }
    }

    private void setPerformance() {
        if (averageMark < 4) {
            this.performance = Performance.NONSATISFACTORY;
        } else if (averageMark < 6) {
            this.performance = Performance.BACKWARD;
        } else if (averageMark < 9) {
            this.performance = Performance.GOOD;
        } else {
            this.performance = Performance.EXCELLENT;
        }
    }

    public Bachelor(String firstName, String lastName, int course, double averageMark) throws IncorrectInputDataException {
        super(firstName, lastName);
        if ((course <= 0) || (course > 4))
            throw new IncorrectInputDataException("Incorrect course: " + course);
        if ((averageMark < 0) || (averageMark > 10))
            throw new IncorrectInputDataException("Incorrect average mark: " + averageMark);
        this.course = course;
        this.averageMark = averageMark;
        this.setPerformance();
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) throws IncorrectInputDataException {
        if ((course <= 0) || (course > 4))
            throw new IncorrectInputDataException("Incorrect course");
        this.course = course;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) throws IncorrectInputDataException {
        if ((averageMark < 0) || (averageMark > 10))
            throw new IncorrectInputDataException("Incorrect average mark");
        this.averageMark = averageMark;
        this.setPerformance();
    }

    public String getPerformance() {
        return performance.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bachelor)) return false;
        if (!super.equals(o)) return false;
        Bachelor bachelor = (Bachelor) o;
        return course == bachelor.course &&
                Double.compare(bachelor.averageMark, averageMark) == 0 &&
                performance == bachelor.performance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), course, averageMark, performance);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Студент ");
        sb.append(course).append(" курса ");
        sb.append(firstName).append(" ");
        sb.append(lastName).append(", средний балл ");
        sb.append(averageMark).append(" (");
        sb.append(performance.description).append(")");
        return sb.toString();
    }
}