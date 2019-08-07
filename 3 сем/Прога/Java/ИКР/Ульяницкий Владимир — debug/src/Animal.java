import java.util.Objects;

public abstract class Animal implements Comparable<Animal> {
    private String name;
    private Position position;

    public Animal(String name, int position) throws IncorrectInputDataException {
        if (name == null || name.equals(""))
            throw new IncorrectInputDataException("Incorrect surname");
        switch (position) {
            case 1: {
                this.position = Position.AFRICA;
                break;
            }
            case 2: {
                this.position = Position.AMERICA;
                break;
            }
            case 3: {
                this.position = Position.EUROPE;
                break;
            }
            default: {
                throw new IncorrectInputDataException("Incorrect position");
            }
        }
        this.name = name;
    }

    public Animal() throws IncorrectInputDataException {
        this("", 0);
    }

    @Override
    public int compareTo(Animal o) {
        int dif = position.compareTo(o.position);
        if (dif == 0) {
            return name.compareToIgnoreCase(o.name);
        }
        return dif;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(' ');
        sb.append(position.toString()).append(' ');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return Objects.equals(position, animal.position) &&
                Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, name);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int position) throws IncorrectInputDataException {
        switch (position) {
            case 1: {
                this.position = Position.AFRICA;
                break;
            }
            case 2: {
                this.position = Position.AMERICA;
                break;
            }
            case 3: {
                this.position = Position.EUROPE;
                break;
            }
            default: {
                throw new IncorrectInputDataException("Incorrect position");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IncorrectInputDataException {
        if (name == null || name.equals(""))
            throw new IncorrectInputDataException("Incorrect surname");
        this.name = name;
    }

    public abstract int getMass();
}