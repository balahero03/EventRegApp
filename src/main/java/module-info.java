module org.example.eventregapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;
    requires java.naming;
    requires java.desktop;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;

    opens org.example.eventregapp to javafx.fxml;
    opens org.example.eventregapp.model to org.hibernate.orm.core;
    opens org.example.eventregapp.util to org.hibernate.orm.core;

    exports org.example.eventregapp;
    exports org.example.eventregapp.model;
    exports org.example.eventregapp.util;
}