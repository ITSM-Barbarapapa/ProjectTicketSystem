package com.projectticketsystem.Service;

import com.projectticketsystem.dal.TicketDAO;
import com.projectticketsystem.Model.*;

import java.util.List;

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService(){
        ticketDAO = new TicketDAO();
    }

    public void AddTicket(Ticket ticket){
        ticketDAO.addTicket(ticket);
    }

    public int getHighestTicketID(){
        return ticketDAO.getHighestTicketID();
    }

    public List<Ticket> getAllTickets(){
        return ticketDAO.getAllTickets();
    }

    public List<Ticket> getTicketsByFilter()
    {
        //TODO: implement filter logic here
        return null;
    }
}
