package com.projectticketsystem.ui;

import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.TicketStatus;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import com.projectticketsystem.service.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class TicketListViewController extends BaseController implements Initializable {
    private final User user;
    UserService userService = new UserService();
    TicketService ticketService = new TicketService();
    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, Integer> ticketIDColumn;
    @FXML
    private TableColumn<Ticket, String> subjectColumn;
    @FXML
    private TableColumn<Ticket, String> priorityColumn;
    @FXML
    private TableColumn<Ticket, String> assigneeColumn;
    @FXML
    private TableColumn<Ticket, String> statusColumn;
    @FXML
    private ChoiceBox<String> statusFilterChoicebox;
    @FXML
    private ChoiceBox<String> employeeFilterChoicebox;
    @FXML
    private Label usernameLabel;

    public TicketListViewController(User user) {
        this.user = user;
    }

    private static List<Ticket> getAllTickets() {
        TicketService ticketService = new TicketService();
        return ticketService.getAllTickets();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeTableView();
        initializeChoiceBoxes();
        ticketTable.setEditable(true);
        usernameLabel.setText(user.getName());
    }

    private void initializeChoiceBoxes() {
        List<String> statusList = TicketStatus.getObservableList();
        statusList.add(0, "All");
        statusFilterChoicebox.getItems().addAll(FXCollections.observableArrayList(statusList));
        statusFilterChoicebox.setValue("Open");

        List<String> employeeList = userService.getEmployeeNames();
        employeeList.add(0, "All");
        employeeFilterChoicebox.getItems().addAll(FXCollections.observableArrayList(employeeList));
        employeeFilterChoicebox.setValue("All");
    }

    private void initializeTableView() {
        ticketIDColumn.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("ticketSummary"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        assigneeColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeUsername"));
        assigneeColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(userService.getEmployeeNames()));
        assigneeColumn.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.assignNewEmployeeToTicket(event.getNewValue());
            ticketService.updateTicket(ticket);
        });

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));
        statusColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(TicketStatus.getObservableList()));
        statusColumn.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.setTicketStatus(TicketStatus.valueOf(event.getNewValue()));
            ticketService.updateTicket(ticket);
        });

        ticketTable.getItems().addAll(getAllTickets());
    }

    @FXML
    private void openTicket(MouseEvent event) {
        Ticket ticket = ticketTable.getSelectionModel().getSelectedItem();
        if (ticket != null)
            loadNextStage("ticket-view.fxml", new TicketController(ticket, user, "ticket-list-view.fxml", new TicketListViewController(user)), event);
    }

    @FXML
    private void onItemChange(ActionEvent event) {
        String statusFilter = statusFilterChoicebox.getValue();
        String employeeFilter = employeeFilterChoicebox.getValue();
        ticketTable.getItems().clear();
        ticketTable.getItems().addAll(ticketService.getTicketsByFilter(statusFilter, employeeFilter));
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
