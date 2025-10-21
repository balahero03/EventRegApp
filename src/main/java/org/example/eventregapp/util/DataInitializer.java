package org.example.eventregapp.util;

import org.example.eventregapp.model.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DataInitializer {

    public static void initializeDefaultData() {
        try (Session session = DatabaseUtil.getSession()) {
            // Check if admin user already exists
            Long adminCount = session.createQuery("SELECT COUNT(*) FROM Participant WHERE role = 'admin'", Long.class)
                    .uniqueResult();

            if (adminCount == 0) {
                // Create default admin user
                Participant admin = new Participant("Admin User", "admin@example.com", "admin123", "admin");

                Transaction transaction = session.beginTransaction();
                session.save(admin);
                transaction.commit();

                System.out.println("Default admin user created: admin@example.com / admin123");
            }

            // Check if regular user exists
            Long userCount = session.createQuery("SELECT COUNT(*) FROM Participant WHERE role = 'user'", Long.class)
                    .uniqueResult();

            if (userCount == 0) {
                // Create default regular user
                Participant user = new Participant("Regular User", "user@example.com", "user123", "user");

                Transaction transaction = session.beginTransaction();
                session.save(user);
                transaction.commit();

                System.out.println("Default user created: user@example.com / user123");
            }

        } catch (Exception e) {
            System.err.println("Error initializing default data: " + e.getMessage());
        }
    }
}
