package com.projectticketsystem.model;

import com.projectticketsystem.dal.UserDAO;

import java.time.LocalDate;

public class Ticket {
    private int ticketID;
    private String name;
    private String contact;
    private TicketStatus ticketStatus;
    private LocalDate date;
    private String impact;
    private String urgency;
    private String priority;
    private String ticketCategory;
    private String ticketSummary;
    private String ticketDescription;
    private String ticketReaction;
    private User user;
    private User employee;

    public Ticket(String name, String contact, LocalDate date, TicketStatus ticketStatus, int id) {
        this.name = name;
        this.contact = contact;
        this.ticketStatus = ticketStatus;
        this.date = date;
        this.ticketID = id;
    }

    public Ticket() {
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public int getTicketID() {
        return ticketID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmployeeUsername() {
        return employee.getName();
    }

    public String getTicketStatus() {
        return this.ticketStatus.toString();
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTicketImpact(String impact) {
        this.impact = impact;
    }

    public void setTicketUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getImpact() {
        return impact;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(String category) {
        this.ticketCategory = category;
    }

    public String getTicketSummary() {
        return ticketSummary;
    }

    public void setTicketSummary(String summary) {
        this.ticketSummary = summary;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String description) {
        this.ticketDescription = description;
    }

    public String getTicketReaction() {
        return ticketReaction;
    }

    public void setTicketReaction(String ticketReaction) {
        this.ticketReaction = ticketReaction;
    }

    public void assignNewEmployeeToTicket(String newEmployee) {
        UserDAO userDAO = new UserDAO();
        this.setEmployee(userDAO.getUserByName(newEmployee));
    }
}
