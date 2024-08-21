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

import java.io.OutputStream;
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

    public void TryToLogIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            loginResponse.setText("Username and/or password is empty");
            return;
        }

        try {
            String hashedPassword = PasswordHasher.hashPassword(password);

            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("username", username);
            jsonPayload.put("password", hashedPassword);

            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

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
            loginResponse.setText("An error occurred during login.");
        }
    }
}
