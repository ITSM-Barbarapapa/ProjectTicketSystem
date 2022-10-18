package com.projectticketsystem.UI;

import com.projectticketsystem.model.Ticket;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


import java.io.IOException;

public class ReactTicketController extends BaseController {

    private final Ticket ticket;
    private final Stage stage;

    public TextArea reactionTextfield;

    public ReactTicketController(Ticket ticket, Stage stage) {
        this.ticket = ticket;
        this.stage = stage;
    }

    public void OnAddReactionButtonClick(ActionEvent event) throws IOException {
        ticket.setTicketReaction(ticket.getTicketReaction() + "\n" + reactionTextfield.getText());
        stage.close();
    }

}
