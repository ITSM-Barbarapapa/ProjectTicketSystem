package com.projectticketsystem.UI;

import com.projectticketsystem.DAL.UserDAO;
import com.projectticketsystem.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class LoginController extends BaseController {
    private User user;
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
            //Check if user is admin or regular employee or Service Desk employee
            switch (user.getRole()) {
                case Administrator, ServiceDeskEmployee ->
                    //Load service desk employee view
                    //Load admin view
                        loadNextStage("login-view.fxml", new LoginController(), event);
                case RegularEmployee ->
                    //Load regular employee view
                        loadNextStage("crud-employee-view.fxml", new crudEmployeeController(), event);
            }


        }else {
            System.out.println("Login failed");
        }

    }

    private boolean checkPassword() {
        //password = "Wachtwoord"

        int userID = tryParseInt(usernameField.getText());

        if (userID == -1){
            return false;
        }

        //get user from database
        UserDAO userDAO = new UserDAO();
        user = userDAO.getUser(userID);

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
            System.out.println("An error occurred when checking the password" + e.getMessage());
            return false;
        }
    }

    private int tryParseInt(String value){
        try{
            return Integer.parseInt(value);
        }catch (NumberFormatException e){
            return -1;
        }
    }
}
