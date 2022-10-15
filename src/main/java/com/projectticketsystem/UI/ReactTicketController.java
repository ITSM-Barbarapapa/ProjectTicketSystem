package com.projectticketsystem.UI;

import com.projectticketsystem.Model.Ticket;
import javafx.event.ActionEvent;


import java.io.IOException;

public class ReactTicketController extends BaseController {

    private final Ticket ticket;

    public ReactTicketController(Ticket ticket) {
        this.ticket = ticket;
    }

    public void OnAddReactionButtonClick(ActionEvent event) throws IOException {
        loadNextStage("ticket-view.fxml", new TicketController(ticket), event);
    }

}
