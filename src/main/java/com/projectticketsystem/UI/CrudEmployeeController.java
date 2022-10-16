package com.projectticketsystem.UI;

import com.projectticketsystem.Model.HashedPassword;
import com.projectticketsystem.Model.Role;
import com.projectticketsystem.Model.User;
import com.projectticketsystem.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Collections;
import java.util.ResourceBundle;

public class CrudEmployeeController extends BaseController implements Initializable {

    private static final UserService userService = new UserService();
    private final User user;
    private final ObservableList<User> users;
    @FXML
    public TextField nameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public ChoiceBox<String> roleChoiceBox;
    @FXML
    public Label errorLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    TableView<User> employeesTableView;
    private User selectedUser;

    public CrudEmployeeController(User user) {
        this.user = user;
        users = FXCollections.observableArrayList(userService.getAllUsers());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeesTableView.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            selectedUser = employeesTableView.getSelectionModel().getSelectedItem();
            nameField.setText(selectedUser.getName());
            passwordField.setText("");
            roleChoiceBox.setValue(selectedUser.getRole().toString());
        });
        loadTableView();
        usernameLabel.setText(user.getName());
    }

    private void loadTableView() {
        Collections.sort(users);
        employeesTableView.setItems(users);
    }

    @FXML
    public void onAddEmployeeButtonClick(ActionEvent actionEvent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        actionEvent.consume();
        if (checkEmptyFields()) {
            return;
        }
        String name = nameField.getText();
        String role = roleChoiceBox.getValue();


        User newUser = new User(userService.getNextUserId(), name, hashPassword(), Role.valueOf(role));
        userService.addUser(newUser);
        users.add(newUser);
        loadTableView();
    }

    @FXML
    public void onDeleteEmployeeButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (selectedUser == null) {
            errorLabel.setText("Please select an employee");
            return;
        }
        userService.deleteUser(selectedUser);
        users.remove(selectedUser);
        loadTableView();
    }

    @FXML
    public void onUpdateEmployeeButtonClick(ActionEvent actionEvent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        actionEvent.consume();
        if (employeesTableView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Please select an employee");
            return;
        }

        User updatedUser = selectedUser;


        //set the new data
        updatedUser.setPassword(hashPassword());
        updatedUser.setName(nameField.getText());
        updatedUser.setRole(Role.valueOf(roleChoiceBox.getValue()));

        //remove selected user from tableview
        users.remove(selectedUser);

        //update the user
        userService.updateUser(updatedUser);
        users.add(updatedUser);
        loadTableView();
    }

    private HashedPassword hashPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        //create salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        //hash password
        KeySpec spec = new PBEKeySpec(passwordField.getText().toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return new HashedPassword(hash, salt);
    }


    private boolean checkEmptyFields() {
        if (nameField.getText().isEmpty() || passwordField.getText().isEmpty() || roleChoiceBox.getValue() == null) {
            errorLabel.setText("Please fill in all fields");
            checkField(nameField);
            checkField(passwordField);
            return true;
        }
        return false;
    }

    private void checkField(TextField field) {
        if (field.getText().isEmpty()) {
            field.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        } else {
            field.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }
}
