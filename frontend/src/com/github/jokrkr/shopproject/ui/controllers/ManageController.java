package com.github.jokrkr.shopproject.ui.controllers;

import com.github.jokrkr.shopproject.utils.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ManageController {

    @FXML
    private VBox managePane;

    @FXML
    private void showCreateUser() {
        SceneChanger.changeScene(managePane, "/com/github/jokrkr/shopproject/ui/views/create_user.fxml");
    }
}