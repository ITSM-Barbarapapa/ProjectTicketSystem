package com.projectticketsystem.ui;

import com.projectticketsystem.model.Role;
import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.TicketStatus;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import com.projectticketsystem.service.UserService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TicketController extends BaseController implements Initializable {
    @FXML
    public ChoiceBox<String> impactChoicebox;
    @FXML
    public ChoiceBox<String> urgencyChoicebox;
    @FXML
    public ChoiceBox<String> statusChoicebox;
    @FXML
    public ChoiceBox<String> employeeChoicebox;
    @FXML
    public TextArea reactionTextarea;
    private TicketService ticketService;
    private BaseController previousController;
    private String previousViewFXML;

    public TicketController(Ticket ticket, User user, String fxmlFileName, BaseController controller) {
        userService = new UserService();
        ticketService = new TicketService();
        this.ticket = ticket;
        this.user = user;
        previousViewFXML = fxmlFileName;
        previousController = controller;
    }

    @FXML
    public ImageView homeButton;
    @FXML
    public Label summaryLabel;
    @FXML
    public Label nameAndContactLabel;
    @FXML
    public Label categoryLabel;
    @FXML
    public TextArea descriptionTextField;
    @FXML
    public Label ticketIDLabel;
    @FXML
    public Label priorityLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Button saveTicketButton;
    @FXML
    public Label usernameLabel;
    @FXML
    public ImageView allTicketsIcon;
    @FXML
    public ImageView employeeIcon;
    @FXML
    public ImageView archiveIcon;
    private final Ticket ticket;
    private final UserService userService;
    private final User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        summaryLabel.setText(ticket.getTicketSummary());
        nameAndContactLabel.setText(ticket.getName() + " | " + ticket.getContact());
        categoryLabel.setText(ticket.getTicketCategory());
        descriptionTextField.setText(ticket.getTicketDescription());
        ticketIDLabel.setText(String.valueOf(ticket.getTicketID()));
        priorityLabel.setText(ticket.getPriority());
        dateLabel.setText(ticket.getDate().toString());
        List<String> userNames = getUserList(userService.getUsersByRole(Role.ServiceDeskEmployee));
        employeeChoicebox.setItems(FXCollections.observableList(userNames));
        impactChoicebox.setValue(ticket.getImpact());
        urgencyChoicebox.setValue(ticket.getUrgency());
        employeeChoicebox.setValue(ticket.getUsername());
        statusChoicebox.setValue(ticket.getTicketStatus());
        reactionTextarea.setText(ticket.getTicketReaction());
        usernameLabel.setText(user.getName());
        CheckUser();
    }

    @FXML
    public void onBackButtonClick(ActionEvent event){
        loadNextStage(previousViewFXML, previousController, event);
    }

    private void CheckUser() {
        if (user.getRole().equals(Role.RegularEmployee)) {
            HideServiceDeskTools();
            HideSiteBalkIcons();
        }
    }

    private void HideSiteBalkIcons() {
        allTicketsIcon.setVisible(false);
        employeeIcon.setVisible(false);
        archiveIcon.setVisible(false);
    }

    private void HideServiceDeskTools() {
        impactChoicebox.setDisable(true);
        urgencyChoicebox.setDisable(true);
        employeeChoicebox.setDisable(true);
        statusChoicebox.setDisable(true);
        saveTicketButton.setDisable(true);
    }

    private List<String> getUserList(List<User> employees) {
        List<String> userNames = new ArrayList<>();
        for (User u : employees) {
            userNames.add(u.getName());
        }
        return userNames;
    }

    @FXML
    public void OnSaveButtonClickUpdate(ActionEvent event) {
        if (CheckIfEmpty(impactChoicebox.getValue())) {
            ticket.setTicketImpact(impactChoicebox.getValue());
        }
        if (CheckIfEmpty(urgencyChoicebox.getValue())) {
            ticket.setTicketUrgency(urgencyChoicebox.getValue());
        }
        if (CheckIfEmpty(statusChoicebox.getValue())) {
            ticket.setTicketStatus(TicketStatus.valueOf(statusChoicebox.getValue()));
        }
        if (CheckIfEmpty(employeeChoicebox.getValue())) {
            ticket.setUser(userService.getUserByName(employeeChoicebox.getValue()));
        }
        if (CheckIfEmpty(reactionTextarea.getText())) {
            ticket.setTicketReaction(reactionTextarea.getText());
        }
        if (CheckIfEmpty(priorityLabel.getText())) {
            ticket.setPriority(priorityLabel.getText());
        }
        ticketService.updateTicket(ticket);

    }

    private boolean CheckIfEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    @FXML
    public void OnReactButtonClick(ActionEvent event) {
        loadNextInNewStage("react-ticket-view.fxml", new ReactTicketController(ticket, user), event);
        initialize(null, null);
    }

    @FXML
    public void itemChange(ActionEvent actionEvent) {
        String impactValue = impactChoicebox.getValue();
        String urgencyValue = urgencyChoicebox.getValue();
        if (impactValue == null || urgencyValue == null || impactValue.isEmpty() || urgencyValue.isEmpty()) {
            return;
        }


        int impactNumber = Integer.parseInt(String.valueOf(impactValue.toCharArray()[0]));
        int urgencyNumber = Integer.parseInt(String.valueOf(urgencyValue.toCharArray()[0]));

        priorityLabel.setText(String.valueOf(CalculatePriority(impactNumber, urgencyNumber)));
    }

    private int CalculatePriority(int impact, int urgency) {

        return impact + urgency - 1;
    }

    @FXML
    public void onHouseIconClick(MouseEvent mouseEvent) {
        loadNextStage("dashboard-view.fxml", new DashboardController(user), mouseEvent);
        mouseEvent.consume();
    }

    @FXML
    public void onMyTicketIconClick(MouseEvent mouseEvent) {
        //TODO Add right controller and view for my tickets
        loadNextStage("my-tickets-view.fxml", new MyTicketController(user), mouseEvent);
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
