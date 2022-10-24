package com.projectticketsystem.service;

import com.projectticketsystem.dal.TicketDAO;
import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.TicketStatus;
import com.projectticketsystem.model.User;
import org.bson.Document;

import java.util.List;

public class TicketService {
    private final TicketDAO ticketDAO;
    UserService userService = new UserService();

    public TicketService(){
        ticketDAO = new TicketDAO();
    }

    public void addTicket(Ticket ticket){
        ticketDAO.addTicket(ticket);
    }

    public int getHighestTicketID(){
        return ticketDAO.getHighestTicketID();
    }

    public List<TicketStatus> getAllTicketStatus(){return ticketDAO.getAllTicketStatus();}

    public List<Ticket> getAllTickets() {
        return ticketDAO.getAllTickets();
    }

    public void updateTicket(Ticket ticket) {
        ticketDAO.updateTicket(ticket);
    }

    public List<Document> getTicketsToArchive() {
        return ticketDAO.getTicketsToArchive();
    }

    public List<Ticket> getMyTickets(User user){return ticketDAO.getMyTickets(user);}


    public List<Ticket> getTicketsByFilter(String statusFilter, String employeeFilter)
    {
        if (statusFilter.equals("All") && employeeFilter.equals("All"))
            return ticketDAO.getAllTickets();
        if (statusFilter.equals("All"))
            return ticketDAO.getTicketsByEmployee(userService.getUserByName(employeeFilter).getID());
        if (employeeFilter.equals("All"))
            return ticketDAO.getTicketsByStatus(statusFilter);
        return ticketDAO.getTicketsByStatusAndEmployee(statusFilter, userService.getUserByName(employeeFilter).getID());
    }
}
