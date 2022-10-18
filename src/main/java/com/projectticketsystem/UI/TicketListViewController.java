package com.projectticketsystem.UI;

import com.projectticketsystem.Model.Ticket;
import com.projectticketsystem.Service.TicketService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class TicketListViewController extends BaseController implements Initializable
{
    @FXML
    private TableView ticketTable = new TableView<Ticket>();
    private TableColumn ticketIDColumn = new TableColumn("Ticket ID");
    private TableColumn ticketNameColumn = new TableColumn("Name");
    private TableColumn ticketContactColumn = new TableColumn("Contact");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        TicketService ticketService = new TicketService();
        List<Ticket> tickets = ticketService.getAllTickets();

        for (Ticket ticket : tickets)
        {
            ticketTable.getItems().add(ticket);
        }

        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        ticketNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ticketContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        ticketTable.getColumns().addAll(ticketIDColumn, ticketNameColumn, ticketContactColumn);
    }
}
