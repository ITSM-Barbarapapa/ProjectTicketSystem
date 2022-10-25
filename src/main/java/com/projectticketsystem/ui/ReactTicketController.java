package com.projectticketsystem.ui;

import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.User;
import com.projectticketsystem.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReactTicketController extends BaseController {

    private final Ticket ticket;
    private final User user;

    public TextArea reactionTextfield;
    private TicketService ticketService;

    public ReactTicketController(Ticket ticket, User user) {
        ticketService = new TicketService();
        this.ticket = ticket;
        this.user = user;
    }

    @FXML
    public void OnAddReactionButtonClick(ActionEvent event) throws IOException {
        ticket.setTicketReaction(user.getName() + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" + reactionTextfield.getText() + "\n\n" + ticket.getTicketReaction());
        ticketService.updateTicket(ticket);
        Stage stage = (Stage) reactionTextfield.getScene().getWindow();
        stage.close();
    }

}
