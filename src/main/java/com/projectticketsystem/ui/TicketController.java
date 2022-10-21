package com.projectticketsystem.ui;

import com.projectticketsystem.model.Role;
import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import com.projectticketsystem.service.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TicketController extends BaseController implements Initializable {
    @FXML
    public ChoiceBox impactChoicebox;
    @FXML
    public ChoiceBox urgencyChoicebox;
    @FXML
    public ChoiceBox statusChoicebox;
    @FXML
    public ChoiceBox employeeChoicebox;
    @FXML
    public TextArea ReactionTextfield;

    public TicketController(Ticket ticket, User user){
        ticketService = new TicketService();
        userService = new UserService();
        this.ticket = ticket;
        this.user = user;
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
    private final TicketService ticketService;
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

        loadEmployeeChoiceBox();
    }

    private void loadEmployeeChoiceBox() {
        List<User> users = userService.getUsersByRole(Role.ServiceDeskEmployee);
        employeeChoicebox.setItems(FXCollections.observableList(users));
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
