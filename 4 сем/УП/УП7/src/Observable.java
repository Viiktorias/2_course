import javafx.scene.input.KeyEvent;

public interface Observable {
    void addObserver(Observer o);

    void notifyObservers(KeyEvent k);
}
