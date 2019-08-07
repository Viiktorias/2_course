import java.util.Objects;

public class Scoolkid extends AbstractStudent {
    private int classNumber;
    private Behaviours behaviours;

    public Scoolkid(String name, String establishment, double mark, int classNumber, String behaviours) throws IllegalArgumentException {
        super(name, establishment, mark);
        if ((classNumber<0)||(classNumber>11))
            throw new IllegalArgumentException("Incorrect class number");
        this.classNumber = classNumber;
        switch (behaviours) {
            case "Excellent": {
                this.behaviours = Behaviours.EXCELLENT;
                break;
            }
            case "Well": {
                this.behaviours = Behaviours.WELL;
                break;
            }
            case "Bad": {
                this.behaviours = Behaviours.BAD;
                break;
            }
            default: {
                throw new IllegalArgumentException("Incorrect behaviours");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scoolkid)) return false;
        if (!super.equals(o)) return false;
        Scoolkid scoolkid = (Scoolkid) o;
        return classNumber == scoolkid.classNumber &&
                behaviours == scoolkid.behaviours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), classNumber, behaviours);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ");
        sb.append(establishment).append(" ");
        sb.append(mark).append(" ");
        sb.append(classNumber).append(" ");
        sb.append(behaviours.description);
        return sb.toString();
    }

    private enum Behaviours {
        EXCELLENT("Excellent"), WELL("Well"), BAD("Bad");
        private String description;

        Behaviours(String description) {
            this.description = description;
        }
    }
}
