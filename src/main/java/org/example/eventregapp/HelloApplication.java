package org.example.eventregapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.eventregapp.util.DataInitializer;
import org.example.eventregapp.util.LoginTest;

import java.io.IOException;

/**
 * Simple Event Registration System
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize default data (admin and user accounts)
        DataInitializer.initializeDefaultData();

        // Test login connection to Participant table
        LoginTest.testLoginConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("INVENTE'25 | SSN College of Engineering");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}