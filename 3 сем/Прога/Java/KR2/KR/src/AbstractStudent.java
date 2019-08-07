import java.util.Objects;

public abstract class AbstractStudent implements Comparable<AbstractStudent> {
    protected String firstName;
    protected String lastName;

    public AbstractStudent(String firstName, String lastName) throws IncorrectInputDataException{
        if ((firstName == null) || (firstName == ""))
            throw new IncorrectInputDataException("Incorrect first name");
        if ((lastName == null) || (lastName == ""))
            throw new IncorrectInputDataException("Incorrect last name");
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IncorrectInputDataException {
        if ((firstName == null) || (firstName == ""))
            throw new IncorrectInputDataException("Incorrect first name");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws IncorrectInputDataException {
        if ((lastName == null) || (lastName == ""))
            throw new IncorrectInputDataException("Incorrect last name");
        this.lastName = lastName;
    }

    @Override
    public int compareTo(AbstractStudent right) {
        int comp1 = lastName.compareTo(right.lastName);
        if (comp1 == 0)
            return firstName.compareTo(right.firstName);
        return comp1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractStudent that = (AbstractStudent) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}