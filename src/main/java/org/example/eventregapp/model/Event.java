package org.example.eventregapp.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_seq")
    @SequenceGenerator(name = "events_seq", sequenceName = "events_seq", allocationSize = 1)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @Column(name = "registration_count")
    private Integer registrationCount = 0;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Registration> registrations = new HashSet<>();

    // Default constructor
    public Event() {
    }

    // Constructor with parameters
    public Event(String eventName, LocalDate eventDate, Integer totalSeats) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.totalSeats = totalSeats;
    }

    // Getters and Setters
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }

    public Integer getRegistrationCount() {
        return registrationCount;
    }

    public void setRegistrationCount(Integer registrationCount) {
        this.registrationCount = registrationCount;
    }

    // Helper method to get available seats
    public int getAvailableSeats() {
        return totalSeats - registrationCount;
    }

    // Helper method to check if event is full
    public boolean isFull() {
        return registrationCount >= totalSeats;
    }

    // Helper method to check if event is available for registration
    public boolean isAvailable() {
        return !isFull() && eventDate.isAfter(java.time.LocalDate.now());
    }

    // Helper method to increment registration count
    public void incrementRegistrationCount() {
        this.registrationCount++;
    }

    // Helper method to decrement registration count
    public void decrementRegistrationCount() {
        if (this.registrationCount > 0) {
            this.registrationCount--;
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
