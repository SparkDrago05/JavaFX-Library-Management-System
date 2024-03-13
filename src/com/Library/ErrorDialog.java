package com.Library;

import javafx.scene.control.Dialog;

// Create and Error Dialog Showing An Error Occurred. I will use it to show in try catch instead of program crash I will show his Dialog
public class ErrorDialog {
    public static void show(String message) {
        // Set Icon to Error Icon and Show Button OK and hide titlebar
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Error");
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);
        dialog.initStyle(javafx.stage.StageStyle.UTILITY);
        // Set Error Icon
        javafx.scene.image.Image image = new javafx.scene.image.Image("error.png");
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        dialog.setGraphic(imageView);
        dialog.showAndWait();
    }
}
