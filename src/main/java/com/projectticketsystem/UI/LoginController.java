package com.projectticketsystem.UI;

import com.projectticketsystem.DAL.UserDAO;
import com.projectticketsystem.Model.Role;
import com.projectticketsystem.Model.User;
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
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());

        if (checkPassword()){
            System.out.println("Login successful");
            // Go to next window
            // get current Stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // load new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-employee-view.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.show();

        }else {
            System.out.println("Login failed");
        }

    }

    private boolean checkPassword() throws InvalidKeySpecException, NoSuchAlgorithmException {
        //password = "Wachtwoord"
        byte[] salt = {-83, -91, -112, -10, 119, -120, -74, -60, 72, 42, -76, -52, -104, 94, -113, 75};
        byte[] passwordHash = {-24, 22, -106, -117, -114, -74, 78, 46, 108, 119, 22, -26, -117, -64, 11, 85};
        User user = new User(1, "test", passwordHash, salt, Role.RegularEmployee);

        //get user from database
        UserDAO userDAO = new UserDAO();
        user = userDAO.getUser(1001);


        KeySpec spec = new PBEKeySpec(passwordField.getText().toCharArray(), user.getPassword().getSalt(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] currentHash = factory.generateSecret(spec).getEncoded();

        return Arrays.equals(user.getPassword().getHashedPassword(), currentHash);
    }
}
