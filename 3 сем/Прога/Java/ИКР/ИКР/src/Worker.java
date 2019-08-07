import java.util.Objects;

public abstract class Worker implements Comparable<Worker> {
    private Position position;
    private String surname;
    private int salary;

    public Worker(int position, String surname, int salary) throws IncorrectInputDataException {
        if (surname == null || surname.equals(""))
            throw new IncorrectInputDataException("Incorrect surname");
        if (salary <= 0)
            throw new IncorrectInputDataException("Incorrect salary");
        switch (position) {
            case 1: {
                this.position = Position.FIRST;
                break;
            }
            case 2: {
                this.position = Position.SECOND;
                break;
            }
            case 3: {
                this.position = Position.THIRD;
                break;
            }
            default: {
                throw new IncorrectInputDataException("Incorrect position");
            }
        }
        this.surname = surname;
        this.salary = salary;
    }

    public Worker() throws IncorrectInputDataException {
        this(0, "", 0);
    }

    @Override
    public int compareTo(Worker o) {
        return surname.compareToIgnoreCase(o.surname);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(position.toString()).append(' ');
        sb.append(surname).append(' ');
        sb.append(salary);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return Objects.equals(position, worker.position) &&
                Objects.equals(surname, worker.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, surname, salary);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int position) throws IncorrectInputDataException {
        switch (position) {
            case 1: {
                this.position = Position.FIRST;
                break;
            }
            case 2: {
                this.position = Position.SECOND;
                break;
            }
            case 3: {
                this.position = Position.THIRD;
                break;
            }
            default: {
                throw new IncorrectInputDataException("Incorrect position");
            }
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws IncorrectInputDataException {
        if (surname == null || surname.equals(""))
            throw new IncorrectInputDataException("Incorrect surname");
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) throws IncorrectInputDataException {
        if (salary <= 0)
            throw new IncorrectInputDataException("Incorrect salary");
        this.salary = salary;
    }
}