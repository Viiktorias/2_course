import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class MyPane extends GridPane implements Observable {
    private List<Observer> observers;

    public MyPane() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(KeyEvent k) {
        observers.forEach(o -> o.handle(k));
    }
}
