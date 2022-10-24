package com.projectticketsystem.dal;

import com.mongodb.client.MongoCollection;
import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.TicketStatus;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArchivedTicketDao extends BaseDAO {

    public ArchivedTicketDao() {
        super();
    }

    private MongoCollection<Document> getCollection() {
        try {
            return database.getCollection("ArchivedTickets");
        } catch (Exception e) {
            System.out.println("An error occurred when getting the collection");
            throw new RuntimeException(e);
        }
    }

    public void addTicket(Document ticket) {
        Objects.requireNonNull(getCollection()).insertOne(ticket);
    }

    public List<Ticket> getAllArchivedTickets() {
        List<Ticket> tickets = new ArrayList<>();
        Objects.requireNonNull(getCollection()).find().forEach(document ->
        {
            Ticket ticket = new Ticket(
                    document.getString("Name"),
                    document.getString("Contact"),
                    LocalDate.parse(document.getString("Date")),
                    TicketStatus.valueOf(document.getString("Status")),
                    document.getInteger("TicketID")
            );
            ticket.setTicketSummary(document.getString("Summary"));
            ticket.setPriority(document.getString("Priority"));
            tickets.add(ticket);
        });
        return tickets;
    }
}
