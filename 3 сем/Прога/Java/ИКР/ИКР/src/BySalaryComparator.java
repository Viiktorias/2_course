import java.util.Comparator;

public class BySalaryComparator implements Comparator<Worker> {
    @Override
    public int compare(Worker first, Worker second) {
        return second.getSalary() - first.getSalary();
    }
}
