package com.github.jokrkr.shopproject.ui.controllers;

import com.github.jokrkr.shopproject.state.Session;
import com.github.jokrkr.shopproject.utils.PasswordHasher;
import com.github.jokrkr.shopproject.utils.ResponseParser;

import com.github.jokrkr.shopproject.utils.SceneChanger;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginResponse;

    //todo add login-attempts and a lockout.

    public void TryToLogIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            loginResponse.setText("Username and/or password is empty");
            return;
        }

        try {
            String hashedPassword = PasswordHasher.hashPassword(password);

            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
            String encodedPassword = URLEncoder.encode(hashedPassword, StandardCharsets.UTF_8);

            URI uri = new URI(String.format("http://localhost:8080/login?username=%s&password=%s", encodedUsername, encodedPassword));
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            JSONObject responseJson = ResponseParser.parseResponse(connection);

            if (responseCode == 200) {
                String sessionId = responseJson.getString("sessionId");
                Session.getInstance().setSessionId(sessionId);

                loginResponse.setText("Login successful!");

                SceneChanger.changeScene(loginResponse, "/com/github/jokrkr/shopproject/ui/views/main_view.fxml");

            } else {
                String errorMessage = responseJson.has("message") ? responseJson.getString("message") : "An unknown error occurred.";
                loginResponse.setText("Error: " + errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //todo better exception handling needed
            loginResponse.setText("An error occurred during login.");
        }
    }
}
