package com.projectticketsystem.ui;

import com.projectticketsystem.dal.TicketDAO;
import com.projectticketsystem.model.Role;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
    private void onLoginButtonClicked(ActionEvent event) {
        System.out.println("Username: " + userIdField.getText());
        System.out.println("Password: " + passwordField.getText());

        TicketDAO ticketDAO = new TicketDAO();

        if (checkPassword()) {
            //Check if user is admin or regular employee or Service Desk employee
            if (user.getRole() == Role.Administrator || user.getRole() == Role.ServiceDeskEmployee) {
                loadNextStage("ticket-view.fxml", new TicketController(ticketDAO.getTicketByID(5), user, "dashboard-view.fxml", null), event);
            } else if (user.getRole() == Role.RegularEmployee) {
                loadNextStage("crud-employee-view.fxml", new CrudEmployeeController(user), event);
            }
        } else {
            errorLabel.setText("Gebruikerscode of wachtwoord is onjuist");
        }
        event.consume();
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
            //check if hashes are equal
            return user.getPassword().checkPassword(passwordField.getText());
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
