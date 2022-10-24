package com.projectticketsystem.ui;

import com.projectticketsystem.model.HashedPassword;
import com.projectticketsystem.model.Role;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
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
            if (selectedUser != null) {
                nameField.setText(selectedUser.getName());
                passwordField.setText("");
                roleChoiceBox.setValue(selectedUser.getRole().name);
            }
        });
        loadTableView();
        usernameLabel.setText(user.getName());
        roleChoiceBox.setItems(FXCollections.observableArrayList(Role.RegularEmployee.name, Role.ServiceDeskEmployee.name, Role.Administrator.name));
        roleChoiceBox.setValue(Role.RegularEmployee.name);
    }

    @FXML
    public void onAddEmployeeButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (checkEmptyFields()) {
            return;
        }
        String name = nameField.getText();
        Role role = Role.valueOf(Role.values()[roleChoiceBox.getItems().indexOf(roleChoiceBox.getValue())].toString());

        //add new Employee
        User newUser = new User(userService.getNextUserId(), name, new HashedPassword(passwordField.getText()), role);
        userService.addUser(newUser);
        users.add(newUser);
        loadTableView();
        employeesTableView.getSelectionModel().select(newUser);
        resetFields();
    }

    private void resetFields() {
        nameField.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        passwordField.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        roleChoiceBox.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        errorLabel.setText("");
    }

    @FXML
    public void onUpdateEmployeeButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (employeesTableView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Selecteer een medewerker");
            return;
        }

        //set the new data
        User updatedUser = selectedUser;
        updatedUser.setPassword(new HashedPassword(passwordField.getText()));
        updatedUser.setName(nameField.getText());
        updatedUser.setRole(Role.valueOf(Role.values()[roleChoiceBox.getItems().indexOf(roleChoiceBox.getValue())].toString()));

        //remove selected user from tableview
        users.remove(selectedUser);

        //update the user
        userService.updateUser(updatedUser);
        users.add(updatedUser);
        loadTableView();
        employeesTableView.getSelectionModel().select(updatedUser);
    }

    @FXML
    public void onDeleteEmployeeButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (selectedUser == null) {
            errorLabel.setText("selecteer een medewerker");
            return;
        }
        userService.deleteUser(selectedUser);
        users.remove(selectedUser);
        loadTableView();
        employeesTableView.getSelectionModel().clearSelection();
    }

    private boolean checkEmptyFields() {
        if (nameField.getText().isEmpty() || passwordField.getText().isEmpty() || roleChoiceBox.getValue() == null) {
            checkField(nameField);
            checkField(passwordField);
            errorLabel.setText("Vul alle velden in");
            return true;
        }
        return false;
    }

    private void loadTableView() {
        Collections.sort(users);
        employeesTableView.setItems(users);
    }

    private void checkField(TextField field) {
        if (field.getText().isEmpty()) {
            field.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        } else {
            field.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    @FXML
    public void onHouseIconClick(MouseEvent mouseEvent) {
        loadNextStage("dashboard-view.fxml", new DashboardController(user), mouseEvent);
        mouseEvent.consume();
    }

    @FXML
    public void onMyTicketIconClick(MouseEvent mouseEvent) {
        loadNextStage("myTickets-view.fxml", new MyTicketController(user), mouseEvent);
        mouseEvent.consume();
    }

    @FXML
    public void onAllTicketIconClick(MouseEvent mouseEvent) {
        loadNextStage("ticket-list-view.fxml", new TicketListViewController(), mouseEvent);
        mouseEvent.consume();
    }

    @FXML
    public void onArchiveTicketIconClick(MouseEvent mouseEvent) {
        loadNextStage("archive-database-view.fxml", new ArchiveDatabaseController(user), mouseEvent);
        mouseEvent.consume();
    }

    @FXML
    public void onCRUDEmployeeIconClick(MouseEvent mouseEvent) {
        loadNextStage("crud-employee-view.fxml", new CrudEmployeeController(user), mouseEvent);
        mouseEvent.consume();
    }
}
