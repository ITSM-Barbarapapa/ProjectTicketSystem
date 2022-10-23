package com.projectticketsystem.ui;

import com.projectticketsystem.model.HashedPassword;
import com.projectticketsystem.model.Role;
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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ArchiveDatabaseController extends BaseController implements Initializable {
    @FXML
    public Label usernameLabel;

    private final User user;
    private final ArchivedTicketService archivedTicketService;
    private final ObservableList<Ticket> archivedTickets;
    @FXML
    public TableView<Ticket> archivedTicketsTableView;


    public ArchiveDatabaseController(/*User user*/) {
        this.user = new User(1, "test", new HashedPassword(new byte[5], new byte[5]), Role.Administrator); //user;
        archivedTicketService = new ArchivedTicketService();
        archivedTickets = FXCollections.observableList(archivedTicketService.getAllArchivedTickets());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO Populate the tableview with the archived tickets
        usernameLabel.setText(user.getName());
        archivedTicketsTableView.setItems(archivedTickets);
    }

    @FXML
    public void onSearchFieldTextChange(ActionEvent actionEvent) {
        //TODO Change data in tableview based on search field
    }

    @FXML
    public void onArchiveButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        archivedTicketService.archiveTickets();
    }

    @FXML
    public void onHouseIconClick(MouseEvent mouseEvent) {
        loadNextStage("dashboard-view.fxml", null, mouseEvent);
        mouseEvent.consume();
    }
}
