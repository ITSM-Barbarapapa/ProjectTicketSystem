package com.projectticketsystem.Service;

import com.projectticketsystem.DAL.TicketDAO;
import com.projectticketsystem.Model.*;

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService(){
        ticketDAO = new TicketDAO();
    }

    public void AddTicket(Ticket ticket){
        ticketDAO.addTicket(ticket);
    }

    public int GetHighestTicketID(){
        return ticketDAO.GetHighestTicketID();
    }

}
