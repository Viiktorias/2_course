import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * A sample that demonstrates various key events and their usage. Type in the
 * text box to view the triggered events: key pressed, key typed and key
 * released. Pressing the Shift, Ctrl, and Alt keys also trigger events.
 *
 * @see javafx.scene.input.KeyCode
 * @see javafx.scene.input.KeyEvent
 * @see javafx.event.EventHandler
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private void init(Stage stage) {
        MyPane root = new MyPane();
        root.setPrefWidth(800);
        root.setPrefHeight(400);
        MyListView console = new MyListView();
        MyLabel lastKey = new MyLabel();
        lastKey.setFocusTraversable(true);
        lastKey.setFont(new Font("BodoniCameoC", 72));
        root.addObserver(console);
        root.addObserver(lastKey);
        root.setOnKeyPressed(root::notifyObservers);
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(70);
        columnConstraints1.setHalignment(HPos.CENTER);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setPercentWidth(30);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.CENTER);
        rowConstraints.setPercentHeight(100);
        root.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        root.getRowConstraints().addAll(rowConstraints);
        root.add(lastKey, 0, 0);
        root.add(console, 1, 0);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Key Logger");
        try (FileInputStream icon = new FileInputStream("src\\logger.png")) {
            stage.getIcons().add(new Image(icon));
        } catch (IOException e) {
        }
        stage.sizeToScene();
    }

    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        primaryStage.show();
    }
}