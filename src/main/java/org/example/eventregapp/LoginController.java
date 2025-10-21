package org.example.eventregapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.eventregapp.model.Participant;
import org.example.eventregapp.service.AuthenticationService;
import org.example.eventregapp.util.DatabaseUtil;
import org.example.eventregapp.util.ValidationUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both email and password");
            return;
        }

        try {
            // Authenticate using the Participant table
            Participant participant = AuthenticationService.authenticate(email, password);

            if (participant == null) {
                statusLabel.setText("Invalid email or password");
                return;
            }

            // Redirect based on role from Participant table
            if (AuthenticationService.isAdmin(participant)) {
                redirectToAdminPanel(participant);
            } else if (AuthenticationService.isUser(participant)) {
                redirectToUserPanel(participant);
            } else {
                statusLabel.setText("Invalid user role: " + AuthenticationService.getUserRole(participant));
            }

        } catch (Exception e) {
            statusLabel.setText("Login error: " + e.getMessage());
            e.printStackTrace(); // For debugging
        }
    }

    private void redirectToAdminPanel(Participant participant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventregapp/admin-view.fxml"));
            Scene scene = new Scene(loader.load());

            AdminController controller = loader.getController();
            controller.setCurrentUser(participant);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin Panel - Event Registration System");
            stage.centerOnScreen();

        } catch (IOException e) {
            statusLabel.setText("Error loading admin panel: " + e.getMessage());
        }
    }

    private void redirectToUserPanel(Participant participant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventregapp/user-view.fxml"));
            Scene scene = new Scene(loader.load());

            UserController controller = loader.getController();
            controller.setCurrentUser(participant);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("User Dashboard - Event Registration System");
            stage.centerOnScreen();

        } catch (IOException e) {
            statusLabel.setText("Error loading user panel: " + e.getMessage());
        }
    }

    @FXML
    private void handleSignup() {
        // Create a simple signup dialog
        javafx.scene.control.Dialog<Participant> signupDialog = new javafx.scene.control.Dialog<>();
        signupDialog.setTitle("Create New Account");
        signupDialog.setHeaderText("Please fill in your details to create a new account");

        // Set the button types
        javafx.scene.control.ButtonType signupButtonType = new javafx.scene.control.ButtonType("Sign Up",
                javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        signupDialog.getDialogPane().getButtonTypes().addAll(signupButtonType, javafx.scene.control.ButtonType.CANCEL);

        // Create the signup form
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.setPromptText("Full Name");
        javafx.scene.control.TextField emailFieldSignup = new javafx.scene.control.TextField();
        emailFieldSignup.setPromptText("Email");
        javafx.scene.control.PasswordField passwordFieldSignup = new javafx.scene.control.PasswordField();
        passwordFieldSignup.setPromptText("Password");

        grid.add(new javafx.scene.control.Label("Full Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new javafx.scene.control.Label("Email:"), 0, 1);
        grid.add(emailFieldSignup, 1, 1);
        grid.add(new javafx.scene.control.Label("Password:"), 0, 2);
        grid.add(passwordFieldSignup, 1, 2);

        signupDialog.getDialogPane().setContent(grid);

        // Convert the result to a Participant when the signup button is clicked
        signupDialog.setResultConverter(dialogButton -> {
            if (dialogButton == signupButtonType) {
                String name = nameField.getText().trim();
                String email = emailFieldSignup.getText().trim();
                String password = passwordFieldSignup.getText().trim();

                // Validation
                String nameError = ValidationUtil.getNameErrorMessage(name);
                if (nameError != null) {
                    statusLabel.setText(nameError);
                    return null;
                }

                String emailError = ValidationUtil.getEmailErrorMessage(email);
                if (emailError != null) {
                    statusLabel.setText(emailError);
                    return null;
                }

                String passwordError = ValidationUtil.getPasswordErrorMessage(password);
                if (passwordError != null) {
                    statusLabel.setText(passwordError);
                    return null;
                }

                // Create new user (only regular users can be created through signup)
                return new Participant(name, email, password, "user");
            }
            return null;
        });

        // Show the dialog and handle the result
        java.util.Optional<Participant> result = signupDialog.showAndWait();
        result.ifPresent(participant -> {
            try {
                // Save the new participant to database
                try (Session session = DatabaseUtil.getSession()) {
                    Transaction transaction = session.beginTransaction();
                    session.save(participant);
                    transaction.commit();
                    statusLabel.setText("Account created successfully! Please login with your credentials.");
                    // Clear the login fields
                    emailField.clear();
                    passwordField.clear();
                }
            } catch (Exception e) {
                statusLabel.setText("Error creating account: " + e.getMessage());
            }
        });
    }
}
