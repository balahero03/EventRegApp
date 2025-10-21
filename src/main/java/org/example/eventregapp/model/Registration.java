package org.example.eventregapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REGISTRATIONS")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registrations_seq")
    @SequenceGenerator(name = "registrations_seq", sequenceName = "registrations_seq", allocationSize = 1)
    @Column(name = "registration_id")
    private Long registrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    // Default constructor
    public Registration() {
        this.registrationDate = LocalDateTime.now();
    }

    // Constructor with parameters
    public Registration(Event event, Participant participant) {
        this.event = event;
        this.participant = participant;
        this.registrationDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", event=" + (event != null ? event.getEventName() : "null") +
                ", participant=" + (participant != null ? participant.getFullName() : "null") +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
