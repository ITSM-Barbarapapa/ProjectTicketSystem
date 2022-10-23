package com.projectticketsystem.dal;


import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.projectticketsystem.model.Ticket;
import com.projectticketsystem.model.TicketStatus;
import com.projectticketsystem.model.User;
import org.bson.BsonType;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.*;
import static java.lang.System.out;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;

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
        Document document = new Document(TICKET_ID, ticket.getTicketID())
                .append("Name", ticket.getName())
                .append("Contact", ticket.getContact())
                .append("Impact", ticket.getImpact())
                .append("Urgency", ticket.getUrgency())
                .append("Priority", ticket.getPriority())
                .append("Category", ticket.getTicketCategory())
                .append("Summary", ticket.getTicketSummary())
                .append("Description", ticket.getTicketDescription())
                .append("Date", ticket.getDate().toString())
                .append("UserID", ticket.getUser().getID())
                .append("Status", ticket.getTicketStatus())
                .append("Reaction", ticket.getTicketReaction());

        getCollection().insertOne(document);
        out.println("Ticket added");
    }

    public void updateTicket(Ticket ticket)
    {
        //TODO: if ticket has no 'User' it needs to create one or add one so ErrorHandling
        Document found = getCollection().find(new Document().append("TicketID", ticket.getTicketID())).first();
        if (found == null)
        {
            out.println("Ticket not found in database");
            out.println("Could not update ticket");
            return;
        }

        Bson updatedValues = Updates.combine(
                Updates.set("Impact", ticket.getImpact()),
                Updates.set("Urgency", ticket.getUrgency()),
                Updates.set("Status", ticket.getTicketStatus()),
                Updates.set("Priority", ticket.getPriority()),
                Updates.set("UserID", ticket.getUser().getID()),
                Updates.set("Reaction", ticket.getTicketReaction()));

        UpdateOptions options = new UpdateOptions().upsert(true);

        getCollection().updateOne(found, updatedValues, options);
        out.println("Ticket updated");
    }

    private void deleteTicket(Document ticket) {
        Objects.requireNonNull(getCollection()).deleteOne(ticket);
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
        ticket.setUser(new UserDAO().getUserByID((document.getInteger("UserID"))));
        ticket.setTicketDescription(document.getString("Description"));
        ticket.setTicketReaction(document.getString("Reaction"));

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

    public List<Document> getTicketsToArchive() {
        //Date 2 years ago
        LocalDate date = LocalDate.now().minusYears(2);

        //Get all tickets older than 2 years and with status Resolved or ClosedWithoutResolved
        Bson filter = and(or(eq("Status", "Resolved"), eq("Status", "ClosedWithoutResolve")), lt("Date", date.toString()));

        //Get all documents that match the filter
        List<Document> tickets = Objects.requireNonNull(getCollection()).find(filter).into(new ArrayList<>());

        //delete documents from current collection
        for (Document ticket : tickets) {
            deleteTicket(ticket);
        }

        return tickets;
    }
    public List<TicketStatus> getAllTicketStatus()
    {
        List<TicketStatus> ticketStatus = new ArrayList<>();

        Bson filter = type("Status", BsonType.STRING);
        FindIterable<Document> results = getCollection().find(filter);

        for ( Document d : results)
        {
            ticketStatus.add(TicketStatus.valueOf(d.getString("Status")));
        }

        return ticketStatus;
    }
    public List<Ticket> getMyTickets(User user)
    {
        List<Ticket> myTickets = new ArrayList<>();

        Bson filter = eq("UserID", user.getID());
        FindIterable<Document> results = getCollection().find(filter);

        for ( Document d : results)
        {
            if (d == null)
            {
                return null;
            }

            Ticket ticket = new Ticket(
                    d.getString("Name"),
                    d.getString("Contact"),
                    LocalDate.parse(d.getString("Date")),
                    TicketStatus.valueOf(d.getString("Status")),
                    d.getInteger(TICKET_ID));

            myTickets.add(ticket);
        }

        return myTickets;
    }

}