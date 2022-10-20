module com.Project.TicketSystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    opens com.projectticketsystem.ui to javafx.fxml;
    opens com.projectticketsystem.model to javafx.base;
    exports com.projectticketsystem.ui;
    exports com.projectticketsystem.model;
}