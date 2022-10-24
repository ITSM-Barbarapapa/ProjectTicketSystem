package com.projectticketsystem.ui;

import com.projectticketsystem.model.TicketStatus;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController extends BaseController implements Initializable
{
    double perOpen;
    double perResolved;
    double perClosed;

    private int statusOpen;
    private int statusResolved;
    private int statusClosedWithoutResolve;
    @FXML
    PieChart statusChart;

    public Label countOpen;
    public Label countResolved;
    public Label countClosed;

    public Label labelUsername;
    private final User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        FillStatusChart();
    }
    public DashboardController(User user)
    {
        this.user = user;

    }
    private void FillStatusChart()
    {
        labelUsername.setText(user.getName());

        perOpen = 0;
        perResolved = 0;
        perClosed = 0;

        CalculateChart();
        ObservableList<PieChart.Data> statusChartData = FXCollections.observableArrayList();

        if(perOpen > 0)
        {
            PieChart.Data data = new PieChart.Data("Open", perOpen);
            statusChartData.add(data);
        }
        if (perResolved > 0)
        {
            PieChart.Data data = new PieChart.Data("Opgelost", perResolved);
            statusChartData.add(data);
        }
        if (perResolved > 0)
        {
            PieChart.Data data = new PieChart.Data("Gesloten zonder oplossing", perClosed);
            statusChartData.add(data);
        }
        statusChart.setData(statusChartData);
        statusChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," ", data.pieValueProperty(), "%")));
        fillTable();
    }
    private void fillTable()
    {
        countOpen.setText(String.valueOf(statusOpen));
        countResolved.setText(String.valueOf(statusResolved));
        countClosed.setText(String.valueOf(statusClosedWithoutResolve));
    }
    private void CalculateChart()
    {
        TicketService ticketService = new TicketService();
        List<TicketStatus> statuses = ticketService.getAllTicketStatus();

        countStatuses(statuses);

        if(statusOpen > 0)
            perOpen = (100.00 / statuses.size()) * statusOpen;
        if(statusResolved > 0)
            perResolved = (100.00 / statuses.size()) * statusResolved;
        if(statusClosedWithoutResolve > 0)
            perClosed = (100.00 / statuses.size()) * statusClosedWithoutResolve;

        statuses.clear();
    }
    private void countStatuses(List<TicketStatus> statuses)
    {
        statusOpen =0;
        statusResolved = 0;
        statusClosedWithoutResolve = 0;

        for (TicketStatus status : statuses)
        {
            switch(status)
            {
                case Open:
                    statusOpen++;
                    break;
                case Resolved:
                    statusResolved++;
                    break;
                case ClosedWithoutResolve:
                    statusClosedWithoutResolve++;
                    break;
            }
        }
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
