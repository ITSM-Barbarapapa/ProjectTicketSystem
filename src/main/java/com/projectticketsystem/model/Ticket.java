package com.projectticketsystem.model;

import com.projectticketsystem.dal.UserDAO;

import java.time.LocalDate;

public class Ticket {
    private final int ticketID;
    private final String name;
    private final String contact;
    private TicketStatus ticketStatus;
    private final LocalDate date;
    private String impact;
    private String urgency;
    private String priority;
    private String ticketCategory;
    private String ticketSummary;
    private String ticketDescription;
    private String ticketReaction;
    private User user;

    public Ticket(String name, String contact, LocalDate date, TicketStatus ticketStatus, int id) {
        this.name = name;
        this.contact = contact;
        this.ticketStatus = ticketStatus;
        this.date = date;
        this.ticketID = id;
    }

    public int getTicketID() { return ticketID; }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTicketStatus() {return this.ticketStatus.toString(); }

    public void setTicketStatus(TicketStatus ticketStatus) { this.ticketStatus = ticketStatus; }


    public LocalDate getDate() {
        return date;
    }

    public void setTicketCategory(String category) {this.ticketCategory = category;}
    public void setTicketSummary(String summary){this.ticketSummary = summary;}
    public void setTicketDescription(String description) {this.ticketDescription = description;}
    public void setTicketImpact(String impact) {this.impact = impact;}
    public void setTicketUrgency(String urgency) {this.urgency = urgency;}
    public void setTicketPriority(String priority) {this.priority = priority;}
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

    public String getTicketCategory() {
        return ticketCategory;
    }

    public String getTicketSummary() {
        return ticketSummary;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public String getTicketReaction() {
        return ticketReaction;
    }

    public void setTicketReaction(String ticketReaction) {
        this.ticketReaction = ticketReaction;
    }

    public void assignNewEmployeeToTicket(String newEmployee)
    {
        UserDAO userDAO = new UserDAO();
        this.setUser(userDAO.getUserByName(newEmployee));
    }
}
