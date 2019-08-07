import java.util.Objects;

public class Security extends Worker {
    private String guardedObject;

    public Security(int position, String surname, int salary, String guardedObject) throws IncorrectInputDataException {
        super(position, surname, salary);
        if (guardedObject == null || guardedObject.equals(""))
            throw new IncorrectInputDataException("Incorrect guarded object");
        this.guardedObject = guardedObject;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(' ').append(guardedObject);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Security)) return false;
        if (!super.equals(o)) return false;
        Security security = (Security) o;
        return Objects.equals(guardedObject, security.guardedObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), guardedObject);
    }

    public String getGuardedObject() {
        return guardedObject;
    }

    public void setGuardedObject(String guardedObject) throws IncorrectInputDataException {
        if (guardedObject == null || guardedObject.equals(""))
            throw new IncorrectInputDataException("Incorrect guarded object");
        this.guardedObject = guardedObject;
    }
}
