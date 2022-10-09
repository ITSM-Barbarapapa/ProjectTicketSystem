package com.projectticketsystem.Service;

import com.projectticketsystem.DAL.UserDAO;
import com.projectticketsystem.Model.User;

public class UserService {

    private static final UserDAO userDAO = new UserDAO();

    public User getUser(int userID){
        return userDAO.getUser(userID);
    }

    public void addUser(User user){
        userDAO.addUser(user);
    }

    public void updateUser(User user){
        userDAO.updateUser(user);
    }

    public void deleteUser(User user){
        userDAO.deleteUser(user);
    }

}
