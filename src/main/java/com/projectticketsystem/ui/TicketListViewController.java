package com.projectticketsystem.ui;

import com.projectticketsystem.model.*;
import com.projectticketsystem.service.TicketService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class TicketListViewController extends BaseController implements Initializable
{
    @FXML private TableView<Ticket> ticketTable;
    @FXML private TableColumn<Ticket, Integer> ticketIDColumn;
    @FXML private TableColumn<Ticket, String> subjectColumn;
    @FXML private TableColumn<Ticket, String> priorityColumn;
    @FXML private TableColumn<Ticket, String> assigneeColumn;
    @FXML private TableColumn<TicketStatus, String> statusColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeTableView();
    }

    private void initializeTableView() {
        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("ticketSummary"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        //assigneeColumn.setCellValueFactory(new PropertyValueFactory<>("assignee"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));

        ticketTable.getItems().addAll(getAllTickets());
    }

    private static List<Ticket> getAllTickets()
    {
        TicketService ticketService = new TicketService();
        return ticketService.getAllTickets();
    }



    //TODO: Make a search function for the ticket table
    //TODO: Make a filter function for the ticket table

    //TODO: Research how to add a combobox to the table view
    //TODO: Implement the combobox to change the status of the ticket
}
