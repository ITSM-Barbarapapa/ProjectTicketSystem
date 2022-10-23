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
import static java.lang.System.out;

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
            out.println("An error occurred when getting the collection" + e.getMessage());
            throw e;
        }
    }

   public User getUser(int userID)
    {
        Document found = getCollection().find(new Document("UserID", userID)).first();
        return getUser(found);
    }

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        Bson filter = and(type("Password", BsonType.findByValue(5)), type("Salt", BsonType.findByValue(5)));
        return AddFoundUsersToList(users, filter);
    }

    public List<User> getAllEmployees()
    {
        List<User> users = new ArrayList<>();
        Bson filter = and(type("Password", BsonType.findByValue(5)), type("Salt", BsonType.findByValue(5)), new Document("Role", Role.ServiceDeskEmployee.toString()));
        return AddFoundUsersToList(users, filter);
    }

    public void addUser(User user)
    {
        Document document = new Document("UserID", user.getID())
                .append("Username", user.getName())
                .append("Password", user.getPassword().getHashPassword())
                .append("Salt", user.getPassword().getSalt())
                .append("Role", user.getRole().toString());
        getCollection().insertOne(document);
        out.println("User added");
    }

    public void updateUser(User user)
    {
        Document found = getCollection().find(new Document().append("UserID", user.getID())).first();
        if (found == null)
        {
            out.println("User not found in database");
            out.println("Could not update user");
            return;
        }

        Bson updatedValues = Updates.combine(
                Updates.set("Username", user.getName()),
                Updates.set("Password", user.getPassword().getHashPassword()),
                Updates.set("Salt", user.getPassword().getSalt()),
                Updates.set("Role", user.getRole().toString()));

        UpdateOptions options = new UpdateOptions().upsert(true);

        getCollection().updateOne(found, updatedValues, options);
        out.println("User updated");
    }

    public void deleteUser(User user)
    {
        getCollection().deleteOne(new Document("UserID", user.getID()));
        out.println("User deleted");
    }

    public int getNextUserId()
    {
        int nextId;
        Document found = getCollection().find().sort(new Document("UserID", -1)).first();
        assert found != null;
        nextId = found.getInteger("UserID");
        return nextId + 1;
    }

    public User getUserByID(int userID)
    {
        Document found = getCollection().find(new Document("UserID", userID)).first();
        return getUser(found);
    }

    public boolean checkPassword(String password, User user)
    {
        return false; // Temporary return condition. Change when implemented.
         // TODO make function to check password
    }

    public List<User> getUsersByRole(Role role) {
        List<User> users = new ArrayList<>();
        Bson filter = and(type("Password", BsonType.findByValue(5)), type("Salt", BsonType.findByValue(5)), new Document("Role", role.toString()));
        return AddFoundUsersToList(users, filter);
    }

    private List<User> AddFoundUsersToList(List<User> users, Bson filter) {
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

    public User getUserByName(String userName) {
        Document found = getCollection().find(new Document("Username", userName)).first();
        return getUser(found);
    }

    private User getUser(Document found) {
        if (found == null)
        {
            out.println("User not found");
            return null;
        }

        out.println("User found");
        out.println(found.toJson());

        return new User(
                found.getInteger("UserID"),
                found.getString("Username"),
                found.get("Password", Binary.class).getData(),
                found.get("Salt", Binary.class).getData(),
                Role.valueOf(found.getString("Role")));
    }
}

