package com.projectticketsystem.UI;

import com.projectticketsystem.Service.TicketService;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class TicketListViewController extends BaseController implements Initializable
{
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("ticket-list-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Green Desk");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        TicketService ticketService = new TicketService();
        ticketService.getAllTickets();
    }
}
