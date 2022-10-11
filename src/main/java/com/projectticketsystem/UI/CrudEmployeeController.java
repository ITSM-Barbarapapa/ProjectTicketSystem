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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ResourceBundle;

public class CrudEmployeeController extends BaseController implements Initializable {

    private static final UserService userService = new UserService();
    public TextField nameField;
    public PasswordField passwordField;
    public ChoiceBox<String> roleChoiceBox;
    private final ObservableList<User> users;
    @FXML
    TableView<User> employeesTableView;

    public CrudEmployeeController(){
        users = FXCollections.observableArrayList(userService.getAllUsers());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableView();
    }

    private void loadTableView(){
        employeesTableView.setItems(users);
    }

    @FXML
    public void onAddEmployeeButtonClick(ActionEvent actionEvent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        actionEvent.consume();
        if (nameField.getText().isEmpty() || passwordField.getText().isEmpty() || roleChoiceBox.getValue() == null){
            System.out.println("Please fill in all fields");
            return;
        }
        String name = nameField.getText();
        String role = roleChoiceBox.getValue();


        User user = new User(userService.getNextUserId(), name, hashPassword(), Role.valueOf(role));
        userService.addUser(user);
        users.add(user);
        loadTableView();
    }

    @FXML
    public void onDeleteEmployeeButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        User user = employeesTableView.getSelectionModel().getSelectedItem();
        if (user == null){
            System.out.println("Please select an employee");
            return;
        }
        userService.deleteUser(user);
        users.remove(user);
        loadTableView();
    }

    @FXML
    public void onUpdateEmployeeButtonClick(ActionEvent actionEvent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        actionEvent.consume();
        if (nameField.getText().isEmpty() || passwordField.getText().isEmpty() || roleChoiceBox.getValue() == null
                || employeesTableView.getSelectionModel().getSelectedItem() == null){
            System.out.println("Please fill in all fields");
            return;
        }
        //get selected user
        User user = userService.getUser(employeesTableView.getSelectionModel().getSelectedItem().getId());

        users.remove(user);

        //set the new data
        user.setPassword(hashPassword());
        user.setName(nameField.getText());
        user.setRole(Role.valueOf(roleChoiceBox.getValue()));

        //update the user
        userService.updateUser(user);
        users.add(user);
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

    @FXML
    public void onNewSelect(MouseEvent mouseEvent) {
        mouseEvent.consume();
        if (employeesTableView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        User user = employeesTableView.getSelectionModel().getSelectedItem();
        nameField.setText(user.getName());
        passwordField.setText("");
        roleChoiceBox.setValue(user.getRole().toString());
    }
}
