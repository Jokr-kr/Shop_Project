package com.github.jokrkr.shopproject.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class SceneChanger {


    public static void changeScene(Control control, String fxmlFilePath) {
        try {
            Parent root = FXMLLoader.load(SceneChanger.class.getResource(fxmlFilePath));

            Stage stage = (Stage) control.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
           //todo better exception handling needed
        }
    }
}