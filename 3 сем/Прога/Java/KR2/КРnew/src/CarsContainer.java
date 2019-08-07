import java.util.ArrayList;
import java.util.Collections;

public class CarsContainer<T extends AbstractCar> extends ArrayList<T> implements Cloneable {
    @Override
    @SuppressWarnings("unchecked")
    public CarsContainer<T> clone() {
        return (CarsContainer<T>) super.clone();
    }

    public T binarySearch(T elem) {
        int i = Collections.binarySearch(this, elem);
        if (i > -1)
            return this.get(i);
        else return null;
    }

    public CarsContainer<T> sorted() throws IncorrectInputDataException {
        if (this.isEmpty())
            throw new IncorrectInputDataException("Container is empty");
        CarsContainer<T> cloned = this.clone();
        Collections.sort(cloned);
        return cloned;
    }

    public T max() throws IncorrectInputDataException {
        if (this.isEmpty())
            throw new IncorrectInputDataException("Container is empty");
        return Collections.max(this);
    }

    public int count(T car) {
        return Collections.frequency(this, car);
    }

    public void println() {
        System.out.print("CarsContainer[");
        int size = super.size() - 1;
        for (int i = 0; i < size; i++) {
            this.get(i).print();
            System.out.print(", ");
        }
        if (size > 0)
            this.get(size).print();
        System.out.println();
    }
}