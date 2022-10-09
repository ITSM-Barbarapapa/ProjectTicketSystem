package com.projectticketsystem.DAL;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.projectticketsystem.Model.Role;
import com.projectticketsystem.Model.User;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

public class UserDAO extends BaseDAO
{
    MongoDatabase database;

    public UserDAO()
    {
        database = ConnectDatabase();
    }

    private MongoCollection<Document> GetCollection()
    {
        try {
            return database.getCollection("Users");
        } catch (Exception e) {
            System.out.println("An error occurred when getting the collection" + e.getMessage());
            throw e;
        }
    }

   public User getUser(int userID)
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
                found.get("Password", Binary.class).getData(),
                found.get("Salt", Binary.class).getData(),
                Role.valueOf(found.getString("Role")));

    }

    public void addUser(User user)
    {
        Document document = new Document("UserID", user.getId())
                .append("Username", user.getUsername())
                .append("Password", user.getPassword().getHashPassword())
                .append("Salt", user.getPassword().getSalt())
                .append("Role", user.getRole().toString());
        GetCollection().insertOne(document);
        System.out.println("User added");
    }

    public void updateUser(User user)
    {
        Document found = GetCollection().find(new Document().append("UserID", user.getId())).first();
        if (found == null)
        {
            System.out.println("User not found in database");
            System.out.println("Could not update user");
            return;
        }

        Bson updatedValues = Updates.combine(
                Updates.set("Username", user.getUsername()),
                Updates.set("Password", user.getPassword().getHashPassword()),
                Updates.set("Salt", user.getPassword().getSalt()),
                Updates.set("Role", user.getRole().toString()));

        UpdateOptions options = new UpdateOptions().upsert(true);

        GetCollection().updateOne(found, updatedValues, options);
        System.out.println("User updated");
    }

    public void deleteUser(User user)
    {
        GetCollection().deleteOne(new Document("UserID", user.getId()));
        System.out.println("User deleted");
    }
}

