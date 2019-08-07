import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentContainer<T extends AbstractStudent> implements Cloneable {
    private ArrayList<T> list;

    public StudentContainer() {
        list = new ArrayList<>();
    }

    public StudentContainer(ArrayList<T> list) {
        this.list = list;
    }

    public void add(T elem) {
        list.add(elem);
    }

    public int count(T student) {
        return Collections.frequency(list, student);
    }

    @Override
    @SuppressWarnings("unchecked")
    public StudentContainer<T> clone() {
        return new StudentContainer<>((ArrayList<T>) list.clone());
    }

    public StudentContainer<T> sorted() {
        StudentContainer<T> cloned = this.clone();
        Collections.sort(cloned.list);
        return cloned;
    }

    public StudentContainer<T> sorted(Comparator<? super T> comparator) {
        StudentContainer<T> cloned = this.clone();
        Collections.sort(cloned.list, comparator);
        return cloned;
    }

    public T binarySearch(T elem) {
        int i = Collections.binarySearch(list, elem, new StudentsComparator());
        if (i > -1)
            return list.get(i);
        else return null;
    }

    public T min() {
        if (!list.isEmpty())
            return Collections.min(list, new StudentsComparator());
        else return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (T elem : list) {
            stringBuilder.append(elem).append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
