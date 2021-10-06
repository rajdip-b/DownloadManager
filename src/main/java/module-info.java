module com.app.downloadmanager.downloadmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app.downloadmanager to javafx.fxml;
    exports com.app.downloadmanager;
}