package com.projectticketsystem.Model;

import java.time.LocalDate;

public class Ticket {
    private final int ticketID;
    private final String name;
    private final String contact;
    private final String impact;
    private final String urgency;
    private final String priority;
    private String ticketCategory;
    private String ticketSummary;
    private String ticketDescription;
    private User user;
    private final TicketStatus ticketStatus;
    private final LocalDate date;


    public Ticket(String name, String contact, String impact, String urgency, String priority, LocalDate date, TicketStatus ticketStatus, int id) {
        this.name = name;
        this.contact = contact;
        this.impact = impact;
        this.urgency = urgency;
        this.priority = priority;
        this.ticketStatus = ticketStatus;
        this.date = date;
        this.ticketID = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTicketCategory(String category) {this.ticketCategory = category;}
    public void setTicketSummary(String summary){this.ticketSummary = summary;}
    public void setTicketDescription(String description) {this.ticketDescription = description;}
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

    public int getTicketId() {
        return ticketID;
    }
}
