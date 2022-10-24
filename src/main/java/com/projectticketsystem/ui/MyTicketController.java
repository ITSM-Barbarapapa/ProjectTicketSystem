package com.projectticketsystem.ui;

import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class MyTicketController extends BaseController implements Initializable {

    @FXML private ChoiceBox choiceBox;
    @FXML private TableView<Ticket> myTicketView;
    @FXML private TableColumn<Ticket, Integer> columnTicketID;
    @FXML private TableColumn<Ticket, String> columnSubject;
    @FXML private TableColumn<Ticket, String> columnPriority;
    @FXML private TableColumn<Ticket, String> columnStatus;

    private ObservableList<Ticket> myTickets;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //myTickets();
        fillChoiceBox();
        myTicketsView();
    }

    public MyTicketController(User user)
    {
        TicketService ticketService = new TicketService();
        this.myTickets = ticketService.getMyTickets(user);
        this.user = user;
    }
    private void fillChoiceBox()
    {
        choiceBox.getItems().addAll("Ticket ID", "Prioriteit");
    }
    @FXML
    public void choiceboxClicked()
    {
        if(choiceBox.getValue() == "Ticket ID")
        {
            Comparator<Ticket> comparator = Comparator.comparing(Ticket::getTicketID);
            myTickets.sort(comparator);
        }
        else if(choiceBox.getValue() == "Prioriteit")
        {
            Comparator<Ticket> comparator = Comparator.comparing(Ticket::getPriority);
            myTickets.sort(comparator);
        }
        else if(choiceBox.getValue() == null)
        {
            Comparator<Ticket> comparator = Comparator.comparing(Ticket::getTicketID);
            myTickets.sort(comparator);
        }

        myTicketsView();
    }
    private void myTicketsView()
    {
        myTicketView.getItems().clear();

        columnTicketID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        columnSubject.setCellValueFactory(new PropertyValueFactory<>("ticketSummary"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));

        myTicketView.getItems().addAll(myTickets);
    }
    @FXML
    private void clickedTicket(Event event)
    {
        Ticket ticket = myTicketView.getSelectionModel().getSelectedItem();
        if(ticket != null)
            loadNextStage("ticket-view.fxml", new TicketController(ticket, user, "myTickets-view.fxml", new MyTicketController(user)), event);
    }
    @FXML
    public void onHouseIconClick(MouseEvent mouseEvent) {
        loadNextStage("dashboard-view.fxml", new DashboardController(user), mouseEvent);
        mouseEvent.consume();
    }
    @FXML
    public void onMyTicketIconClick(MouseEvent mouseEvent) {
        loadNextStage("myTickets-view.fxml", null, mouseEvent);
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
