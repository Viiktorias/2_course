import java.util.Objects;

public class Master extends AbstractStudent {
    private String speciality;

    public Master(String firstName, String lastName, String speciality) throws IncorrectInputDataException {
        super(firstName, lastName);
        if ((speciality == null) || (speciality == ""))
            throw new IncorrectInputDataException("Incorrect speciality");
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) throws IncorrectInputDataException{
        if ((speciality == null) || (speciality == ""))
            throw new IncorrectInputDataException("Incorrect speciality");
        this.speciality = speciality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Master)) return false;
        if (!super.equals(o)) return false;
        Master master = (Master) o;
        return Objects.equals(speciality, master.speciality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speciality);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Магистрант ");
        sb.append(firstName).append(" ");
        sb.append(lastName).append(", специальность ");
        sb.append(speciality);
        return sb.toString();
    }
}
