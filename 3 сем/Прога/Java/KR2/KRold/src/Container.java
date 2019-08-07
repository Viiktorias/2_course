import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Container<T extends Comparable<T>> extends ArrayList<T> implements Cloneable {
    public Container(int initialCapacity) {
        super(initialCapacity);
    }

    public Container() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Container<T> clone() {
        return (Container<T>) super.clone();
    }

    @Override
    public boolean add(T element) {
        return super.add(element);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public T set(int index, T element) {
        return super.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
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
        final StringBuilder sb = new StringBuilder("Container{");
        int size = super.size() - 1;
        for (int i = 0; i < size; i++) {
            sb.append(super.get(i).toString());
            sb.append(", ");
        }
        if (size > 0)
            sb.append(super.get(size).toString());
        sb.append('}');
        return sb.toString();
    }
}
