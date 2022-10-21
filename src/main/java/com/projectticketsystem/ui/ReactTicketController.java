package com.projectticketsystem.ui;

import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


import java.io.IOException;

public class ReactTicketController extends BaseController {

    private final Ticket ticket;
    private final User user;

    public TextArea reactionTextfield;

    public ReactTicketController(Ticket ticket, User user) {
        this.ticket = ticket;
        this.user = user;
    }

    @FXML
    public void OnAddReactionButtonClick(ActionEvent event) throws IOException {
        ticket.setTicketReaction(ticket.getTicketReaction() + "\n" + reactionTextfield.getText());
        loadNextStage("ticket-view.fxml", new TicketController(ticket, user), event);
    }

}
