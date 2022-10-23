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
    double perOpen = 0;
    double perResolved = 0;
    double perClosed = 0;

    private int statusOpen = 0;
    private int statusResolved = 0;
    private int statusClosedWithoutResolve = 0;
    @FXML
    PieChart statusChart;

    public Label countOpen;
    public Label countResolved;
    public Label countClosed;

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
        CalculateChart();

        ObservableList<PieChart.Data> statusChartData = FXCollections.observableArrayList(
                /*new PieChart.Data("Open", perOpen),
                new PieChart.Data("Opgelost", perResolved),
                new PieChart.Data("Gesloten zonder oplossing", perClosed)*/);

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
            perOpen = (statuses.size() / statusOpen) * 100;
        if(statusResolved > 0)
            perResolved = (statuses.size() / statusResolved) * 100;
        if(statusClosedWithoutResolve > 0)
            perClosed = (statuses.size() / statusClosedWithoutResolve) * 100;
    }
    private void countStatuses(List<TicketStatus> statuses)
    {
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
        loadNextStage("dashboard-view.fxml", null, mouseEvent);
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
