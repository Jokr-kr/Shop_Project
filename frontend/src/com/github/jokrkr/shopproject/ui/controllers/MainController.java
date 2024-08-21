package com.github.jokrkr.shopproject.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentPane;

    private void loadPage(String fxmlPath) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
            //todo better exception handling needed
        }
    }

    //list of available pages
    @FXML
    public void showPage1() {loadPage("/com/github/jokrkr/shopproject/ui/views/Page1.fxml");}
    @FXML
    public void showPage2() {loadPage("/com/github/jokrkr/shopproject/ui/views/Page2.fxml");}
    @FXML
    public void showPage3() {loadPage("/com/github/jokrkr/shopproject/ui/views/Page3.fxml");}
    @FXML
    public void showPage4() {loadPage("/com/github/jokrkr/shopproject/ui/views/LoginView.fxml");}


}
