package com.projectticketsystem.ui;

import com.projectticketsystem.model.*;
import com.projectticketsystem.service.TicketService;
import com.projectticketsystem.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class TicketListViewController extends BaseController implements Initializable
{
    @FXML private TableView<Ticket> ticketTable;
    @FXML private TableColumn<Ticket, Integer> ticketIDColumn;
    @FXML private TableColumn<Ticket, String> subjectColumn;
    @FXML private TableColumn<Ticket, String> priorityColumn;
    @FXML private TableColumn<Ticket, String> assigneeColumn;
    @FXML private TableColumn<Ticket, String> statusColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeTableView();
        initializeComboBoxes();
        ticketTable.setEditable(true);
    }

    private void initializeComboBoxes()
    {

    }

    private void initializeTableView() {
        UserService userService = new UserService();

        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("ticketSummary"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        assigneeColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        assigneeColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(userService.getEmployeeNames()));
        assigneeColumn.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.assignNewEmployeeToTicket(event.getNewValue().toString());
            TicketService ticketService = new TicketService();
            ticketService.updateTicket(ticket);
        });

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));
        ticketTable.getItems().addAll(getAllTickets());
    }

    private static List<Ticket> getAllTickets()
    {
        TicketService ticketService = new TicketService();
        return ticketService.getAllTickets();
    }

    @FXML
    private void handleTicketTableClicked(Event event)
    {
        Ticket ticket = ticketTable.getSelectionModel().getSelectedItem();
        if (ticket != null)
            openTicket(ticket, event);
    }

    // Open the ticket
    private void openTicket(Ticket ticket, Event event)
    {

    }


    //TODO: Make a search function for the ticket table
    //TODO: Make a filter function for the ticket table

    //TODO: Research how to add a combobox to the table view
    //TODO: Implement the combobox to change the status of the ticket
}
