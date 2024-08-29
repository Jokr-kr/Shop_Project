package com.github.jokrkr.shopproject.ui.controllers;

import com.github.jokrkr.shopproject.state.Session;
import com.github.jokrkr.shopproject.utils.PasswordHasher;
import com.github.jokrkr.shopproject.utils.SceneChanger;
import com.github.jokrkr.shopproject.utils.ServerCommunication;
import com.github.jokrkr.shopproject.utils.JsonFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.json.JSONObject;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginResponse;

    @FXML
    public void initialize() {
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendLoginRequest();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendLoginRequest();
            }
        });
    }

    public void sendLoginRequest() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            loginResponse.setText("Username and/or password is empty");
            return;
        }
        try {
            String hashedPassword = PasswordHasher.hashPassword(password);
            JSONObject loginJson = JsonFactory.createLoginJson(username, hashedPassword);

            String response = ServerCommunication.sendRequest("POST", "/login", null, loginJson);
            JSONObject responseJson = new JSONObject(response);

            String status = responseJson.getString("status");

            if ("SUCCESS".equalsIgnoreCase(status)) {
                String sessionId = responseJson.getString("sessionId");
                String userRole = responseJson.getString("role");
                Session.getInstance().setSessionId(sessionId);
                Session.getInstance().setUserRole(userRole);

                loginResponse.setText("Login successful!");
                SceneChanger.changeScene(loginResponse, "/com/github/jokrkr/shopproject/ui/views/MainView.fxml");
            } else {
                String errorMessage = responseJson.has("message") ? responseJson.getString("message") : "An unknown error occurred.";
                loginResponse.setText("Error: " + errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // todo better exception handling
            loginResponse.setText("An error occurred during login.");
        }
    }
}