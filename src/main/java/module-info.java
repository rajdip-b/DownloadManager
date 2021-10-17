module com.app.downloadmanager.downloadmanager {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.app.downloadmanager.controller;
    opens com.app.downloadmanager.controller;
    opens com.app.downloadmanager.model;
    opens com.app.downloadmanager to javafx.fxml;
    exports com.app.downloadmanager;
    //    opens com.app.downloadmanager.utils.classes to javafx.fxml;
    exports com.app.downloadmanager.model;
    exports com.app.downloadmanager.utils.classes.core;
    opens com.app.downloadmanager.utils.classes.core;
    exports com.app.downloadmanager.utils.classes.ui;
    opens com.app.downloadmanager.utils.classes.ui;
    exports com.app.downloadmanager.utils.interfaces;
}