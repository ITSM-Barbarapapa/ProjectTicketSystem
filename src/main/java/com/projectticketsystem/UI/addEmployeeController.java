package com.projectticketsystem.UI;

import javafx.event.ActionEvent;

public class addEmployeeController implements ViewController {

    public void onAddEmployeeClick(ActionEvent actionEvent) {
        actionEvent.consume();
        System.out.println("Add employee clicked");
    }
}
