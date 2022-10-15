package com.projectticketsystem.UI;

import com.projectticketsystem.DAL.TicketDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("ticket-view.fxml"));
        TicketDAO ticketDAO = new TicketDAO();



        fxmlLoader.setController(new TicketController(ticketDAO.getTicketByID(3)));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Green Desk");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}