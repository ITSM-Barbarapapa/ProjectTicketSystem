package com.ProjectTicketSystemDal;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class UserDao extends BaseDAO{

    public UserDao() {
        BaseDAO baseDAO = new BaseDAO();
        MongoDatabase database = baseDAO.ConnectDatabase();
        MongoCollection<Document> collection = database.getCollection("users");
    }
}

