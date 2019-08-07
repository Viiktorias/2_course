import java.util.Objects;

public class Car extends AbstractCar {
    private Material material;

    public Car(String name, String color, String fuel, String material) throws IncorrectInputDataException {
        super(name, color, fuel);
        switch (material) {
            case "кожа": {
                this.material = Material.LEATHER;
                break;
            }
            case "кожзам": {
                this.material = Material.SYNTHETICS;
                break;
            }
            default: {
                throw new IncorrectInputDataException("Incorrect material");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return material == car.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), material);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car: ");
        sb.append(super.toString()).append(", сиденья ");
        sb.append(material.description);
        return sb.toString();
    }

    @Override
    public void print() {
        System.out.print(this.toString());
    }

    private enum Material {
        LEATHER("кожа"), SYNTHETICS("кожзам");
        private String description;

        Material(String description) {
            this.description = description;
        }
    }
}
