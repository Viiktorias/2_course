import java.util.Objects;

public class Bus extends AbstractCar {
    private int doors;
    private int seats;

    public Bus(String name, String color, String fuel, int doors, int seats) throws IncorrectInputDataException {
        super(name, color, fuel);
        if (doors < 0)
            throw new IncorrectInputDataException("Incorrect doors count");
        if (seats < 0)
            throw new IncorrectInputDataException("Incorrect seats count");
        this.doors = doors;
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bus)) return false;
        if (!super.equals(o)) return false;
        Bus bus = (Bus) o;
        return doors == bus.doors &&
                seats == bus.seats;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), doors, seats);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bus: ");
        sb.append(super.toString()).append(", ");
        sb.append("дверей ").append(doors).append(", сидений ").append(seats);
        return sb.toString();
    }

    @Override
    public void print() {
        System.out.print(this.toString());
    }
}