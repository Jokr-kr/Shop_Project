package com.github.jokrkr.shopproject.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneChanger {

    public static void changeScene(Node node, String fxmlFile) {
        try {
            Scene currentScene = node.getScene();
            Parent newRoot = FXMLLoader.load(SceneChanger.class.getResource(fxmlFile));
            currentScene.setRoot(newRoot);
        } catch (Exception e) {
            e.printStackTrace();
            // todo better exception handling needed
        }
    }
}