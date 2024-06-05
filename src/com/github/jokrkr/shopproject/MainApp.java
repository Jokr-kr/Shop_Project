package com.github.jokrkr.shopproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set up your primary stage and scene here
        primaryStage.setTitle("My JavaFX Application");

        StackPane root = new StackPane();
        root.getChildren().add(new Label("Hello, JavaFX!"));

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application
    }
}