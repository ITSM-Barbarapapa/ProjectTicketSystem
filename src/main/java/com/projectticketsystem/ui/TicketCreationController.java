package com.projectticketsystem.ui;

import com.projectticketsystem.model.Role;
import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.TicketStatus;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class TicketCreationController extends BaseController implements Initializable {

    private final TicketService ticketService;
    @FXML
    public Label impactLabel;
    @FXML
    public Label urgentieLabel;
    @FXML
    public Label berekendePrioriteitLabel;
    private User user;
    @FXML
    public TextArea descriptionTextField;
    @FXML
    public TextField summaryTextField;
    @FXML
    public ChoiceBox<String> categoryChoiceBox;
    @FXML
    public ChoiceBox<String> impactChoiceBox;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField contactTextField;
    @FXML
    public TextField calculatePriorityTextBox;
    @FXML
    public ChoiceBox<String> urgencyChoiceBox;
    @FXML
    public Button addTicketButton;
    @FXML
    public ImageView homeButton;
    @FXML
    public Label usernameLabel;

    @FXML
    public ImageView allTicketsIcon;
    @FXML
    public ImageView employeeIcon;
    @FXML
    public ImageView archiveIcon;

    public TicketCreationController(User user){
        ticketService = new TicketService();
        this.user = user;
    }

    private void CheckUser(){
        if(user.getRole().equals(Role.RegularEmployee)) {
            HidePriorityBoxes();
            HideSiteBalkIcons();
        }
    }

    private void HideSiteBalkIcons() {
        allTicketsIcon.setVisible(false);
        employeeIcon.setVisible(false);
        archiveIcon.setVisible(false);
    }

    private void HidePriorityBoxes() {
        impactChoiceBox.setVisible(false);
        urgencyChoiceBox.setVisible(false);
        calculatePriorityTextBox.setVisible(false);
        impactLabel.setVisible(false);
        urgentieLabel.setVisible(false);
        berekendePrioriteitLabel.setVisible(false);
    }

    @FXML
    public void AddTicket(ActionEvent actionEvent){
        if (!Requirements()){
            return;
        }

        Ticket ticket = new Ticket(nameTextField.getText(), contactTextField.getText(), LocalDate.now(), TicketStatus.Open, CreateID());
        FillInTicketWithInformation(ticket);
        ticketService.addTicket(ticket);
        loadNextStage("myTickets-view.fxml", new MyTicketController(user), actionEvent);
    }

    private int CreateID(){
        int ticketID = ticketService.getHighestTicketID();
        return ticketID + 1;
    }

    private void FillInTicketWithInformation(Ticket ticket) {
        ticket.setTicketImpact("");
        ticket.setTicketUrgency("");
        ticket.setTicketSummary("");
        ticket.setTicketDescription("");
        ticket.setTicketCategory("");
        ticket.setTicketReaction("");
        ticket.setPriority("");
        ticket.setUser(user);

        if(categoryChoiceBox.getValue() != null){
            ticket.setTicketCategory(categoryChoiceBox.getValue());
        }

        if (!Objects.equals(summaryTextField.getText(), "")){
            ticket.setTicketSummary(summaryTextField.getText());
        }

        if (!Objects.equals(descriptionTextField.getText(), "")){
            ticket.setTicketDescription(descriptionTextField.getText());
        }

        if (impactChoiceBox.getValue() != null){
            ticket.setTicketImpact(impactChoiceBox.getValue());
        }

        if (urgencyChoiceBox.getValue() != null){
            ticket.setTicketUrgency(urgencyChoiceBox.getValue());
        }

        if (!Objects.equals(calculatePriorityTextBox.getText(), "")){
            ticket.setPriority(calculatePriorityTextBox.getText());
        }

    }

    private boolean Requirements(){
        boolean requirementsCheck = true;

        out.println(nameTextField.getText());
        out.println(contactTextField.getText());


        if (Objects.equals(nameTextField.getText(), "") || Objects.equals(contactTextField.getText(), ""))
            requirementsCheck = false;

        return requirementsCheck;
    }

    @FXML
    public void itemChange(ActionEvent actionEvent) {
        String impactValue = impactChoiceBox.getValue();
        String urgencyValue = urgencyChoiceBox.getValue();
        if (impactValue == null || urgencyValue == null){
            return;
        }

        int impactNumber = Integer.parseInt(String.valueOf(impactValue.toCharArray()[0]));
        int urgencyNumber = Integer.parseInt(String.valueOf(urgencyValue.toCharArray()[0]));

        calculatePriorityTextBox.setText(String.valueOf(CalculatePriority(impactNumber, urgencyNumber)));
    }

    private int CalculatePriority(int impact, int urgency){

        return impact + urgency - 1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CheckUser();
        usernameLabel.setText(user.getName());
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        loadNextStage("dashboard-view.fxml", null, event);
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
        loadNextStage("ticket-list-view.fxml", new TicketListViewController(user), mouseEvent);
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
