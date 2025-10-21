package org.example.eventregapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.eventregapp.model.Event;
import org.example.eventregapp.model.Participant;
import org.example.eventregapp.util.DatabaseUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

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
    private TextField eventNameField;
    @FXML
    private TextField eventDateField;
    @FXML
    private TextField totalSeatsField;
    @FXML
    private Button addEventButton;
    @FXML
    private Button updateEventButton;
    @FXML
    private Button deleteEventButton;
    @FXML
    private Label eventStatusLabel;

    // Participant components
    @FXML
    private TableView<Participant> participantsTable;
    @FXML
    private TableColumn<Participant, String> participantNameColumn;
    @FXML
    private TableColumn<Participant, String> participantEmailColumn;
    @FXML
    private TableColumn<Participant, String> participantRoleColumn;
    @FXML
    private TextField participantNameField;
    @FXML
    private TextField participantEmailField;
    @FXML
    private TextField participantPasswordField;
    @FXML
    private TextField participantRoleField;
    @FXML
    private Button addParticipantButton;
    @FXML
    private Button updateParticipantButton;
    @FXML
    private Button deleteParticipantButton;
    @FXML
    private Label participantStatusLabel;

    // Observable lists
    private ObservableList<Event> eventsList;
    private ObservableList<Participant> participantsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Initialize observable lists
            eventsList = FXCollections.observableArrayList();
            participantsList = FXCollections.observableArrayList();

            // Setup table columns
            setupEventTableColumns();
            setupParticipantTableColumns();

            // Load initial data
            loadEvents();
            loadParticipants();

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

    private void setupParticipantTableColumns() {
        participantNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        participantEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        participantRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    // Event CRUD Operations
    @FXML
    private void addEvent() {
        try {
            String name = eventNameField.getText().trim();
            String dateStr = eventDateField.getText().trim();
            String seatsStr = totalSeatsField.getText().trim();

            if (name.isEmpty() || dateStr.isEmpty() || seatsStr.isEmpty()) {
                eventStatusLabel.setText("Please fill all fields");
                return;
            }

            LocalDate date = LocalDate.parse(dateStr);
            int seats = Integer.parseInt(seatsStr);

            Event event = new Event(name, date, seats);

            try (Session session = DatabaseUtil.getSession()) {
                Transaction transaction = session.beginTransaction();
                session.save(event);
                transaction.commit();
                eventStatusLabel.setText("Event added successfully");
                clearEventFields();
                loadEvents();
            }

        } catch (Exception e) {
            eventStatusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void updateEvent() {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            eventStatusLabel.setText("Please select an event to update");
            return;
        }

        try {
            String name = eventNameField.getText().trim();
            String dateStr = eventDateField.getText().trim();
            String seatsStr = totalSeatsField.getText().trim();

            if (name.isEmpty() || dateStr.isEmpty() || seatsStr.isEmpty()) {
                eventStatusLabel.setText("Please fill all fields");
                return;
            }

            selectedEvent.setEventName(name);
            selectedEvent.setEventDate(LocalDate.parse(dateStr));
            selectedEvent.setTotalSeats(Integer.parseInt(seatsStr));

            try (Session session = DatabaseUtil.getSession()) {
                Transaction transaction = session.beginTransaction();
                session.update(selectedEvent);
                transaction.commit();
                eventStatusLabel.setText("Event updated successfully");
                clearEventFields();
                loadEvents();
            }

        } catch (Exception e) {
            eventStatusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void deleteEvent() {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            eventStatusLabel.setText("Please select an event to delete");
            return;
        }

        try (Session session = DatabaseUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(selectedEvent);
            transaction.commit();
            eventStatusLabel.setText("Event deleted successfully");
            loadEvents();
        } catch (Exception e) {
            eventStatusLabel.setText("Error: " + e.getMessage());
        }
    }

    // Participant CRUD Operations
    @FXML
    private void addParticipant() {
        try {
            String name = participantNameField.getText().trim();
            String email = participantEmailField.getText().trim();
            String password = participantPasswordField.getText().trim();
            String role = participantRoleField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                participantStatusLabel.setText("Please fill all fields");
                return;
            }

            Participant participant = new Participant(name, email, password, role);

            try (Session session = DatabaseUtil.getSession()) {
                Transaction transaction = session.beginTransaction();
                session.save(participant);
                transaction.commit();
                participantStatusLabel.setText("Participant added successfully");
                clearParticipantFields();
                loadParticipants();
            }

        } catch (Exception e) {
            participantStatusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void updateParticipant() {
        Participant selectedParticipant = participantsTable.getSelectionModel().getSelectedItem();
        if (selectedParticipant == null) {
            participantStatusLabel.setText("Please select a participant to update");
            return;
        }

        try {
            String name = participantNameField.getText().trim();
            String email = participantEmailField.getText().trim();
            String password = participantPasswordField.getText().trim();
            String role = participantRoleField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                participantStatusLabel.setText("Please fill all fields");
                return;
            }

            selectedParticipant.setFullName(name);
            selectedParticipant.setEmail(email);
            selectedParticipant.setPassword(password);
            selectedParticipant.setRole(role);

            try (Session session = DatabaseUtil.getSession()) {
                Transaction transaction = session.beginTransaction();
                session.update(selectedParticipant);
                transaction.commit();
                participantStatusLabel.setText("Participant updated successfully");
                clearParticipantFields();
                loadParticipants();
            }

        } catch (Exception e) {
            participantStatusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void deleteParticipant() {
        Participant selectedParticipant = participantsTable.getSelectionModel().getSelectedItem();
        if (selectedParticipant == null) {
            participantStatusLabel.setText("Please select a participant to delete");
            return;
        }

        try (Session session = DatabaseUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(selectedParticipant);
            transaction.commit();
            participantStatusLabel.setText("Participant deleted successfully");
            loadParticipants();
        } catch (Exception e) {
            participantStatusLabel.setText("Error: " + e.getMessage());
        }
    }

    // Load data methods
    private void loadEvents() {
        try (Session session = DatabaseUtil.getSession()) {
            List<Event> events = session.createQuery("FROM Event", Event.class).list();
            eventsList.clear();
            eventsList.addAll(events);
            eventsTable.setItems(eventsList);
        } catch (Exception e) {
            eventStatusLabel.setText("Error loading events: " + e.getMessage());
        }
    }

    private void loadParticipants() {
        try (Session session = DatabaseUtil.getSession()) {
            List<Participant> participants = session.createQuery("FROM Participant", Participant.class).list();
            participantsList.clear();
            participantsList.addAll(participants);
            participantsTable.setItems(participantsList);
        } catch (Exception e) {
            participantStatusLabel.setText("Error loading participants: " + e.getMessage());
        }
    }

    // Helper methods
    private void clearEventFields() {
        eventNameField.clear();
        eventDateField.clear();
        totalSeatsField.clear();
    }

    private void clearParticipantFields() {
        participantNameField.clear();
        participantEmailField.clear();
        participantPasswordField.clear();
        participantRoleField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}