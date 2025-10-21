package org.example.eventregapp.service;

import org.example.eventregapp.model.Event;
import org.example.eventregapp.model.Participant;
import org.example.eventregapp.model.Registration;
import org.example.eventregapp.util.DatabaseUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RegistrationService {

    /**
     * Register a participant for an event with validation
     */
    public static String registerForEvent(Participant participant, Event event) {
        try (Session session = DatabaseUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                // Check if user is already registered for this event
                if (isUserRegisteredForEvent(participant, event)) {
                    transaction.rollback();
                    return "You are already registered for this event";
                }

                // Check if event is available (not full and future date)
                if (!event.isAvailable()) {
                    if (event.isFull()) {
                        transaction.rollback();
                        return "Event is full. No more registrations allowed";
                    }
                    if (event.getEventDate().isBefore(java.time.LocalDate.now())) {
                        transaction.rollback();
                        return "Cannot register for past events";
                    }
                }

                // Create new registration
                Registration registration = new Registration(event, participant);
                session.save(registration);

                // Update event registration count
                event.incrementRegistrationCount();
                session.update(event);

                transaction.commit();
                return "Registration successful!";

            } catch (Exception e) {
                transaction.rollback();
                return "Registration failed: " + e.getMessage();
            }
        } catch (Exception e) {
            return "Database error: " + e.getMessage();
        }
    }

    /**
     * Check if user is already registered for an event
     */
    public static boolean isUserRegisteredForEvent(Participant participant, Event event) {
        try (Session session = DatabaseUtil.getSession()) {
            String query = "FROM Registration r WHERE r.participant = :participant AND r.event = :event";
            List<Registration> registrations = session.createQuery(query, Registration.class)
                    .setParameter("participant", participant)
                    .setParameter("event", event)
                    .list();

            return !registrations.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Remove a registration (unregister from event)
     */
    public static String removeRegistration(Participant participant, Event event) {
        try (Session session = DatabaseUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                String query = "FROM Registration r WHERE r.participant = :participant AND r.event = :event";
                List<Registration> registrations = session.createQuery(query, Registration.class)
                        .setParameter("participant", participant)
                        .setParameter("event", event)
                        .list();

                if (registrations.isEmpty()) {
                    transaction.rollback();
                    return "No registration found for this event";
                }

                // Delete all registrations (should be only one due to unique constraint)
                for (Registration registration : registrations) {
                    session.delete(registration);
                }

                // Update event registration count
                event.decrementRegistrationCount();
                session.update(event);

                transaction.commit();
                return "Successfully unregistered from event";

            } catch (Exception e) {
                transaction.rollback();
                return "Failed to unregister: " + e.getMessage();
            }
        } catch (Exception e) {
            return "Database error: " + e.getMessage();
        }
    }

    /**
     * Get all registrations for a specific event
     */
    public static List<Registration> getEventRegistrations(Event event) {
        try (Session session = DatabaseUtil.getSession()) {
            String query = "FROM Registration r WHERE r.event = :event ORDER BY r.registrationDate DESC";
            return session.createQuery(query, Registration.class)
                    .setParameter("event", event)
                    .list();
        } catch (Exception e) {
            return List.of();
        }
    }

    /**
     * Get all registrations for a specific participant
     */
    public static List<Registration> getParticipantRegistrations(Participant participant) {
        try (Session session = DatabaseUtil.getSession()) {
            String query = "FROM Registration r WHERE r.participant = :participant ORDER BY r.registrationDate DESC";
            return session.createQuery(query, Registration.class)
                    .setParameter("participant", participant)
                    .list();
        } catch (Exception e) {
            return List.of();
        }
    }

    /**
     * Remove a specific registration by ID (admin function)
     */
    public static String removeRegistrationById(Long registrationId) {
        try (Session session = DatabaseUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                Registration registration = session.get(Registration.class, registrationId);
                if (registration == null) {
                    transaction.rollback();
                    return "Registration not found";
                }

                // Get the event before deleting registration
                Event event = registration.getEvent();

                session.delete(registration);

                // Update event registration count
                event.decrementRegistrationCount();
                session.update(event);

                transaction.commit();
                return "Registration removed successfully";

            } catch (Exception e) {
                transaction.rollback();
                return "Failed to remove registration: " + e.getMessage();
            }
        } catch (Exception e) {
            return "Database error: " + e.getMessage();
        }
    }
}
