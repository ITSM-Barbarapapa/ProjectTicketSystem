package com.ProjectTicketSystemDal;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class UserDAO
{
    MongoDatabase database;

    public UserDAO()
    {
        database = new BaseDAO().ConnectDatabase();
    }

    public static void main(String[] args)
    {
        UserDAO userDAO = new UserDAO();
        System.out.println("Adding user to database");
        userDAO.AddTestUser(new User("6", "Luke","Luke123!", Role.Employee));
        userDAO.GetUser("6");
    }

    private MongoCollection GetCollection()
    {
        try {
            return database.getCollection("Users");
        } catch (Exception e) {
            System.out.println("An error occurred when getting the collection" + e.getMessage());
            return null;
        }
    }

    private User GetUser(String userID)
    {
        Document found = (Document) GetCollection().find(new Document("UserID", userID)).first();
        if (found == null)
        {
            System.out.println("User not found");
            return null;
        }
        System.out.println("User found");
        System.out.println(found.toJson());
        return new User(found.getString("UserID"),
                found.getString("Username"),
                found.getString("Password"));
    }

    private void AddTestUser(User user)
    {
        Document document = new Document("UserID", user.UserID)
                .append("Username", user.Username)
                .append("Password", user.Password)
                .append("Role", user.Role);
        GetCollection().insertOne(document);
        System.out.println("User added");
    }
}