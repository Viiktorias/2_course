package my.excel;


import my.cell.FuncFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyParser {
    private static Pattern intPattern = Pattern.compile("^(?:(?:-?[1-9]\\d*)|0)$");
    private static Pattern likeDatePattern = Pattern.compile("^(?:(?:\\d{2}\\.\\d{2}\\.\\d{4})|(?:\\d{1,2}/\\d{1,2}/\\d{4}))$");
    private static Pattern datePattern = Pattern.compile("^(?:(?:(?:(?:(?:(?:0[1-9])|(?:1\\d)|(?:2[0-8]))\\.(?:(?:0[1-9])|(?:1[0-2])))|(?:(?:(?:29)|(?:30))\\.(?:(?:0[13-9])|(?:1[0-2])))|(?:31\\.(?:(?:0[13578])|(?:1[02]))))" +
            "\\.(?:(?!0000)\\d{4}))" +
            "|(?:(?:(?:(?:(?:[1-9])|(?:1[0-2]))/(?:(?:[1-9])|(?:1\\d)|(?:2[0-8])))|(?:(?:(?:[13-9])|(?:1[0-2]))/(?:(?:29)|(?:30)))|(?:(?:(?:[13578])|(?:1[02]))/31))" +
            "/(?:(?!0000)\\d{4}))" +
            "|(?:(?:(?:29\\.02\\.)|(?:2/29/))(?:(?:(?:(?:[13579][26])|(?:[2468][048])|(?:0[48]))00)|" +
            "(?:\\d{2}(?:(?:[13579][26])|(?:[2468][048])|(?:0[48]))))))$");
    private static Pattern likeLinkPattern = Pattern.compile("^(?:[A-Z]\\d{1,3})$");
    private static Pattern linkPattern = Pattern.compile("^(?:[A-Z](?:(?:[1-9])|(?:[1-9]\\d)|(?:1[01]\\d)|(?:12[0-8])))$");
    private static Pattern sumPattern = Pattern.compile("^(?:(?:[A-Z]\\d{1,3})|(?:(?:\\d{2}\\.\\d{2}\\.\\d{4})|(?:\\d{1,2}/\\d{1,2}/\\d{4}))|(?:(?:-?[1-9]\\d*)|0))(?:[-+](?:(?:[A-Z]\\d{1,3})|(?:(?:\\d{2}\\.\\d{2}\\.\\d{4})|(?:\\d{1,2}/\\d{1,2}/\\d{4}))|(?:(?:-?[1-9]\\d*)|0)))*$");
    private static Pattern parseSumPattern = Pattern.compile("(?:[^-+]+)|(?:[-+])");
    private static Pattern minMaxPattern = Pattern.compile("^(?:MIN|MAX)\\((?:(?:(?:[A-Z]\\d{1,3}):(?:[A-Z]\\d{1,3}))|(?:(?:(?:[A-Z]\\d{1,3})|(?:(?:\\d{2}\\.\\d{2}\\.\\d{4})|(?:\\d{1,2}/\\d{1,2}/\\d{4}))|(?:(?:-?[1-9]\\d*)|0));(?:(?:[A-Z]\\d{1,3})|(?:(?:\\d{2}\\.\\d{2}\\.\\d{4})|(?:\\d{1,2}/\\d{1,2}/\\d{4}))|(?:(?:-?[1-9]\\d*)|0))))" +
            "(?:;(?:(?:(?:[A-Z]\\d{1,3}):(?:[A-Z]\\d{1,3}))|(?:(?:[A-Z]\\d{1,3})|(?:(?:\\d{2}\\.\\d{2}\\.\\d{4})|(?:\\d{1,2}/\\d{1,2}/\\d{4}))|(?:(?:-?[1-9]\\d*)|0))))*\\)$");
    private static Pattern parseMinMaxPattern = Pattern.compile("(?:[^;:]+)|(?:[;:])");


    public static boolean intVerify(String text) {
        return intPattern.matcher(text).matches();
    }

    public static boolean smellsLikeDate(String text) {
        return likeDatePattern.matcher(text).matches();
    }

    public static boolean smellsLikeLink(String text) {
        return likeLinkPattern.matcher(text).matches();
    }

    public static boolean dateVerify(String text) {
        return datePattern.matcher(text).matches();
    }

    public static boolean linkVerify(String text) {
        return linkPattern.matcher(text).matches();
    }


    private static boolean sumVerify(String text) {
        return sumPattern.matcher(text).matches();
    }

    private static boolean minMaxVerify(String text) {
        return minMaxPattern.matcher(text).matches();
    }

    private static List<String> parseSum(String text) {
        List<String> list = new ArrayList<>();
        list.add("SUM");
        Matcher matcher = parseSumPattern.matcher(text);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    private static List<String> parseMinMax(String text) {
        List<String> list = new ArrayList<>();
        if (text.charAt(2) == 'X')
            list.add("MAX");
        else list.add("MIN");
        Matcher matcher = parseMinMaxPattern.matcher(text.substring(4, text.length() - 1));
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    public static List<String> parseFunc(String text) throws FuncFormatException {
        List<String> tokens;
        if (sumVerify(text)) {
            tokens = parseSum(text);
        } else if (minMaxVerify(text)) {
            tokens = parseMinMax(text);
        } else
            throw new FuncFormatException("Запись не является формулой");
        int size = tokens.size();
        for (int i = 0; i < size; i++) {
            if ((smellsLikeLink(tokens.get(i))) && (!linkVerify(tokens.get(i))))
                throw new FuncFormatException("Ячейка " + tokens.get(i) + " не существует");
            else if ((smellsLikeDate(tokens.get(i))) && (!dateVerify(tokens.get(i))))
                throw new FuncFormatException("Дата " + tokens.get(i) + " не существует");
        }
        return tokens;
    }
}