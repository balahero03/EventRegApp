package org.example.eventregapp.service;

import org.example.eventregapp.model.Participant;
import org.example.eventregapp.util.DatabaseUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * Service class for handling authentication operations
 * Connected to the Participant table for user authentication
 */
public class AuthenticationService {

    /**
     * Authenticate a user by email and password
     * 
     * @param email    User's email
     * @param password User's password
     * @return Participant object if authentication successful, null otherwise
     */
    public static Participant authenticate(String email, String password) {
        try (Session session = DatabaseUtil.getSession()) {
            List<Participant> participants = session.createQuery(
                    "FROM Participant WHERE email = :email AND password = :password",
                    Participant.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .list();

            if (participants.isEmpty()) {
                return null;
            }

            return participants.get(0);
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Check if a user has admin role
     * 
     * @param participant The participant to check
     * @return true if user is admin, false otherwise
     */
    public static boolean isAdmin(Participant participant) {
        return participant != null && "admin".equalsIgnoreCase(participant.getRole());
    }

    /**
     * Check if a user has user role
     * 
     * @param participant The participant to check
     * @return true if user is regular user, false otherwise
     */
    public static boolean isUser(Participant participant) {
        return participant != null && "user".equalsIgnoreCase(participant.getRole());
    }

    /**
     * Get user role from Participant table
     * 
     * @param participant The participant
     * @return User role (admin/user)
     */
    public static String getUserRole(Participant participant) {
        return participant != null ? participant.getRole() : null;
    }
}
