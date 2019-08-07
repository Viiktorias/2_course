import java.util.Comparator;

public class ByPositionComparator implements Comparator<Animal> {
    public int compare(Animal first, Animal second) {
        int dif = first.getPosition().compareTo(second.getPosition());
        if (dif == 0)
            return first.getName().compareToIgnoreCase(second.getName());
        return dif;
    }
}
