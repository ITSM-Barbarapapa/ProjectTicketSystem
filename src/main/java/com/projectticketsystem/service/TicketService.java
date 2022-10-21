package com.projectticketsystem.service;

import com.projectticketsystem.dal.TicketDAO;
import com.projectticketsystem.model.*;

import java.util.List;

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService(){
        ticketDAO = new TicketDAO();
    }

    public void addTicket(Ticket ticket){
        ticketDAO.addTicket(ticket);
    }

    public int getHighestTicketID(){
        return ticketDAO.getHighestTicketID();
    }

    public List<Ticket> getAllTickets(){
        return ticketDAO.getAllTickets();
    }

    public void getTicketsByFilter()
    {
        //TODO: implement filter logic here
    }
}
