package org.example.eventregapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.eventregapp.model.Event;
import org.example.eventregapp.model.Participant;
import org.example.eventregapp.service.RegistrationService;
import org.example.eventregapp.util.DatabaseUtil;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

public class UserController {

    private Participant currentUser;

    // Event components
    @FXML
    private TableView<Event> eventsTable;
    @FXML
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, String> eventDateColumn;
    @FXML
    private TableColumn<Event, Integer> totalSeatsColumn;
    @FXML
    private TableColumn<Event, Integer> availableSeatsColumn;
    @FXML
    private Button registerButton;
    @FXML
    private Button unregisterButton;
    @FXML
    private Label eventStatusLabel;

    // Navigation components
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logoutButton;

    // Observable lists
    private ObservableList<Event> eventsList;

    public void setCurrentUser(Participant user) {
        this.currentUser = user;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + user.getFullName() + "!");
        }
        initializeData();
    }

    @FXML
    private void initialize() {
        if (currentUser != null) {
            initializeData();
        }
    }

    private void initializeData() {
        try {
            // Initialize observable lists
            eventsList = FXCollections.observableArrayList();

            // Setup table columns
            setupEventTableColumns();

            // Load initial data
            loadEvents();

        } catch (Exception e) {
            showError("Error initializing application: " + e.getMessage());
        }
    }

    private void setupEventTableColumns() {
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        totalSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
    }

    // Registration methods
    @FXML
    private void registerForEvent() {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            eventStatusLabel.setText("Please select an event to register");
            return;
        }

        String result = RegistrationService.registerForEvent(currentUser, selectedEvent);
        eventStatusLabel.setText(result);

        if (result.contains("successful")) {
            loadEvents(); // Refresh the events list
        }
    }

    @FXML
    private void unregisterFromEvent() {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            eventStatusLabel.setText("Please select an event to unregister");
            return;
        }

        String result = RegistrationService.removeRegistration(currentUser, selectedEvent);
        eventStatusLabel.setText(result);

        if (result.contains("successful")) {
            loadEvents(); // Refresh the events list
        }
    }

    // Load data methods
    private void loadEvents() {
        try (Session session = DatabaseUtil.getSession()) {
            // Load only available events (future dates and not full) for users
            String query = "FROM Event e WHERE e.eventDate > :today AND e.registrationCount < e.totalSeats ORDER BY e.eventDate ASC";
            List<Event> events = session.createQuery(query, Event.class)
                    .setParameter("today", java.time.LocalDate.now())
                    .list();
            eventsList.clear();
            eventsList.addAll(events);
            eventsTable.setItems(eventsList);
        } catch (Exception e) {
            eventStatusLabel.setText("Error loading events: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventregapp/login-view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login - Event Registration System");
            stage.centerOnScreen();

        } catch (IOException e) {
            showError("Error loading login screen: " + e.getMessage());
        }
    }
}
