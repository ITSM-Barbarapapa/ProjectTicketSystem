package com.projectticketsystem.Model;

import java.time.LocalDate;

public class Ticket {
    private User user;
    private TicketStatus ticketStatus;
    private String userInput;
    private String ticketSubject;
    private LocalDate date;

    public Ticket(User user, TicketStatus ticketStatus, String userInput, String ticketSubject, LocalDate date) {
        this.user = user;
        this.ticketStatus = ticketStatus;
        this.userInput = userInput;
        this.ticketSubject = ticketSubject;
        this.date = date;
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

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getTicketSubject() {
        return ticketSubject;
    }

    public void setTicketSubject(String ticketSubject) {
        this.ticketSubject = ticketSubject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
