package com.projectticketsystem.ui;

import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MyTicketController extends BaseController implements Initializable {

    @FXML private ChoiceBox choiceBox;
    @FXML private TableView<Ticket> myTicketView;
    @FXML private TableColumn<Ticket, Integer> columnTicketID;
    @FXML private TableColumn columnSubject;
    @FXML private TableColumn columnPriority;
    @FXML private TableColumn columnStatus;

    private List<Ticket> myTickets;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        myTickets();
        fillChoiceBox();
        sortTicketview();
        myTicketsView();
    }
    private void myTickets()
    {
        TicketService ticketService = new TicketService();
        this.myTickets = ticketService.getMyTickets(user);
    }
    private void fillChoiceBox()
    {
        choiceBox.getItems().addAll("Ticket ID", "Prioriteit");
    }
    private void sortTicketview()
    {
        if(choiceBox.getValue() == "Ticket ID")
        {
            columnTicketID.isSortable();
            columnTicketID.setSortType(TableColumn.SortType.ASCENDING);

            //myTicketView.setItems(sortedList);
            //columnTicketID.sortTypeProperty()
            /*Collections.sort(myTickets.sort(myTickets.));*/
        }
        else if(choiceBox.getValue() == "Prioriteit")
        {
            columnPriority.setSortType(TableColumn.SortType.ASCENDING);
        }
        else if(choiceBox.getValue() == null)
        {
            columnTicketID.setSortType(TableColumn.SortType.ASCENDING);
        }
    }
    private void myTicketsView()
    {
        columnTicketID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        columnSubject.setCellFactory(new PropertyValueFactory<>("ticketSummary"));
        columnPriority.setCellFactory(new PropertyValueFactory<>("priority"));
        columnStatus.setCellFactory(new PropertyValueFactory<>("ticketStatus"));

        myTicketView.getItems().addAll(myTickets);
    }
    @FXML
    private void clickedTicket(Event event)
    {
        BaseController controller = new BaseController();
        Ticket ticket = myTicketView.getSelectionModel().getSelectedItem();
        if(ticket != null)
            loadNextStage("ticket-view.fxml", new TicketController(ticket, user, "myTickets-view.fxml", controller), event);
    }

}
