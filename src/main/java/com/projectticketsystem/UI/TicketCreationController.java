package com.projectticketsystem.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;;import java.io.IOException;

public class TicketCreationController {
    public TextArea DescriptionTextField;
    public TextField summaryTextField;
    public ChoiceBox<String> categoryChoiceBox;
    public ChoiceBox<String> impactChoiceBox;
    public TextField nameTextField;
    public TextField contactTextField;
    public TextField calculatePriorityTextBox;
    public ChoiceBox<String> urgencyChoiceBox;
    public Button addTicketButton;
    public ImageView homeButton;

    @FXML
    private void OnHomeButtonClick(MouseEvent event) throws IOException {
        // Go to next window
        // get current Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // load new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public TicketCreationController(){

    }


    public void itemChange(ActionEvent actionEvent) {
        String impactValue = impactChoiceBox.getValue();
        String urgencyValue = urgencyChoiceBox.getValue();
        if (impactValue == null || urgencyValue == null){
            return;
        }

        int impactNumber = Integer.parseInt(String.valueOf(impactValue.toCharArray()[0]));
        int urgencyNumber = Integer.parseInt(String.valueOf(urgencyValue.toCharArray()[0]));

        calculatePriorityTextBox.setText(String.valueOf(CalculatePriority(impactNumber, urgencyNumber)));
    }

    private int CalculatePriority(int impact, int urgency){

        return impact + urgency - 1;
    }

}
