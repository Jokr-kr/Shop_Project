package com.github.jokrkr.shopproject.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/github/jokrkr/shopproject/ui/views/LoginView.fxml"));

            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root));

            primaryStage.setMaximized(true);
            primaryStage.show();

            primaryStage.setFullScreen(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            //todo better exception handling
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
