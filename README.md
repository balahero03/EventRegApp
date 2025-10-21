# Event Registration System

A JavaFX desktop application for managing event registrations with Oracle database integration using Hibernate ORM.

## Features

- **User Authentication**: Login system with email/password
- **Event Management**: View available events with seat availability
- **Registration System**: Register/unregister for events
- **User Dashboard**: View personal registrations
- **Database Integration**: Oracle database with Hibernate ORM

## Prerequisites

- Java 17 or higher
- Oracle Database (XE version recommended)
- Maven 3.6+

## Database Setup

1. Create an Oracle database instance
2. Run the SQL script in `db/query.sql` to create tables and sample data
3. Update database connection details in `src/main/resources/hibernate.cfg.xml`

## Sample Data

The application comes with sample data:
- **Admin User**: admin@test.com / adminpass
- **Regular User**: john.doe@test.com / userpass
- **Sample Events**: JavaFX Workshop, Hibernate for Beginners, Oracle SQL Masterclass

## Running the Application

```bash
# Compile the project
mvn clean compile

# Run the application
mvn javafx:run
```

## Project Structure

```
src/main/java/org/example/eventregapp/
├── model/                 # Entity classes
│   ├── Participant.java
│   ├── Event.java
│   └── Registration.java
├── service/              # Business logic services
│   ├── ParticipantService.java
│   ├── EventService.java
│   └── RegistrationService.java
├── HelloApplication.java # Main application class
└── HelloController.java  # UI controller
```

## Database Schema

- **PARTICIPANTS**: User information (id, name, email, password, role)
- **EVENTS**: Event details (id, name, date, total_seats)
- **REGISTRATIONS**: Many-to-many relationship between participants and events

## Technologies Used

- JavaFX 17.0.2
- Hibernate 5.6.15.Final
- Oracle JDBC Driver 19.3.0.0
- Maven
- Java 17
