package com.projectticketsystem.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MyTicketController implements Initializable {

    @FXML
    ChoiceBox choiceBox;

    @FXML
    ScrollPane ticketPane;


    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        fillChoiceBox();
        viewTickets();
    }

    private void fillChoiceBox()
    {
        choiceBox.getItems().add("Nieuwste");
        choiceBox.getItems().add("Prioriteit");
    }
    private void viewTickets()
    {
        Pane ticket = new Pane();
        ticket.setStyle("-fx-background-color: black;");
    }

}
