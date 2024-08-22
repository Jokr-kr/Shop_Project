package com.github.jokrkr.shopproject.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class SceneChanger {

    public static void changeScene(Control control, String fxmlFile) {
        try {

            Scene currentScene = control.getScene();

            Parent newRoot = FXMLLoader.load(SceneChanger.class.getResource(fxmlFile));

            currentScene.setRoot(newRoot);

        } catch (Exception e) {
            e.printStackTrace();
           //todo better exception handling needed
        }
    }
}
