module com.app.downloadmanager.downloadmanager {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.app.downloadmanager.controller;
    opens com.app.downloadmanager.controller;
    opens com.app.downloadmanager.model;
    opens com.app.downloadmanager to javafx.fxml;
    exports com.app.downloadmanager;
    exports com.app.downloadmanager.utils.classes;
//    opens com.app.downloadmanager.utils.classes to javafx.fxml;
    opens com.app.downloadmanager.utils.classes;
}