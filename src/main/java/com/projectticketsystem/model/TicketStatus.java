package com.projectticketsystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum TicketStatus {
    Open, Resolved, ClosedWithoutResolve;

    public static ObservableList<String> getObservableList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (TicketStatus status : TicketStatus.values())
            list.add(status.toString());
        return list;
    }
}
