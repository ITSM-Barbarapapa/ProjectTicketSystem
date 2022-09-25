package com.ProjectTicketSystemDal;

import com.ProjectTicketSystemModel.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class UserDAO extends BaseDAO
{
    MongoDatabase database;

    public UserDAO()
    {
        database = ConnectDatabase();
    }

    public static void main(String[] args)
    {
        UserDAO userDAO = new UserDAO();
        System.out.println("Adding user to database");
        userDAO.AddTestUser(new User(6, "Luke","Luke123!", Role.RegularEmployee));
        userDAO.GetUser(6);
    }

    private MongoCollection<Document> GetCollection()
    {
        try {
            return database.getCollection("Users");
        } catch (Exception e) {
            System.out.println("An error occurred when getting the collection" + e.getMessage());
            return null;
        }
    }

    private User GetUser(int userID)
    {
        Document found = GetCollection().find(new Document("UserID", userID)).first();
        if (found == null)
        {
            System.out.println("User not found");
            return null;
        }
        System.out.println("User found");
        System.out.println(found.toJson());


        return new User(
                found.getInteger("UserID"),
                found.getString("Username"),
                found.getString("Password"),
                Role.valueOf(found.getString(("Role"))));
    }

    private void AddTestUser(User user)
    {
        Document document = new Document("UserID", user.getId())
                .append("Username", user.getUsername())
                .append("Password", user.getPassword())
                .append("Role", user.getRole().toString());
        GetCollection().insertOne(document);
        System.out.println("User added");
    }
}