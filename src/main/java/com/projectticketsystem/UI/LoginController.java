package com.projectticketsystem.UI;

import com.projectticketsystem.Model.Role;
import com.projectticketsystem.Model.User;
import com.projectticketsystem.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class LoginController extends BaseController {
    @FXML
    public TextField userIdField;
    @FXML
    public VBox vBoxLogin;
    @FXML
    public Label errorLabel;
    @FXML
    public PasswordField passwordField;
    private User user;

    @FXML
    private void onLoginButtonClicked(ActionEvent event) throws IOException {
        System.out.println("Username: " + userIdField.getText());
        System.out.println("Password: " + passwordField.getText());

        if (checkPassword()) {
            //Check if user is admin or regular employee or Service Desk employee
            if (user.getRole() == Role.Administrator || user.getRole() == Role.ServiceDeskEmployee) {
                loadNextStage("dashboard-view.fxml", null, event);
            } else if (user.getRole() == Role.RegularEmployee) {
                loadNextStage("crud-employee-view.fxml", new CrudEmployeeController(user), event);
            }
        } else {
            errorLabel.setText("Gebruikerscode of wachtwoord is onjuist");
        }
    }

    private boolean checkPassword() {
        int userID = tryParseInt(userIdField.getText());

        if (userID == -1) {
            return false;
        }

        //get user from database
        UserService userService = new UserService();
        user = userService.getUser(userID);

        try {
            //get hash password
            byte[] salt = user.getPassword().getSalt();
            byte[] hash = user.getPassword().getHashPassword();

            KeySpec spec = new PBEKeySpec(passwordField.getText().toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash2 = factory.generateSecret(spec).getEncoded();

            //check if hashes are equal
            return Arrays.equals(hash, hash2);
        } catch (Exception e) {
            errorLabel.setText("Er is iets fout gegaan bij het checken van uw wachtwoord");
            return false;
        }
    }

    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            errorLabel.setText("Gebruikerscode is geen nummer");
            return -1;
        }
    }
}
