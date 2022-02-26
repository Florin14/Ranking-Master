module com.example.livescore {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.rankingmaster to javafx.fxml;
    exports com.example.rankingmaster;
    exports com.example.rankingmaster.controller;
    opens com.example.rankingmaster.controller to javafx.fxml;
    exports com.example.rankingmaster.database;
    exports com.example.rankingmaster.service;
    exports com.example.rankingmaster.repository;
    exports com.example.rankingmaster.domain;
    opens com.example.rankingmaster.database to javafx.fxml;
}