module com.projectticketsystem.projectticketsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.projectticketsystem.projectticketsystem to javafx.fxml;
    exports com.projectticketsystem.projectticketsystem;
}