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
import org.example.eventregapp.model.Registration;
import org.example.eventregapp.service.RegistrationService;
import org.example.eventregapp.util.DatabaseUtil;
import org.example.eventregapp.util.ValidationUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AdminController {

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
    private TableColumn<Event, Integer> registrationCountColumn;
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
    private Button viewRegistrationsButton;
    @FXML
    private Label eventStatusLabel;

    // User management components
    @FXML
    private TextField userNameField;
    @FXML
    private TextField userEmailField;
    @FXML
    private PasswordField userPasswordField;
    @FXML
    private ComboBox<String> userRoleComboBox;
    @FXML
    private Button createUserButton;
    @FXML
    private Button createAdminButton;
    @FXML
    private Label userStatusLabel;
    @FXML
    private TableView<Participant> usersTable;
    @FXML
    private TableColumn<Participant, String> userNameColumn;
    @FXML
    private TableColumn<Participant, String> userEmailColumn;
    @FXML
    private TableColumn<Participant, String> userRoleColumn;

    // Navigation components
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logoutButton;

    // Observable lists
    private ObservableList<Event> eventsList;
    private ObservableList<Participant> usersList;

    public void setCurrentUser(Participant user) {
        this.currentUser = user;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + user.getFullName() + "!");
        }
        initializeData();
    }

    @FXML
    private void initialize() {
        // Initialize ComboBox even if currentUser is not set yet
        if (userRoleComboBox != null) {
            userRoleComboBox.getItems().add("user");
            userRoleComboBox.getItems().add("admin");
        }

        if (currentUser != null) {
            initializeData();
        }
    }

    private void initializeData() {
        try {
            // Initialize observable lists
            eventsList = FXCollections.observableArrayList();
            usersList = FXCollections.observableArrayList();

            // Setup table columns
            setupEventTableColumns();
            setupUserTableColumns();

            // Load initial data
            loadEvents();
            loadUsers();

        } catch (Exception e) {
            showError("Error initializing application: " + e.getMessage());
        }
    }

    private void setupEventTableColumns() {
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        totalSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        registrationCountColumn.setCellValueFactory(new PropertyValueFactory<>("registrationCount"));
    }

    private void setupUserTableColumns() {
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    // Event CRUD Operations
    @FXML
    private void addEvent() {
        try {
            String name = eventNameField.getText().trim();
            String dateStr = eventDateField.getText().trim();
            String seatsStr = totalSeatsField.getText().trim();

            // Validation
            if (name.isEmpty() || dateStr.isEmpty() || seatsStr.isEmpty()) {
                eventStatusLabel.setText("Please fill all fields");
                return;
            }

            // Validate name
            String nameError = ValidationUtil.getNameErrorMessage(name);
            if (nameError != null) {
                eventStatusLabel.setText(nameError);
                return;
            }

            // Validate date
            if (!ValidationUtil.isValidFutureDate(dateStr)) {
                eventStatusLabel.setText("Please enter a valid future date (YYYY-MM-DD)");
                return;
            }

            // Validate seats
            if (!ValidationUtil.isValidPositiveNumber(seatsStr)) {
                eventStatusLabel.setText("Total seats must be a positive number");
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

    // Registration Management Operations
    @FXML
    private void viewEventRegistrations() {
        RegistrationController.showRegistrationWindow();
    }

    // User Management Operations
    @FXML
    private void createUser() {
        createParticipant("user");
    }

    @FXML
    private void createAdmin() {
        createParticipant("admin");
    }

    private void createParticipant(String role) {
        try {
            String name = userNameField.getText().trim();
            String email = userEmailField.getText().trim();
            String password = userPasswordField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                userStatusLabel.setText("Please fill all fields");
                return;
            }

            // Validation
            String nameError = ValidationUtil.getNameErrorMessage(name);
            if (nameError != null) {
                userStatusLabel.setText(nameError);
                return;
            }

            String emailError = ValidationUtil.getEmailErrorMessage(email);
            if (emailError != null) {
                userStatusLabel.setText(emailError);
                return;
            }

            String passwordError = ValidationUtil.getPasswordErrorMessage(password);
            if (passwordError != null) {
                userStatusLabel.setText(passwordError);
                return;
            }

            Participant participant = new Participant(name, email, password, role);

            try (Session session = DatabaseUtil.getSession()) {
                Transaction transaction = session.beginTransaction();
                session.save(participant);
                transaction.commit();
                userStatusLabel.setText(role + " created successfully");
                clearUserFields();
                loadUsers();
            }

        } catch (Exception e) {
            userStatusLabel.setText("Error: " + e.getMessage());
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

    private void loadUsers() {
        try (Session session = DatabaseUtil.getSession()) {
            List<Participant> users = session.createQuery("FROM Participant", Participant.class).list();
            usersList.clear();
            usersList.addAll(users);
            usersTable.setItems(usersList);
        } catch (Exception e) {
            userStatusLabel.setText("Error loading users: " + e.getMessage());
        }
    }

    // Helper methods
    private void clearEventFields() {
        eventNameField.clear();
        eventDateField.clear();
        totalSeatsField.clear();
    }

    private void clearUserFields() {
        userNameField.clear();
        userEmailField.clear();
        userPasswordField.clear();
        userRoleComboBox.setValue(null);
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
            stage.setTitle("INVENTE'25 | SSN College of Engineering");
            stage.centerOnScreen();

        } catch (IOException e) {
            showError("Error loading login screen: " + e.getMessage());
        }
    }
}
