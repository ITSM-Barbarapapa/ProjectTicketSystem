package com.projectticketsystem.dal;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.TicketStatus;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static java.lang.System.*;

public class TicketDAO extends BaseDAO
{
    static final String TICKET_ID = "TicketID";
    
    public TicketDAO()
    {
        super();
    }

    private MongoCollection<Document> getCollection()
    {
        try {
            return database.getCollection("Tickets");
        } catch (Exception e) {
            out.println("An error occurred when getting the collection" + e.getMessage());
            return null;
        }
    }

    public Ticket getTicketByID(int ticketID)
    {
        Document found = Objects.requireNonNull(getCollection()).find(new Document(TICKET_ID, ticketID)).first();
        if (found == null)
        {
            out.println("Ticket not found");
            return null;
        }

        out.println("Ticket found");
        out.println(found.toJson());

        return generateTicket(found);
    }

    public void addTicket(Ticket ticket)
    {
        Document document = new Document(TICKET_ID, ticket.getTicketId())
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

        getCollection().insertOne(document);
        out.println("Ticket added");
    }

    public void updateTicket(Ticket ticket)
    {
        // TODO create an update function
    }

    public void deleteTicket(Ticket ticket)
    {
        // TODO make an archive so tickets can't be deleted but kept in a pool
    }

    public int getHighestTicketID() {
        Document doc = Objects.requireNonNull(getCollection()).find()
                .sort(Sorts.descending(TICKET_ID))
                .first();
        return Objects.requireNonNull(doc).getInteger(TICKET_ID);
    }

    // This method collects all tickets from the database and returns them as a list
    public List<Ticket> getAllTickets()
    {
        List<Ticket> tickets = new ArrayList<>();
        List<Document> found = getCollection().find().into(new ArrayList<>());
        for (Document document : found)
        {
            Ticket ticket = generateTicket(document);
            tickets.add(ticket);
            out.println("Ticket Converted");
        }
        return tickets;
    }

    private Ticket generateTicket(Document document) {
        Ticket ticket = new Ticket(
                document.getString("Name"),
                document.getString("Contact"),
                LocalDate.parse(document.getString("Date")),
                TicketStatus.valueOf(document.getString("Status")),
                document.getInteger(TICKET_ID));

        ticket.setTicketImpact(document.getString("Impact"));
        ticket.setTicketUrgency(document.getString("Urgency"));
        ticket.setTicketPriority(document.getString("Priority"));
        ticket.setTicketSummary(document.getString("Summary"));
        ticket.setTicketCategory(document.getString("Category"));
        ticket.setTicketDescription(document.getString("Description"));
        return ticket;
    }

    public List<Ticket> getTicketsByFilter(String filter, String value)
    {
        List<Ticket> tickets = new ArrayList<>();
        List<Document> found = Objects.requireNonNull(getCollection()).find(eq(filter, value)).into(new ArrayList<>());
        for (Document document : found)
            tickets.add(generateTicket(document));
        return tickets;
    }

    public Ticket getTicketByFilter(String filter, String value)
    {
        Document found = Objects.requireNonNull(getCollection()).find(eq(filter, value)).first();
        if (found == null)
            return null;
        return generateTicket(found);
    }
}