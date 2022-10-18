package com.projectticketsystem.ui;

import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketController extends BaseController implements Initializable {
    public TicketController(Ticket ticket){
        ticketService = new TicketService();
        this.ticket = ticket;
        //this.user = user;
    }
    public ImageView homeButton;
    public Label summaryLabel;
    public Label nameAndContactLabel;
    public Label categoryLabel;
    public TextArea descriptionTextField;
    public Label ticketIDLabel;
    public Label impactLabel;
    public Label urgencyLabel;
    public Label priorityLabel;
    public Label dateLabel;
    public Label ticketStatusLabel;
    private final TicketService ticketService;
    private final Ticket ticket;
    //private final User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        summaryLabel.setText(ticket.getTicketSummary());
        nameAndContactLabel.setText(ticket.getName() + " | " + ticket.getContact());
        categoryLabel.setText(ticket.getTicketCategory());
        descriptionTextField.setText(ticket.getTicketDescription());
        ticketIDLabel.setText(String.valueOf(ticket.getTicketId()));
        impactLabel.setText(ticket.getImpact());
        urgencyLabel.setText(ticket.getUrgency());
        priorityLabel.setText(ticket.getPriority());
        dateLabel.setText(ticket.getDate().toString());
        ticketStatusLabel.setText(ticket.getTicketStatus().toString());
    }

   /* public void OnHomeButtonClick(MouseEvent event) throws IOException {
        // Go to next window
        // get current Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // load new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }*/

    public void OnReactButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("react-ticket-view.fxml"));
        loader.setController(new ReactTicketController(ticket, stage));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Reageren op ticket");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }


}
