package com.projectticketsystem.UI;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginController {
    @FXML
    TextField usernameField;

    @FXML
    VBox vBoxLogin;

    @FXML
    PasswordField passwordField;

    @FXML
    private void onLoginButtonClicked() {
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());
    }



}
