package com.github.jokrkr.shopproject.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

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
        //todo logout logic
    }
}