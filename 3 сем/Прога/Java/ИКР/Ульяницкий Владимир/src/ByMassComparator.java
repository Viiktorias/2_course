import java.util.Comparator;

public class ByMassComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal first, Animal second) {
        return second.getMass() - first.getMass();
    }
}
