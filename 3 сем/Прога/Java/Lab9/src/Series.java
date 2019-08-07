import java.io.FileNotFoundException;
import java.io.PrintStream;

public abstract class Series {
    protected int length;
    protected double first;

    public Series(int length, double first) throws IllegalArgumentException {
        if (length <= 0)
            throw new IllegalArgumentException();
        this.length = length;
        this.first = first;
    }

    protected abstract double j(int i);

    public double sum() {
        double sum = 0;
        for (int i = 1; i <= length; i++)
            sum += j(i);
        return sum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= length; i++)
            sb.append(j(i)).append("; ");
        sb.append("сумма: ").append(sum());
        return sb.toString();
    }

    public void save(String path) throws FileNotFoundException {
        try(PrintStream ps = new PrintStream(path)) {
            ps.println(this.toString());
        }
    }
}