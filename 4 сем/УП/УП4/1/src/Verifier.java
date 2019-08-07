import java.util.regex.Pattern;

public class Verifier {
    private static Pattern naturalPattern = Pattern.compile("^[1-9]\\d*$");
    private static Pattern intPattern = Pattern.compile("^(?:(?:[-‐−‒–—]?[1-9]\\d*)|0)$");
    private static Pattern doublePattern = Pattern.compile("^(?:0|(?:[-‐−‒–—]?(?:(?:[1-9]\\d*(?:[.,]\\d*[1-9])?)|(?:0[,.]\\d*[1-9]))(?:[eе](?:[-‐−‒–—]?[1-9]\\d*))?))$");
    private static Pattern datePattern = Pattern.compile("^(?:(?:(?:(?:(?!0000)\\d{4})-" +
            "(?:(?:(?:(?:0[1-9])|(?:1[0-2]))[-‐−‒–—](?:(?:0[1-9])|(?:1\\d)|(?:2[0-8])))|(?:(?:(?:0[13-9])|(?:1[0-2]))[-‐−‒–—](?:(?:29)|(?:30)))|(?:(?:(?:0[13578])|(?:1[02]))[-‐−‒–—]31)))" +
            "|(?:(?:(?:(?:(?:[13579][26])|(?:[2468][048])|(?:0[48]))00)|(?:\\d{2}(?:(?:[13579][26])|(?:[2468][048])|(?:0[48]))))[-‐−‒–—]02[-‐−‒–—]29)" +
            "|(?:(?:(?:(?:(?:0[1-9])|(?:1\\d)|(?:2[0-8]))\\.(?:(?:0[1-9])|(?:1[0-2])))|(?:(?:(?:29)|(?:30))\\.(?:(?:0[13-9])|(?:1[0-2])))|(?:31\\.(?:(?:0[13578])|(?:1[02]))))" +
            "\\.(?:(?!0000)\\d{4}))" +
            "|(?:(?:(?:(?:(?:0?[1-9])|(?:1[0-2]))/(?:(?:0?[1-9])|(?:1\\d)|(?:2[0-8])))|(?:(?:(?:0?[13-9])|(?:1[0-2]))/(?:(?:29)|(?:30)))|(?:(?:(?:0?[13578])|(?:1[02]))/31))" +
            "/(?:(?!0000)\\d{4}))" +
            "|(?:(?:(?:29\\.02\\.)|(?:0?2/29/))(?:(?:(?:(?:[13579][26])|(?:[2468][048])|(?:0[48]))00)|(?:\\d{2}(?:(?:[13579][26])|(?:[2468][048])|(?:0[48]))))))" +
            "(?: ?(?:(?:BC|AD)|(?:(?:до )?(?:(?:н\\. ?э\\.)|(?:нашей эры)))))?)$");
    private static Pattern timePattern = Pattern.compile("^(?:(?:(?:[1-9]|(?:1[0-2]))[.:][0-5]\\d ?(?:(?:[ap]\\. ?m\\.)|(?:[AP]M)))" +
            "|(?:(?:(?:[01]\\d)|(?:2[0-3]))[.:][0-5]\\d))$");
    private static Pattern emailPattern = Pattern.compile("^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(?:\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[a-z0-9](?:[-a-z0-9]{0,61}[a-z0-9])?\\.)+" +
            "(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$");

    public static boolean verify(String text, int index) {
        if (text.length() == 0)
            return true;
        switch (index) {
            case 0:
                return naturalVerify(text);
            case 1:
                return intVerify(text);
            case 2:
                return doubleVerify(text);
            case 3:
                return dateVerify(text);
            case 4:
                return timeVerify(text);
            case 5:
                return emailVerify(text);
            default:
                return true;
        }
    }

    private static boolean naturalVerify(String text) {
        return naturalPattern.matcher(text).matches();
    }

    private static boolean intVerify(String text) {
        return intPattern.matcher(text).matches();
    }

    private static boolean doubleVerify(String text) {
        return doublePattern.matcher(text).matches();
    }

    private static boolean dateVerify(String text) {
        return datePattern.matcher(text).matches();
    }

    private static boolean timeVerify(String text) {
        return timePattern.matcher(text).matches();
    }

    private static boolean emailVerify(String text) {
        return emailPattern.matcher(text).matches();
    }
}
