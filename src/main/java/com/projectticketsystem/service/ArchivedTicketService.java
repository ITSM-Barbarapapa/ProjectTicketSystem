package com.projectticketsystem.service;

import com.projectticketsystem.dal.ArchivedTicketDAO;
import com.projectticketsystem.model.Ticket;
import org.bson.Document;

import java.util.List;

public class ArchivedTicketService {
    private final ArchivedTicketDAO archivedTicketDAO;

    public ArchivedTicketService() {
        archivedTicketDAO = new ArchivedTicketDAO();
    }

    public void archiveTickets() {
        List<Document> ticketsToArchive = new TicketService().getTicketsToArchive();
        for (Document ticket : ticketsToArchive) {
            archivedTicketDAO.addTicket(ticket);
        }
    }

    public List<Ticket> getAllArchivedTickets() {
        return archivedTicketDAO.getAllArchivedTickets();
    }
}

