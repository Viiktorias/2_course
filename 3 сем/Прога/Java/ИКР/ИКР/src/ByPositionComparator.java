import java.util.Comparator;

public class ByPositionComparator implements Comparator<Worker> {
    public int compare(Worker first, Worker second) {
        return first.getPosition().compareTo(second.getPosition());
    }
}
