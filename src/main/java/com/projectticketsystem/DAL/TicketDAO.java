package com.projectticketsystem.DAL;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.projectticketsystem.Model.Ticket;
import org.bson.Document;

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

    public void getTicket(Ticket ticket)
    {

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

    public int GetHighestTicketID() {
        int highestID = 0;



        return highestID;
    }
}