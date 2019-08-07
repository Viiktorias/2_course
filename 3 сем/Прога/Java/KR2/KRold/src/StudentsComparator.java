import java.util.Comparator;

public class StudentsComparator implements Comparator<Student> {
    public int compare(Student first, Student second) {
        double diff = first.getAverageMark() - second.getAverageMark();
        if (diff == 0.0)
            return first.getCourse() - second.getCourse();
        if (diff < 0)
            return (int) Math.floor(diff);
        else
            return (int) Math.ceil(diff);
    }
}
