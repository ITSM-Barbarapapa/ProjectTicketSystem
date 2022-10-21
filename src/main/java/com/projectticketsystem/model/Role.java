package com.projectticketsystem.model;

public enum Role {
    RegularEmployee("Normale Medewerker"),
    ServiceDeskEmployee("Service Desk Medewerker"),
    Administrator("Administrator");

    public final String name;

    Role(String name) {
        this.name = name;
    }
}
