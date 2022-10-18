package com.projectticketsystem.DAL;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.projectticketsystem.Model.Ticket;
import com.projectticketsystem.Model.TicketStatus;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TicketDAO extends BaseDAO
{
    MongoDatabase database;

    public TicketDAO()
    {
        database = ConnectDatabase();
    }

    private MongoCollection<Document> GetCollection()
    {
        try {
            return database.getCollection("Tickets");
        } catch (Exception e) {
            System.out.println("An error occurred when getting the collection" + e.getMessage());
            return null;
        }
    }

    public Ticket getTicketByID(int ticketID)
    {
        Document found = Objects.requireNonNull(GetCollection()).find(new Document("TicketID", ticketID)).first();
        if (found == null)
        {
            System.out.println("Ticket not found");
            return null;
        }

        System.out.println("Ticket found");
        System.out.println(found.toJson());

        return generateTicket(found);
    }

    public void addTicket(Ticket ticket)
    {
        Document document = new Document("TicketID", ticket.getTicketId())
                .append("Name", ticket.getName())
                .append("Contact", ticket.getContact())
                .append("Impact", ticket.getImpact())
                .append("Urgency", ticket.getUrgency())
                .append("Priority", ticket.getPriority())
                .append("Category", ticket.getTicketCategory())
                .append("Summary", ticket.getTicketSummary())
                .append("Description", ticket.getTicketDescription())
                .append("Date", ticket.getDate().toString())
                .append("Status", ticket.getTicketStatus().toString());

        GetCollection().insertOne(document);
        System.out.println("Ticket added");
    }

    public void updateTicket(Ticket ticket)
    {

    }

    public void deleteTicket(Ticket ticket)
    {

    }

    public int getHighestTicketID() {
        Document doc = Objects.requireNonNull(GetCollection()).find()
                .sort(Sorts.descending("TicketID"))
                .first();

        return Objects.requireNonNull(doc).getInteger("TicketID");
    }

    // This method collects all tickets from the database and returns them as a list
    public List<Ticket> getAllTickets()
    {
        List<Ticket> tickets = new ArrayList<>();
        List<Document> found = GetCollection().find().into(new ArrayList<>());
        if (found == null)
        {
            System.out.println("Something went wrong while getting all tickets...");
            return null;
        }
        for (Document document : found)
        {
            Ticket ticket = generateTicket(document);
            tickets.add(ticket);
            System.out.println("Ticket Converted");
        }
        return tickets;
    }

    private Ticket generateTicket(Document document) {
        Ticket ticket = new Ticket(
                document.getString("Name"),
                document.getString("Contact"),
                LocalDate.parse(document.getString("Date")),
                TicketStatus.valueOf(document.getString("Status")),
                document.getInteger("TicketID"));

        ticket.setTicketImpact(document.getString("Impact"));
        ticket.setTicketUrgency(document.getString("Urgency"));
        ticket.setTicketPriority(document.getString("Priority"));
        ticket.setTicketSummary(document.getString("Summary"));
        ticket.setTicketCategory(document.getString("Category"));
        ticket.setTicketDescription(document.getString("Description"));
        return ticket;
    }
}