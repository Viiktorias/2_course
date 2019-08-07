import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MyUtils {
    public static String getText(KeyEvent ke) {
        KeyCode code = ke.getCode();
        if (code.equals(KeyCode.UNDEFINED)) {
            switch (ke.getText()) {
                case "Ж":
                case "ж": {
                    code = KeyCode.SEMICOLON;
                    break;
                }
                case "Э":
                case "э": {
                    code = KeyCode.QUOTE;
                    break;
                }
                case "Ё":
                case "ё": {
                    code = KeyCode.BACK_QUOTE;
                    break;
                }
                case "Б":
                case "б": {
                    code = KeyCode.COMMA;
                    break;
                }
                case "Ю":
                case "ю": {
                    code = KeyCode.PERIOD;
                    break;
                }
            }
        }
        return code.getName();
    }
}
