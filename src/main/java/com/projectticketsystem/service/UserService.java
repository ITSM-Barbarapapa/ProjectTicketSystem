package com.projectticketsystem.service;

import com.projectticketsystem.dal.UserDAO;
import com.projectticketsystem.model.Role;
import com.projectticketsystem.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserService {

    private static final UserDAO userDAO = new UserDAO();

    public User getUser(int userID) {
        return userDAO.getUser(userID);
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<User> getAllEmployees() {
        return userDAO.getAllEmployees();
    }

    public ObservableList<String> getEmployeeNames() {
        ObservableList<String> employeeNames = FXCollections.observableArrayList();
        for (User user : getAllEmployees())
            employeeNames.add(user.getName());
        return employeeNames;
    }

    public List<User> getUsersByRole(Role role) {
        return userDAO.getUsersByRole(role);
    }

    public int getNextUserId() {
        return userDAO.getNextUserId();
    }

    public User getUserByName(String value) {
        return userDAO.getUserByName(value);
    }
}
