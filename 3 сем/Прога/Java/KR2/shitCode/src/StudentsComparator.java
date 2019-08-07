import java.util.Comparator;

public class StudentsComparator implements Comparator<AbstractStudent> {
    public int compare(AbstractStudent first, AbstractStudent second) {
        int diff = first.establishment.compareTo(second.establishment);
        if (diff == 0)
            return first.name.compareTo(second.name);
        else return diff;
    }
}