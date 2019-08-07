import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

public class MyListView extends ListView<String> implements Observer {
    public MyListView() {
        this.setFocusTraversable(false);
    }

    @Override
    public void handle(KeyEvent k) {
        String text = MyUtils.getText(k);
        getItems().add(0, "Key Pressed: " + text);
    }
}
