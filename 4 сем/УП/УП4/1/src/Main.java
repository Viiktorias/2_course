import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        GridPane root = new GridPane();
        root.setGridLinesVisible(true);
        root.setPrefWidth(800);
        root.setPrefHeight(120);
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(60);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setPercentWidth(25);
        ColumnConstraints columnConstraints3 = new ColumnConstraints();
        columnConstraints3.setPercentWidth(15);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        rowConstraints.setValignment(VPos.TOP);
        root.getColumnConstraints().addAll(columnConstraints1, columnConstraints2, columnConstraints3);
        root.getRowConstraints().addAll(rowConstraints);
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        root.add(textArea, 0, 0);
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("Натуральное число", "Целое число", "Число с плавающей запятой", "Дата", "Время", "E-mail"));
        comboBox.setPrefWidth(Double.MAX_VALUE);
        TitledPane titledPane = new TitledPane("Type", comboBox);
        titledPane.setCollapsible(false);
        root.add(titledPane, 1, 0);
        Circle circle = new Circle();
        circle.setFill(Color.GREEN);
        BorderPane pane = new BorderPane(circle);
        pane.widthProperty().addListener(observable -> circle.setRadius(pane.getWidth() / 2.5));
        root.add(pane, 2, 0);
        textArea.textProperty().addListener((observableValue, s, t1) -> circle.setFill(Verifier.verify(t1, comboBox.getSelectionModel().getSelectedIndex()) ? Color.GREEN : Color.RED));
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> circle.setFill(Verifier.verify(textArea.getText(), comboBox.getSelectionModel().getSelectedIndex()) ? Color.GREEN : Color.RED));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Type verification");
        try (FileInputStream icon = new FileInputStream("src\\regexp.png")) {
            stage.getIcons().add(new Image(icon));
        } catch (IOException e) {
        }
        stage.sizeToScene();
        stage.show();
    }
}