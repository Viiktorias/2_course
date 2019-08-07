import java.util.Objects;

public abstract class AbstractStudent implements Comparable<AbstractStudent> {
    protected String name;
    protected String establishment;
    protected double mark;

    public AbstractStudent(String name, String establishment, double mark) throws IllegalArgumentException {
        if ((mark < 0) || (mark > 10))
            throw new IllegalArgumentException("Incorrect mark");
        this.name = name;
        this.establishment = establishment;
        this.mark = mark;
    }

    @Override
    public int compareTo(AbstractStudent right) {
        int diff = establishment.compareTo(right.establishment);
        if (diff == 0)
            return name.compareTo(right.establishment);
        return diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractStudent that = (AbstractStudent) o;
        return Double.compare(that.mark, mark) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(establishment, that.establishment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, establishment, mark);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ");
        sb.append(establishment).append(" ");
        sb.append(mark);
        return sb.toString();
    }
}
