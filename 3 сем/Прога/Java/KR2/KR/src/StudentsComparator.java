import java.util.Comparator;

public class StudentsComparator implements Comparator<AbstractStudent> {
    public int compare(AbstractStudent first, AbstractStudent second) {
        if ((first instanceof Bachelor) && (second instanceof Bachelor)) {
            double diff = ((Bachelor)first).getAverageMark() - ((Bachelor)second).getAverageMark();
            if (diff == 0.0)
                return ((Bachelor)first).getCourse() - ((Bachelor)second).getCourse();
            if (diff < 0)
                return (int) Math.floor(diff);
            else
                return (int) Math.ceil(diff);
        }
        if (first instanceof Bachelor)
            return -1;
        if (second instanceof Bachelor)
            return 1;
        return ((Master)first).getSpeciality().compareTo(((Master)second).getSpeciality());
    }
}