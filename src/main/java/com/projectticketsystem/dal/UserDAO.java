package com.projectticketsystem.dal;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.projectticketsystem.model.Role;
import com.projectticketsystem.model.User;
import org.bson.BsonType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.type;

public class UserDAO extends BaseDAO
{

    public UserDAO()
    {
        super();
    }

    private MongoCollection<Document> getCollection()
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
        Document found = getCollection().find(new Document("UserID", userID)).first();
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

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        Bson filter = and(type("Password", BsonType.findByValue(5)), type("Salt", BsonType.findByValue(5)));
        for (Document found : getCollection().find(filter))
        {
            users.add(new User(
                    found.getInteger("UserID"),
                    found.getString("Username"),
                    found.get("Password", Binary.class).getData(),
                    found.get("Salt", Binary.class).getData(),
                    Role.valueOf(found.getString("Role"))));
        }
        return users;
    }

    public void addUser(User user)
    {
        Document document = new Document("UserID", user.getId())
                .append("Username", user.getName())
                .append("Password", user.getPassword().getHashPassword())
                .append("Salt", user.getPassword().getSalt())
                .append("Role", user.getRole().toString());
        getCollection().insertOne(document);
        System.out.println("User added");
    }

    public void updateUser(User user)
    {
        Document found = getCollection().find(new Document().append("UserID", user.getId())).first();
        if (found == null)
        {
            System.out.println("User not found in database");
            System.out.println("Could not update user");
            return;
        }

        Bson updatedValues = Updates.combine(
                Updates.set("Username", user.getName()),
                Updates.set("Password", user.getPassword().getHashPassword()),
                Updates.set("Salt", user.getPassword().getSalt()),
                Updates.set("Role", user.getRole().toString()));

        UpdateOptions options = new UpdateOptions().upsert(true);

        getCollection().updateOne(found, updatedValues, options);
        System.out.println("User updated");
    }

    public void deleteUser(User user)
    {
        getCollection().deleteOne(new Document("UserID", user.getId()));
        System.out.println("User deleted");
    }

    public int getNextUserId()
    {
        int nextId;
        Document found = getCollection().find().sort(new Document("UserID", -1)).first();
        assert found != null;
        nextId = found.getInteger("UserID");
        return nextId + 1;
    }

    /* public boolean checkPassword(String password, User user){
         // TODO make function to check password
    }*/
}

