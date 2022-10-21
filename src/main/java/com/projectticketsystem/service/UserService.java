package com.projectticketsystem.service;

import com.projectticketsystem.dal.UserDAO;
import com.projectticketsystem.model.Role;
import com.projectticketsystem.model.User;

import java.util.List;

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

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public List<User> getUsersByRole(Role role){return userDAO.getUsersByRole(role);}

    /*public boolean checkPassword(String password, User user){
        // TODO return userDAO.checkPassword(password, user);
    }*/

    public int getNextUserId(){
        return userDAO.getNextUserId();
    }

    public User getUserByName(String value) {return userDAO.getUserByName(value);}
}
