public class IncorrectInputDataException extends Exception {
    public IncorrectInputDataException() {
        super();
    }

    public IncorrectInputDataException(String message) {
        super(message);
    }

    public String getGetMessage() {
        return super.getMessage();
    }
}