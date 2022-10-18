module com.projectticketsystem.projectticketsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    opens com.projectticketsystem.UI to javafx.fxml;
    opens com.projectticketsystem.model to javafx.base;
    exports com.projectticketsystem.UI;
}