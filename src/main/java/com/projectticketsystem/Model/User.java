package com.projectticketsystem.Model;

public class User {

    private int id;
    private String name;
    private HashedPassword password;
    private Role role;

    public User(int id, String name, byte[] hashedPassword, byte[] salt, Role role) {
        this.id = id;
        this.name = name;
        password = new HashedPassword(hashedPassword, salt);
        this.role = role;
    }

    public User(int id, String name, HashedPassword password, Role valueOf) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = valueOf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashedPassword getPassword() {
        return password;
    }
    public void setPassword(HashedPassword password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
