import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
        //root.setGridLinesVisible(true);
        root.setPrefWidth(800);
        root.setPrefHeight(120);
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(70);
        columnConstraints1.setHalignment(HPos.CENTER);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setPercentWidth(30);
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setPrefHeight(Integer.MAX_VALUE);
        rowConstraints1.setValignment(VPos.TOP);
        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setPrefHeight(40);
        RowConstraints rowConstraints3 = new RowConstraints();
        rowConstraints3.setMinHeight(5);
        rowConstraints3.setMaxHeight(5);
        rowConstraints1.setValignment(VPos.CENTER);
        root.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        root.getRowConstraints().addAll(rowConstraints1, rowConstraints3, rowConstraints2, rowConstraints3);
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setText("2018-10-10 BC  пять 9/11/1999 н.э. четыре  07.01.1111 до нашей эры 1999-09-11 три BC два 29.02.2018 раз");
        root.add(textArea, 0, 0);
        ListView<String> listView = new ListView<>();
        TitledPane titledPane = new TitledPane("Dates", listView);
        titledPane.setPrefHeight(Integer.MAX_VALUE);
        titledPane.setCollapsible(false);
        root.add(titledPane, 1, 0, 1, 4);
        Button button = new Button("Parse");
        button.setPrefWidth(100);
        root.add(button, 0, 2);
        button.setOnAction(event -> listView.setItems(FXCollections.observableArrayList(Parser.parse(textArea.getText()))));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Parsing");
        try (FileInputStream icon = new FileInputStream("src\\regexp.png")) {
            stage.getIcons().add(new Image(icon));
        } catch (IOException e) {
        }
        stage.sizeToScene();
        stage.show();
    }
}