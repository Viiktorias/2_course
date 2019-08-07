import java.util.Objects;
import java.util.Scanner;

public class Beast extends Animal {
    private String food;

    public Beast(String name, int position, String food) throws IncorrectInputDataException {
        super(name, position);
        if (food == null || food.equals(""))
            throw new IncorrectInputDataException("Incorrect guarded object");
        this.food = food;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(' ').append(food);
        sb.append(' ').append(getMass());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beast)) return false;
        if (!super.equals(o)) return false;
        Beast beast = (Beast) o;
        return Objects.equals(food, beast.food);
    }

    public int compareTo(Beast o) {
        int dif = super.compareTo(o);
        if (dif == 0) {
            return food.compareTo(o.food);
        }
        return dif;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), food);
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) throws IncorrectInputDataException {
        if (food == null || food.equals(""))
            throw new IncorrectInputDataException("Incorrect guarded object");
        this.food = food;
    }

    @Override
    public int getMass() {
        Scanner scanner = new Scanner(food);
        scanner.useDelimiter("[ ]");
        int m = 0;
        while (scanner.hasNext()) {
            m++;
            scanner.next();
        }
        return m;
    }
}
