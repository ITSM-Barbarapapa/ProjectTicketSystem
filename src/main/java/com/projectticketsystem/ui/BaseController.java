package com.projectticketsystem.ui;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {
    protected void loadNextStage(String fxmlFileName, BaseController controller, Event event) {
        Scene scene = loadScene(fxmlFileName, controller);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        event.consume();
    }

    protected void loadNextInNewStage(String fxmlFileName, BaseController controller, Event event) {
        Scene scene = loadScene(fxmlFileName, controller);
        Stage stage = new Stage();
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        event.consume();
    }

    private Scene loadScene(String fxmlFileName, BaseController controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        if (controller != null) {
            loader.setController(controller);
        }
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Cannot run " + fxmlFileName + "\n" + e);
        }
        return new Scene(root);
    }

}

