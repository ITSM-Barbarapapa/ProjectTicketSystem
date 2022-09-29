package com.projectticketsystem.DAL;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public abstract class BaseDAO
{
    private final String uri = "mongodb+srv://user:user@users.jbnxxyk.mongodb.net/test";
    public MongoDatabase ConnectDatabase()
    {
        try {
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("ProjectNoSQL");
            System.out.println("Connected to database!");
            return database;
        } catch (Exception e) {
            System.out.println("An error occurred when connecting to the database" + e.getMessage());
            return null;
        }
    }
}