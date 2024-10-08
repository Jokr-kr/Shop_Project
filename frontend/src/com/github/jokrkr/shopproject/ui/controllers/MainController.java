package com.github.jokrkr.shopproject.ui.controllers;

import com.github.jokrkr.shopproject.state.Session;
import com.github.jokrkr.shopproject.utils.ErrorHandler;
import com.github.jokrkr.shopproject.utils.SceneChanger;
import com.github.jokrkr.shopproject.utils.ServerCommunication;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private StackPane contentPane;

    @FXML
    private Button manageButton;

    @FXML
    public void initialize() {
        String userRole = Session.getUserRole();
        if (!"admin".equalsIgnoreCase(userRole) && !"moderator".equalsIgnoreCase(userRole)) {
            manageButton.setDisable(true);
        }
    }

    @FXML
    private void showManage() {
        SceneChanger.changeScene(contentPane, "/com/github/jokrkr/shopproject/ui/views/manage.fxml");
    }

    @FXML
    private void handleLogout() {
        try {
            String sessionId = Session.getSessionId();
            System.out.println("Attempting to log out with Session ID: " + sessionId);

            String response = ServerCommunication.sendRequest("DELETE", "/login", sessionId, null);

            System.out.println("Server response: " + response);

            SceneChanger.changeScene(contentPane, "/com/github/jokrkr/shopproject/ui/views/LoginView.fxml");

        } catch (Exception e) {
            ErrorHandler.logAndShowError(e, null, "An error occurred during logout.");
        }
    }
}