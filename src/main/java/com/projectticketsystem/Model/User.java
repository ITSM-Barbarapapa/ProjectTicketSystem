package com.projectticketsystem.Model;

public class User {

    private int id;
    private String username;
    private HashedPassword password;
    private Role role;

    public User(int id, String username, byte[] hashedPassword, byte[] salt, Role role) {
        this.id = id;
        this.username = username;
        password = new HashedPassword(hashedPassword, salt);
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashedPassword getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
