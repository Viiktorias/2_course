import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Container<T extends Comparable<? super T>> extends ArrayList<T> implements Cloneable {
    @Override
    @SuppressWarnings("unchecked")
    public Container<T> clone() {
        return (Container<T>) super.clone();
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
    }

    public T binarySearch(T elem) {
        int i = Collections.binarySearch(this, elem);
        if (i > -1)
            return this.get(i);
        else return null;
    }

    public T binarySearch(T elem, Comparator<? super T> comparator) {
        int i = Collections.binarySearch(this, elem, comparator);
        if (i > -1)
            return this.get(i);
        else return null;
    }

    public Container<T> sorted() throws IncorrectInputDataException {
        if (this.isEmpty())
            throw new IncorrectInputDataException("Container is empty");
        Container<T> cloned = this.clone();
        Collections.sort(cloned);
        return cloned;
    }

    public Container<T> sorted(Comparator<? super T> comparator) throws IncorrectInputDataException {
        if (this.isEmpty())
            throw new IncorrectInputDataException("Container is empty");
        Container<T> cloned = this.clone();
        Collections.sort(cloned, comparator);
        return cloned;
    }

    public T min() throws IncorrectInputDataException {
        if (this.isEmpty())
            throw new IncorrectInputDataException("Container is empty");
        return Collections.min(this);
    }

    public T max() throws IncorrectInputDataException {
        if (this.isEmpty())
            throw new IncorrectInputDataException("Container is empty");
        return Collections.max(this);
    }

    public T min(Comparator<? super T> comparator) throws IncorrectInputDataException {
        if (this.isEmpty())
            throw new IncorrectInputDataException("Container is empty");
        return Collections.min(this, comparator);
    }

    public T max(Comparator<? super T> comparator) throws IncorrectInputDataException {
        if (this.isEmpty())
            throw new IncorrectInputDataException("Container is empty");
        return Collections.max(this, comparator);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Container[");
        int size = super.size() - 1;
        for (int i = 0; i < size; i++) {
            sb.append(super.get(i).toString());
            sb.append(", ");
        }
        if (size > 0)
            sb.append(super.get(size).toString());
        sb.append(']');
        return sb.toString();
    }
}
