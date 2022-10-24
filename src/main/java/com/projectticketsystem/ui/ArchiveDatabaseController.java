package com.projectticketsystem.ui;

import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.ArchivedTicketService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArchiveDatabaseController extends BaseController implements Initializable {
    private final User user;
    private final ArchivedTicketService archivedTicketService;
    private ObservableList<Ticket> archivedTickets;
    @FXML
    public Label usernameLabel;
    @FXML
    public TextField searchField;
    @FXML
    public TableView<Ticket> archivedTicketsTableView;


    public ArchiveDatabaseController(User user) {
        this.user = user;
        archivedTicketService = new ArchivedTicketService();
        archivedTickets = FXCollections.observableList(archivedTicketService.getAllArchivedTickets());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(user.getName());
        archivedTicketsTableView.setItems(archivedTickets);

        //add text changed event handler
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Ticket> filteredTickets = new ArrayList<>();
            archivedTickets.forEach(t -> {
                if (t.getTicketSummary().toLowerCase().contains(searchField.getText().toLowerCase())) {
                    filteredTickets.add(t);
                }
            });
            archivedTicketsTableView.setItems(FXCollections.observableList(filteredTickets));
        });
    }

    @FXML
    public void onArchiveButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        archivedTicketService.archiveTickets();

        archivedTickets = FXCollections.observableList(archivedTicketService.getAllArchivedTickets());
        archivedTicketsTableView.setItems(archivedTickets);
    }

    @FXML
    public void onHouseIconClick(MouseEvent mouseEvent) {
        loadNextStage("dashboard-view.fxml", new DashboardController(user), mouseEvent);
        mouseEvent.consume();
    }

    @FXML
    public void onMyTicketIconClick(MouseEvent mouseEvent) {
        loadNextStage("myTickets-view.fxml", new MyTicketController(user), mouseEvent);
        mouseEvent.consume();
    }

    @FXML
    public void onAllTicketIconClick(MouseEvent mouseEvent) {
        loadNextStage("ticket-list-view.fxml", null, mouseEvent);
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
