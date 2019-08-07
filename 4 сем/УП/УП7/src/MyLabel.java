import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class MyLabel extends Label implements Observer {
    public MyLabel() {
       setText("PRESS F");
    }

    @Override
    public void handle(KeyEvent k) {
        String text = MyUtils.getText(k).toUpperCase();
        setText(text);
    }
}
