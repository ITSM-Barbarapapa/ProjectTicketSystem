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

    }

    public void updateTicket(Ticket ticket)
    {

    }

    public void deleteTicket(Ticket ticket)
    {

    }
}