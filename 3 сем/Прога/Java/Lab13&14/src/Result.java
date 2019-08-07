import java.util.Objects;

public class Result {
    private String number;
    private String name;
    private String subject;
    private int mark;

    public Result(String number, String name, String subject, int mark) {
        if (number == null || number.equals("")){
            throw new IllegalArgumentException("Incorrect number");
        }
        if (name == null || name.equals("")){
            throw new IllegalArgumentException("Incorrect name");
        }
        if (subject == null || subject.equals("")){
            throw new IllegalArgumentException("Incorrect subject");
        }
        if ((mark < 0) || (mark > 10)){
            throw new IllegalArgumentException("Incorrect mark");
        }
        this.number = number;
        this.name = name;
        this.subject = subject;
        this.mark = mark;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(number).append(' ').append(name).append(' ').append(subject).append(' ').append(mark);
        return sb.toString();
    }

    public String toTag() {
        final StringBuilder sb = new StringBuilder("<Result>\n");
        sb.append("\t<Number>").append(number).append("</Number>\n");
        sb.append("\t<Name>").append(name).append("</Name>\n");
        sb.append("\t<Subject>").append(subject).append("</Subject>\n");
        sb.append("\t<Mark>").append(mark).append("</Mark>\n");
        sb.append("</Result>");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result result = (Result) o;
        return mark == result.mark &&
                Objects.equals(number, result.number) &&
                Objects.equals(name, result.name) &&
                Objects.equals(subject, result.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, subject, mark);
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public int getMark() {
        return mark;
    }
}
