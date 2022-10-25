package com.projectticketsystem.dal;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public abstract class BaseDAO {
    private static final String URL = "mongodb+srv://user:user@users.jbnxxyk.mongodb.net/test";
    protected MongoDatabase database;

    protected BaseDAO() {
        connectDatabase();
    }

    private void connectDatabase() {
        try {
            MongoClient mongoClient = MongoClients.create(URL);
            database = mongoClient.getDatabase("ProjectNoSQL");

        } catch (Exception e) {
            System.out.println("An error occurred when connecting to the database" + e.getMessage());
        }
    }


}