package com.projectticketsystem.UI;

import com.projectticketsystem.Model.Ticket;
import com.projectticketsystem.Model.TicketStatus;
import com.projectticketsystem.Model.User;
import com.projectticketsystem.Service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;;import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TicketCreationController {
    private final TicketService ticketService;
    private User user;
    public TextArea DescriptionTextField;
    public TextField summaryTextField;
    public ChoiceBox<String> categoryChoiceBox;
    public ChoiceBox<String> impactChoiceBox;
    public TextField nameTextField;
    public TextField contactTextField;
    public TextField calculatePriorityTextBox;
    public ChoiceBox<String> urgencyChoiceBox;
    public Button addTicketButton;
    public ImageView homeButton;

    public TicketCreationController(){
        ticketService = new TicketService();
    }

    @FXML
    private void OnHomeButtonClick(MouseEvent event) throws IOException {
        // Go to next window
        // get current Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // load new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public void AddTicket(ActionEvent actionEvent){
        if (!Requirements()){
            System.out.println("not all info is there");
            return;
        }

        Ticket ticket = new Ticket(nameTextField.getText(), contactTextField.getText(), urgencyChoiceBox.getValue(), impactChoiceBox.getValue(), calculatePriorityTextBox.getText(), LocalDate.now(), TicketStatus.Open, CreateID());
        FillInTicketWithInformation(ticket);
        ticketService.AddTicket(ticket);

    }

    private int CreateID(){
        int ticketID = ticketService.getHighestTicketID();
        return ticketID + 1;
    }

    private void FillInTicketWithInformation(Ticket ticket) {
        if(categoryChoiceBox.getValue() != null){
            ticket.setTicketCategory(categoryChoiceBox.getValue());
        }

        if (!Objects.equals(summaryTextField.getText(), "")){
            ticket.setTicketSummary(summaryTextField.getText());
        }

        if (!Objects.equals(DescriptionTextField.getText(), "")){
            ticket.setTicketDescription(DescriptionTextField.getText());
        }
    }

    private boolean Requirements(){
        boolean requirementsCheck = true;

        System.out.println(nameTextField.getText());
        System.out.println(contactTextField.getText());
        System.out.println(urgencyChoiceBox.getValue());
        System.out.println(impactChoiceBox.getValue());

        if (Objects.equals(nameTextField.getText(), "") || Objects.equals(nameTextField.getText(), "") || urgencyChoiceBox.getValue() == null || impactChoiceBox.getValue() == null)
            requirementsCheck = false;

        return requirementsCheck;
    }

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

}
