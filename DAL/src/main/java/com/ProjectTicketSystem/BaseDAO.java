package com.ProjectTicketSystem;

public class BaseDAO
{
    public static final String DB_URI = "mongodb+srv://user:user@users.jbnxxyk.mongodb.net/test ";

    MongoClientURI uri = new MongoClientURI(DB_URI);
}
