package com.projectticketsystem.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class LoginController {
    @FXML
    TextField usernameField;

    @FXML
    VBox vBoxLogin;

    @FXML
    PasswordField passwordField;

    @FXML
    private void onLoginButtonClicked(ActionEvent event) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        // Go to next window
        // get current Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // load new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("<NEWWINDOW>-view.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();





        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());
    }

    private boolean checkPassword() throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] salt = {-83, -91, -112, -10, 119, -120, -74, -60, 72, 42, -76, -52, -104, 94, -113, 75};
        byte[] passwordHash = {-24, 22, -106, -117, -114, -74, 78, 46, 108, 119, 22, -26, -117, -64, 11, 85};

        KeySpec spec = new PBEKeySpec(passwordField.getText().toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] currentHash = factory.generateSecret(spec).getEncoded();

        return Arrays.equals(passwordHash, currentHash);
    }
}
