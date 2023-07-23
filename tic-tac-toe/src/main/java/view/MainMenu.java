package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class MainMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setPrefHeight(600);
        root.setPrefWidth(600);
        Button start = new Button("Start");
        start.setOnMouseClicked(event -> {
            try {
                new GameMenu().start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        Button exit = new Button("Exit");
        exit.setOnMouseClicked(e -> stage.close());
        HBox hbox = new HBox(start,exit);
        root.getChildren().add(hbox);
        hbox.setLayoutX(250);
        hbox.setLayoutY(250);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
