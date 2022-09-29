module com.projectticketsystem.projectticketsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;

    opens com.projectticketsystem.UI to javafx.fxml;
    exports com.projectticketsystem.UI;
}