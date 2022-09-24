module com.projectticketsystem.projectticketsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.projectticketsystem.projectticketsystem to javafx.fxml;
    exports com.projectticketsystem.projectticketsystem;
}