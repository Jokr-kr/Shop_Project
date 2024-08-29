package com.github.jokrkr.shopproject.utils;

import javafx.scene.control.Label;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorHandler {

    private static final Logger logger = Logger.getLogger(ErrorHandler.class.getName());

    public static void logAndShowError(Exception e, Label responseLabel, String userMessage) {
        logger.log(Level.SEVERE, userMessage, e);
        if (responseLabel != null) {
            responseLabel.setText(userMessage);
        }
    }
}