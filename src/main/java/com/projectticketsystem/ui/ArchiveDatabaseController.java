package com.projectticketsystem.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ArchiveDatabaseController extends BaseController implements Initializable {
    @FXML
    public Label usernameLabel;

    public ArchiveDatabaseController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO Populate the tableview with the archived tickets
    }

    @FXML
    public void onSearchFieldTextChange(ActionEvent actionEvent) {
    }

    @FXML
    public void onArchiveButtonClick(ActionEvent actionEvent) {
    }
}
