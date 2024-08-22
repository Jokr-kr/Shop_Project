package com.github.jokrkr.shopproject.ui.controllers;

import com.github.jokrkr.shopproject.state.Session;

import com.github.jokrkr.shopproject.utils.SceneChanger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import com.github.jokrkr.shopproject.utils.ServerCommunication;


public class MainController {

    @FXML
    private StackPane contentPane;

    @FXML
    private void showPage1() {loadPage("/com/github/jokrkr/shopproject/ui/views/page1.fxml");}
    @FXML
    private void showPage2() {loadPage("/com/github/jokrkr/shopproject/ui/views/page2.fxml");}
    @FXML
    private void showPage3() {loadPage("/com/github/jokrkr/shopproject/ui/views/page3.fxml");}
    @FXML
    private void showPage4() {loadPage("/com/github/jokrkr/shopproject/ui/views/page4.fxml");}

    private void loadPage(String fxmlFile) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentPane.getChildren().setAll(page);
        } catch (Exception e) {
            e.printStackTrace();
            //todo better exception handling
        }
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
            e.printStackTrace();
            //todo better exception handling
        }
    }
}