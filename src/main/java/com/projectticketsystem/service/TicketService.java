package com.projectticketsystem.service;

import com.projectticketsystem.dal.TicketDAO;
import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.TicketStatus;
import com.projectticketsystem.model.User;
import javafx.collections.ObservableList;
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

    public ObservableList<Ticket> getMyTickets(User user){return ticketDAO.getMyTickets(user);}


    public List<Ticket> getTicketsByFilter(String statusFilter, String employeeFilter)
    {
        if ((statusFilter == "All" || statusFilter == null) && (employeeFilter == "All" || employeeFilter == null))
            return ticketDAO.getAllTickets();
        if (statusFilter == "All" || statusFilter == null)
            return ticketDAO.getTicketsByEmployee(userService.getUserByName(employeeFilter).getID());
        if (employeeFilter == "All" || employeeFilter == null)
            return ticketDAO.getTicketsByStatus(statusFilter);
        return ticketDAO.getTicketsByStatusAndEmployee(statusFilter, userService.getUserByName(employeeFilter).getID());
    }

}
