package com.github.jokrkr.shopproject.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class LoginController {

    @FXML
    private Label loginStatusLabel;

    // Call this method after a successful login
    @FXML
    private void handleLogin() {
        try {
            // Load the main view
            Parent root = FXMLLoader.load(getClass().getResource("/com/github/jokrkr/shopproject/ui/views/main_view.fxml"));

            // Get the current stage and set the new scene
            Stage stage = (Stage) loginStatusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
