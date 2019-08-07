import java.util.Objects;

public abstract class AbstractCar implements Comparable<AbstractCar> {
    protected String name;
    protected String color;
    protected Fuel fuel;

    public AbstractCar(String name, String color, String fuel) throws IncorrectInputDataException {
        if ((name == null) || (name == ""))
            throw new IncorrectInputDataException("Incorrect name");
        if ((color == null) || (color == ""))
            throw new IncorrectInputDataException("Incorrect color");
        if ((fuel == null) || (fuel == ""))
            throw new IncorrectInputDataException("Incorrect fuel");
        this.name = name;
        this.color = color;
        switch (fuel) {
            case "бензин": {
                this.fuel = Fuel.GASOLINE;
                break;
            }
            case "дизель": {
                this.fuel = Fuel.DIESEL;
                break;
            }
            default: {
                throw new IncorrectInputDataException("Incorrect fuel");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCar that = (AbstractCar) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(color, that.color) &&
                fuel == that.fuel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, fuel);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(", ");
        sb.append(color).append(", ");
        sb.append(fuel.description);
        return sb.toString();
    }

    public abstract void print();

    @Override
    public int compareTo(AbstractCar o) {
        int diff = this.name.compareTo(o.name);
        if (diff == 0)
            return o.fuel.description.compareTo(this.fuel.description);
        return diff;
    }

    private enum Fuel {
        GASOLINE("бензин"), DIESEL("дизель");
        private String description;

        Fuel(String description) {
            this.description = description;
        }
    }
}
