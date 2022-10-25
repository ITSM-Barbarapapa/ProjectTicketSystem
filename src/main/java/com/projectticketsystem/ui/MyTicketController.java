package com.projectticketsystem.ui;

import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import static com.projectticketsystem.model.Role.RegularEmployee;

public class MyTicketController extends BaseController implements Initializable {

    @FXML
    public ChoiceBox choiceBox;
    @FXML
    public TableView<Ticket> myTicketView;
    @FXML
    public TableColumn<Ticket, Integer> columnTicketID;
    @FXML
    public TableColumn<Ticket, String> columnSubject;
    @FXML
    public TableColumn<Ticket, String> columnPriority;
    @FXML
    public TableColumn<Ticket, String> columnStatus;

    @FXML
    public Label labelUsername;
    public ImageView AllTicketIcon;
    public ImageView ArchiveTicketIcon;
    public ImageView CRUDEmployeeIcon;
    private ObservableList<Ticket> myTickets;
    private User user;

    public MyTicketController(User user) {
        TicketService ticketService = new TicketService();
        this.myTickets = ticketService.getMyTickets(user);
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.myTickets = new TicketService().getMyTickets(user);

        fillChoiceBox();
        myTicketsView();
        labelUsername.setText(user.getName());

        if (user.getRole() == RegularEmployee) {
            AllTicketIcon.setVisible(false);
            ArchiveTicketIcon.setVisible(false);
            CRUDEmployeeIcon.setVisible(false);
        }
    }

    private void fillChoiceBox() {
        choiceBox.getItems().addAll("Ticket ID", "Prioriteit");
    }

    @FXML
    public void choiceboxClicked() {
        if (choiceBox.getValue() == "Ticket ID") {
            Comparator<Ticket> comparator = Comparator.comparing(Ticket::getTicketID);
            myTickets.sort(comparator);
        } else if (choiceBox.getValue() == "Prioriteit") {
            Comparator<Ticket> comparator = Comparator.comparing(Ticket::getPriority);
            myTickets.sort(comparator);
        } else if (choiceBox.getValue() == null) {
            Comparator<Ticket> comparator = Comparator.comparing(Ticket::getTicketID);
            myTickets.sort(comparator);
        }
        myTicketsView();
    }

    private void myTicketsView() {
        myTicketView.getItems().clear();

        columnTicketID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        columnSubject.setCellValueFactory(new PropertyValueFactory<>("ticketSummary"));
        columnPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));

        myTicketView.getItems().addAll(myTickets);
    }

    @FXML
    public void nieuwTicketButtonClick(Event event) {
        loadNextStage("ticketcreation-view.fxml", new TicketCreationController(user), event);
    }

    @FXML
    private void clickedTicket(Event event) {
        Ticket ticket = myTicketView.getSelectionModel().getSelectedItem();
        if (ticket != null)
            loadNextStage("ticket-view.fxml", new TicketController(ticket, user, "myTickets-view.fxml", new MyTicketController(user)), event);
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
        loadNextStage("ticket-list-view.fxml", new TicketListViewController(user), mouseEvent);
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
