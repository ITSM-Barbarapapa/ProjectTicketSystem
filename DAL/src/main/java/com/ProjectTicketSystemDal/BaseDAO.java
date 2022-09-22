package com.ProjectTicketSystemDal;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class BaseDAO
{
    public BaseDAO(){

    }
    private final String uri = "mongodb+srv://user:user@users.jbnxxyk.mongodb.net/test";
    static void main(String[] args)
    {
        System.out.println("Main method is empty!");
    }

    protected MongoDatabase ConnectDatabase()
    {
        try {
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProjectTicketSystem");
            System.out.println("Connected to database!");
            return database;
        } catch (Exception e) {
            System.out.println("An error occurred when connecting to the database" + e.getMessage());
            return null;
        }
    }

}
