package org.example.eventregapp.util;

import org.example.eventregapp.model.Participant;
import org.example.eventregapp.service.AuthenticationService;
import org.hibernate.Session;

import java.util.List;

/**
 * Test class to verify login functionality is connected to Participant table
 */
public class LoginTest {

    public static void testLoginConnection() {
        System.out.println("=== Testing Login Connection to Participant Table ===");

        try (Session session = DatabaseUtil.getSession()) {
            // Test 1: Check if Participant table exists and has data
            List<Participant> allParticipants = session.createQuery("FROM Participant", Participant.class).list();
            System.out.println("Total participants in database: " + allParticipants.size());

            for (Participant p : allParticipants) {
                System.out.println(
                        "Participant: " + p.getFullName() + " | Email: " + p.getEmail() + " | Role: " + p.getRole());
            }

            // Test 2: Test authentication service
            System.out.println("\n=== Testing Authentication Service ===");

            // Test admin authentication
            Participant admin = AuthenticationService.authenticate("admin@example.com", "admin123");
            if (admin != null) {
                System.out.println("✓ Admin authentication successful: " + admin.getFullName());
                System.out.println("✓ Admin role check: " + AuthenticationService.isAdmin(admin));
            } else {
                System.out.println("✗ Admin authentication failed");
            }

            // Test user authentication
            Participant user = AuthenticationService.authenticate("user@example.com", "user123");
            if (user != null) {
                System.out.println("✓ User authentication successful: " + user.getFullName());
                System.out.println("✓ User role check: " + AuthenticationService.isUser(user));
            } else {
                System.out.println("✗ User authentication failed");
            }

            // Test invalid credentials
            Participant invalid = AuthenticationService.authenticate("invalid@example.com", "wrongpassword");
            if (invalid == null) {
                System.out.println("✓ Invalid credentials properly rejected");
            } else {
                System.out.println("✗ Invalid credentials incorrectly accepted");
            }

            System.out.println("\n=== Login Connection Test Complete ===");

        } catch (Exception e) {
            System.err.println("Error testing login connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
