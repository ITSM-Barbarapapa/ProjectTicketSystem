package com.projectticketsystem.DAL;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.projectticketsystem.Model.Role;
import com.projectticketsystem.Model.Ticket;
import com.projectticketsystem.Model.TicketStatus;
import com.projectticketsystem.Model.User;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;

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

        return new Ticket(
                found.getString("Name"),
                found.getString("Contact"),
                found.getString("Impact"),
                found.getString("Urgency"),
                found.getString("Priority"),
                LocalDate.parse(found.getString("Date")),
                TicketStatus.valueOf(found.getString("Status")),
                found.getInteger("TicketID"));
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
}