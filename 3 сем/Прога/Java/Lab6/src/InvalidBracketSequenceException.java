public class InvalidBracketSequenceException extends Exception {
    InvalidBracketSequenceException (String message) {
        super(message);
    }
    InvalidBracketSequenceException () {
        super();
    }
    @Override public String getMessage() {
       return super.getMessage();
    }
}
