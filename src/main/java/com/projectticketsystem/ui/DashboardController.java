package com.projectticketsystem.ui;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable
{
    double perOpen;
    double perOpgelost;
    double perGesloten;
    @FXML
    PieChart statusChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        FillStatusChart();
    }
    public void FillStatusChart()
    {
        ObservableList<PieChart.Data> statusChartData = FXCollections.observableArrayList(
                new PieChart.Data("Open", 40),
                new PieChart.Data("Opgelost", 25),
                new PieChart.Data("Gesloten zonder oplossing", 35));
        statusChart.setData(statusChartData);

        statusChartData.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," ", data.pieValueProperty(), "%")));
    }
    public void CalculateChart()
    {

    }
}
