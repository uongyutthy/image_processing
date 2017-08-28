package com.imageprocessing.utility;

import javafx.scene.control.Alert;

public class PopUpMsg {
    public static void popupMsg(String title, String contentText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
