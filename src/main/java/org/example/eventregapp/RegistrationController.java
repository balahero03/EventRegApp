package org.example.eventregapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.eventregapp.model.Event;
import org.example.eventregapp.model.Registration;
import org.example.eventregapp.service.RegistrationService;
import org.example.eventregapp.util.DatabaseUtil;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

public class RegistrationController {

    @FXML
    private ComboBox<Event> eventComboBox;
    @FXML
    private Button loadRegistrationsButton;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<Registration> registrationsTable;
    @FXML
    private TableColumn<Registration, String> registrationParticipantColumn;
    @FXML
    private TableColumn<Registration, String> registrationEventColumn;
    @FXML
    private TableColumn<Registration, String> registrationDateColumn;
    @FXML
    private Button removeRegistrationButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Label eventStatusLabel;
    @FXML
    private Label registrationStatusLabel;

    private ObservableList<Event> eventsList;
    private ObservableList<Registration> registrationsList;

    @FXML
    private void initialize() {
        try {
            // Initialize observable lists
            eventsList = FXCollections.observableArrayList();
            registrationsList = FXCollections.observableArrayList();

            // Setup table columns
            setupRegistrationTableColumns();

            // Load events for combo box
            loadEvents();

        } catch (Exception e) {
            showError("Error initializing registration window: " + e.getMessage());
        }
    }

    private void setupRegistrationTableColumns() {
        registrationParticipantColumn.setCellValueFactory(cellData -> {
            Registration registration = cellData.getValue();
            try {
                return new javafx.beans.property.SimpleStringProperty(
                        registration.getParticipant().getFullName());
            } catch (Exception e) {
                return new javafx.beans.property.SimpleStringProperty("N/A");
            }
        });
        registrationEventColumn.setCellValueFactory(cellData -> {
            Registration registration = cellData.getValue();
            try {
                return new javafx.beans.property.SimpleStringProperty(
                        registration.getEvent().getEventName());
            } catch (Exception e) {
                return new javafx.beans.property.SimpleStringProperty("N/A");
            }
        });
        registrationDateColumn.setCellValueFactory(cellData -> {
            Registration registration = cellData.getValue();
            try {
                return new javafx.beans.property.SimpleStringProperty(
                        registration.getRegistrationDate().toString());
            } catch (Exception e) {
                return new javafx.beans.property.SimpleStringProperty("N/A");
            }
        });
    }

    private void loadEvents() {
        try (Session session = DatabaseUtil.getSession()) {
            List<Event> events = session.createQuery("FROM Event ORDER BY eventDate ASC", Event.class).list();
            eventsList.clear();
            eventsList.addAll(events);
            eventComboBox.setItems(eventsList);
        } catch (Exception e) {
            eventStatusLabel.setText("Error loading events: " + e.getMessage());
        }
    }

    @FXML
    private void loadRegistrations() {
        Event selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            eventStatusLabel.setText("Please select an event first");
            return;
        }

        try {
            // Load registrations with proper eager fetching to avoid lazy loading issues
            List<Registration> registrations = loadRegistrationsWithParticipants(selectedEvent);
            registrationsList.clear();
            registrationsList.addAll(registrations);
            registrationsTable.setItems(registrationsList);

            eventStatusLabel
                    .setText("Loaded " + registrations.size() + " registrations for " + selectedEvent.getEventName());
            registrationStatusLabel.setText("");

        } catch (Exception e) {
            eventStatusLabel.setText("Error loading registrations: " + e.getMessage());
            e.printStackTrace(); // For debugging
        }
    }

    private List<Registration> loadRegistrationsWithParticipants(Event event) {
        try (Session session = DatabaseUtil.getSession()) {
            // Use JOIN FETCH to eagerly load participant and event data
            String query = "SELECT r FROM Registration r " +
                    "JOIN FETCH r.participant p " +
                    "JOIN FETCH r.event e " +
                    "WHERE r.event = :event " +
                    "ORDER BY r.registrationDate DESC";

            return session.createQuery(query, Registration.class)
                    .setParameter("event", event)
                    .list();
        } catch (Exception e) {
            System.err.println("Error loading registrations: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    @FXML
    private void removeRegistration() {
        Registration selectedRegistration = registrationsTable.getSelectionModel().getSelectedItem();
        if (selectedRegistration == null) {
            registrationStatusLabel.setText("Please select a registration to remove");
            return;
        }

        String result = RegistrationService.removeRegistrationById(selectedRegistration.getRegistrationId());
        registrationStatusLabel.setText(result);

        if (result.contains("successful")) {
            // Refresh the registrations list
            loadRegistrations();
        }
    }

    @FXML
    private void refreshRegistrations() {
        loadRegistrations();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showRegistrationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    RegistrationController.class.getResource("/org/example/eventregapp/registration-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);

            Stage stage = new Stage();
            stage.setTitle("INVENTE'25 | Event Registrations — SSN College of Engineering");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error loading registration window: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
