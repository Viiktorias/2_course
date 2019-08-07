public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }
        try {
            System.out.println(Tokenizer.tokenize(args[0]));
        }
        catch (InvalidBracketSequenceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
