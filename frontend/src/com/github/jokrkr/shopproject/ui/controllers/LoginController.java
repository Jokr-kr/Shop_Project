package com.github.jokrkr.shopproject.ui.controllers;

import com.github.jokrkr.shopproject.utils.PasswordHasher;
import com.github.jokrkr.shopproject.utils.ResponseParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.net.HttpURLConnection;
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

    public void TryToLogIn(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            loginResponse.setText("Username or password is incorrect");
            return;
        }

        try {
            String hashedPassword = PasswordHasher.hashPassword(password);

            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
            String encodedPassword = URLEncoder.encode(hashedPassword, StandardCharsets.UTF_8.toString());

            String urlString = String.format("http://localhost:8080/login?username=%s&password=%s", encodedUsername, encodedPassword);
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            JSONObject response = ResponseParser.parseResponse(connection);

           //todo  handleResponse(responseCode, response);

        } catch (Exception e) {
            loginResponse.setText("Failed to connect to the server.");
            e.printStackTrace();
        }
    }
}
