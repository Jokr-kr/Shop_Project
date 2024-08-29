package com.github.jokrkr.shopproject.ui.controllers;

import com.github.jokrkr.shopproject.utils.JsonFactory;
import com.github.jokrkr.shopproject.utils.ServerCommunication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

public class CreateUserController {

    @FXML
    private PasswordField confirmField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label responseLabel;

    @FXML
    private void createUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPass = confirmField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            responseLabel.setText("Username and/or password is empty");
            return;
        }
        if (password.length() < 8) {
            responseLabel.setText("Password must be at least 8 characters long");
            return;
        }
        if (!password.equals(confirmPass)) {
            responseLabel.setText("Passwords do not match");
            return;
        }

        try {
            JSONObject userJson = JsonFactory.createLoginJson(username, password);
            String response = ServerCommunication.sendRequest("POST", "/createUser", null, userJson);
            JSONObject responseJson = new JSONObject(response);

            String status = responseJson.getString("status");

            if ("SUCCESS".equalsIgnoreCase(status)) {
                responseLabel.setText("User created successfully!");
            } else {
                String errorMessage = responseJson.has("message") ? responseJson.getString("message") : "An unknown error occurred.";
                responseLabel.setText("Error: " + errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //todo better exception handling
            responseLabel.setText("An error occurred during user creation.");
        }
    }
}