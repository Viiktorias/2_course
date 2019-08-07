import java.util.StringTokenizer;

public class Tokenizer {
    public static String tokenize(String string) throws InvalidBracketSequenceException {
        StringTokenizer stringTokenizer = new StringTokenizer(string, "()", true);
        int col = 0;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder cur;
        Boolean add = false;
        StringBuilder prev = new StringBuilder();
        while (stringTokenizer.hasMoreTokens()) {
            cur = new StringBuilder(stringTokenizer.nextToken());
            if (cur.toString().equals("(")) {
                if (col == 1)
                    stringBuilder.append(prev);
                col++;
                add = false;
            } else if (cur.toString().equals(")")) {
                if (col == 0)
                    throw new InvalidBracketSequenceException("Closing bracket without opening");
                if (add)
                    stringBuilder.append(prev);
                col--;
                add = true;
            } else {
                if (col != 1) {
                    stringBuilder.append(cur);
                    prev = new StringBuilder();
                }
                else prev = cur;
            }
        }
        if (col != 0)
            throw new InvalidBracketSequenceException("Opening bracket without closing");
        return new String(stringBuilder);
    }
}