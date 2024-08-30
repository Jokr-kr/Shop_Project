module com.github.jokrkr.shopproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.httpserver;
    requires org.slf4j;
    requires org.json;
    requires java.net.http;

    opens com.github.jokrkr.shopproject to javafx.fxml;
    exports com.github.jokrkr.shopproject;
    exports com.github.jokrkr.shopproject.ui.controllers to javafx.fxml;
    opens com.github.jokrkr.shopproject.ui.controllers to javafx.fxml;
}