# 🎓 EventRegApp - Event Registration Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-17.0.2-blue?style=for-the-badge&logo=java)
![Hibernate](https://img.shields.io/badge/Hibernate-5.6.15-59666C?style=for-the-badge&logo=hibernate)
![Oracle](https://img.shields.io/badge/Oracle-Database-F80000?style=for-the-badge&logo=oracle)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven)

**A Modern Desktop Application for Event Registration Management**

Developed for **INVENTE'25** - SSN College of Engineering

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Architecture](#-architecture) • [Documentation](#-documentation)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Database Setup](#-database-setup)
- [Running the Application](#-running-the-application)
- [Project Structure](#-project-structure)
- [Architecture](#-architecture)
- [JavaFX Components](#-javafx-components)
- [Hibernate ORM Details](#-hibernate-orm-details)
- [Maven Configuration](#-maven-configuration)
- [Database Schema](#-database-schema)
- [API Documentation](#-api-documentation)
- [Screenshots](#-screenshots)
- [Sample Data](#-sample-data)
- [Troubleshooting](#-troubleshooting)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🌟 Overview

**EventRegApp** is a comprehensive desktop application built with JavaFX and Hibernate ORM for managing event registrations in educational institutions. The application provides a robust platform for administrators to manage events and users, while allowing participants to browse and register for available events.

### Key Highlights

- ✅ **Role-Based Access Control** - Separate interfaces for Admin and User roles
- ✅ **Real-Time Seat Management** - Automatic seat count updates using database triggers
- ✅ **Modern UI/UX** - JavaFX-based responsive interface with gradient designs
- ✅ **Data Integrity** - Comprehensive validation and database constraints
- ✅ **ORM-Powered** - Hibernate for seamless database operations
- ✅ **Scalable Architecture** - MVC pattern with service layer

---

## 🚀 Features

### For Users (Participants)

| Feature | Description |
|---------|-------------|
| 🔐 **Secure Authentication** | Email/password login with validation |
| 📅 **Browse Events** | View all available upcoming events |
| ✅ **Event Registration** | Register for events with instant confirmation |
| ❌ **Unregister** | Cancel event registrations |
| 👤 **Personalized Dashboard** | See available seats and event details |
| 🔄 **Real-Time Updates** | Seat availability updates instantly |

### For Administrators

| Feature | Description |
|---------|-------------|
| 🎯 **Event Management** | Create, Read, Update, Delete events |
| 👥 **User Management** | Create and manage user accounts |
| 📊 **Registration Tracking** | View all event registrations |
| 🗑️ **Registration Control** | Remove specific registrations |
| 📈 **Comprehensive View** | See all events (past, present, future) |
| ⚙️ **Account Creation** | Create both admin and user accounts |

---

## 💻 Technology Stack

### Core Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Core programming language |
| **JavaFX** | 17.0.2 | Desktop GUI framework |
| **Hibernate** | 5.6.15.Final | Object-Relational Mapping (ORM) |
| **Oracle Database** | 19c XE | Relational database |
| **Maven** | 3.6+ | Build and dependency management |
| **JUnit** | 5.10.2 | Testing framework |

### JavaFX Modules Used

- `javafx-controls` - UI controls (Button, TextField, TableView, etc.)
- `javafx-fxml` - FXML markup language support
- `javafx-graphics` - Graphics and scene rendering

### Maven Plugins

- `maven-compiler-plugin` (3.13.0) - Java compilation
- `javafx-maven-plugin` (0.0.8) - JavaFX application runner
- `exec-maven-plugin` (3.1.0) - Command execution

---

## 📦 Prerequisites

Before running this application, ensure you have:

1. **Java Development Kit (JDK) 17 or higher**
   ```bash
   java -version  # Should show version 17+
   ```

2. **Apache Maven 3.6 or higher**
   ```bash
   mvn -version  # Should show version 3.6+
   ```

3. **Oracle Database XE (Express Edition) 19c or 21c**
   - Download from [Oracle Website](https://www.oracle.com/database/technologies/xe-downloads.html)
   - Default port: 1521
   - Database SID: xe

4. **IDE (Optional but Recommended)**
   - IntelliJ IDEA
   - Eclipse
   - NetBeans

---

## 🔧 Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/EventRegApp.git
cd EventRegApp
```

### Step 2: Configure Database Connection

Edit `src/main/resources/hibernate.cfg.xml`:

```xml
<property name="connection.url">jdbc:oracle:thin:@localhost:1521:xe</property>
<property name="connection.username">YOUR_USERNAME</property>
<property name="connection.password">YOUR_PASSWORD</property>
```

### Step 3: Install Dependencies

```bash
mvn clean install
```

---

## 🗄️ Database Setup

### Option 1: SQL Developer

1. Open Oracle SQL Developer
2. Connect to your database instance
3. Open `db/query.sql`
4. Execute the entire script

### Option 2: SQL*Plus Command Line

```bash
sqlplus system/your_password@localhost:1521/xe
@db/query.sql
exit
```

### What the Script Creates

- **3 Tables**: PARTICIPANTS, EVENTS, REGISTRATIONS
- **3 Sequences**: For auto-incrementing primary keys
- **4 Triggers**: For data integrity and automation
- **Sample Data**: 2 users (admin, regular) and 5 events

---

## 🏃 Running the Application

### Using Maven

```bash
# Compile the project
mvn clean compile

# Run the application
mvn javafx:run
```

### Using IDE

1. Import as Maven project
2. Run `HelloApplication.java` as Java Application
3. Or use IDE's JavaFX run configuration

### Default Login Credentials

**Admin Account:**
- Email: `admin@test.com`
- Password: `admin123`

**Regular User:**
- Email: `bala@test.com`
- Password: `bala123`

---

## 📁 Project Structure

```
EventRegApp/
│
├── 📂 src/main/
│   ├── 📂 java/org/example/eventregapp/
│   │   │
│   │   ├── 📂 model/                           # Entity Layer
│   │   │   ├── Participant.java                # User entity
│   │   │   ├── Event.java                      # Event entity
│   │   │   └── Registration.java               # Registration entity
│   │   │
│   │   ├── 📂 service/                         # Business Logic Layer
│   │   │   ├── AuthenticationService.java      # Login/auth operations
│   │   │   └── RegistrationService.java        # Registration operations
│   │   │
│   │   ├── 📂 util/                            # Utility Layer
│   │   │   ├── DatabaseUtil.java               # Hibernate session factory
│   │   │   ├── DataInitializer.java            # Initial data setup
│   │   │   ├── ValidationUtil.java             # Input validation
│   │   │   └── LoginTest.java                  # Connection testing
│   │   │
│   │   ├── 📂 controllers/                     # Controller Layer
│   │   │   ├── HelloApplication.java           # Main entry point
│   │   │   ├── LoginController.java            # Login/signup handler
│   │   │   ├── AdminController.java            # Admin panel logic
│   │   │   ├── UserController.java             # User panel logic
│   │   │   ├── HelloController.java            # Base controller
│   │   │   └── RegistrationController.java     # Registration view
│   │   │
│   │   └── module-info.java                    # Java module descriptor
│   │
│   └── 📂 resources/
│       ├── hibernate.cfg.xml                   # Hibernate configuration
│       │
│       └── 📂 org/example/eventregapp/         # FXML Views
│           ├── login-view.fxml                 # Login screen UI
│           ├── admin-view.fxml                 # Admin dashboard UI
│           ├── user-view.fxml                  # User dashboard UI
│           ├── registration-view.fxml          # Registration view UI
│           └── hello-view.fxml                 # Base view
│
├── 📂 db/
│   └── query.sql                               # Database schema & data
│
├── 📂 target/                                  # Compiled output (ignored)
│
├── pom.xml                                     # Maven configuration
├── .gitignore                                  # Git ignore rules
└── README.md                                   # This file
```

---

## 🏛️ Architecture

### MVC + Service Layer Pattern

```
┌─────────────────────────────────────────────────────────────┐
│                         VIEW LAYER                          │
│              (FXML Files + JavaFX Controllers)              │
│   login-view.fxml → LoginController.java                    │
│   admin-view.fxml → AdminController.java                    │
│   user-view.fxml  → UserController.java                     │
└────────────────────────────┬────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────┐
│                      CONTROLLER LAYER                       │
│                    (Event Handlers)                         │
│   - Handle user input                                       │
│   - Call service methods                                    │
│   - Update views                                            │
└────────────────────────────┬────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────┐
│                      SERVICE LAYER                          │
│                   (Business Logic)                          │
│   AuthenticationService: Login, role checking               │
│   RegistrationService: Registration operations              │
└────────────────────────────┬────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────┐
│                       MODEL LAYER                           │
│                    (Entity Classes)                         │
│   Participant.java (User/Admin)                             │
│   Event.java (Events)                                       │
│   Registration.java (Registrations)                         │
└────────────────────────────┬────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────┐
│                    PERSISTENCE LAYER                        │
│              (Hibernate ORM + DatabaseUtil)                 │
│   - Session management                                      │
│   - Transaction handling                                    │
│   - HQL query execution                                     │
└────────────────────────────┬────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────┐
│                      DATABASE LAYER                         │
│                  (Oracle Database XE)                       │
│   Tables: PARTICIPANTS, EVENTS, REGISTRATIONS               │
│   Triggers: Data integrity automation                       │
│   Sequences: Primary key generation                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎨 JavaFX Components

### Core JavaFX Features Used

#### 1. **Application Class** (`javafx.application.Application`)
- Entry point for JavaFX applications
- Manages application lifecycle
- Scene and stage initialization

```java
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            HelloApplication.class.getResource("login-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 700, 650);
        stage.setTitle("INVENTE'25 | SSN College of Engineering");
        stage.setScene(scene);
        stage.show();
    }
}
```

#### 2. **FXML Loader** (`javafx.fxml.FXMLLoader`)
- Loads UI from FXML markup files
- Separates UI design from logic
- Supports CSS-like styling

#### 3. **Scene Graph Components**

| Component | Purpose | Usage in Project |
|-----------|---------|------------------|
| `Scene` | Container for all UI elements | Main window container |
| `Stage` | Top-level window | Application window |
| `BorderPane` | Layout with 5 regions | Login screen layout |
| `VBox` | Vertical box layout | Form layouts |
| `HBox` | Horizontal box layout | Button groups |
| `TableView<T>` | Data table display | Event and user lists |
| `TableColumn<S,T>` | Table columns | Event properties |

#### 4. **UI Controls Used**

| Control | Description | Implementation |
|---------|-------------|----------------|
| `TextField` | Single-line text input | Email input |
| `PasswordField` | Masked password input | Password entry |
| `Button` | Clickable button | Login, Register, etc. |
| `Label` | Text display | Headers, messages |
| `DatePicker` | Date selection | Event date input |
| `TableView` | Data grid | Event/user listings |
| `ComboBox` | Dropdown selection | Role selection |

#### 5. **Event Handling**

```java
@FXML
private void handleLogin() {
    String email = emailField.getText();
    String password = passwordField.getText();
    
    Participant participant = AuthenticationService.authenticate(email, password);
    
    if (participant != null) {
        if (AuthenticationService.isAdmin(participant)) {
            redirectToAdminPanel(participant);
        } else {
            redirectToUserPanel(participant);
        }
    }
}
```

#### 6. **Property Binding** (`javafx.scene.control.cell.PropertyValueFactory`)

```java
eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
totalSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
```

#### 7. **Observable Collections** (`javafx.collections.ObservableList`)

```java
private ObservableList<Event> eventsList = FXCollections.observableArrayList();
eventsTable.setItems(eventsList);
```

#### 8. **CSS Styling in FXML**
- Inline styles for modern UI
- Gradient backgrounds
- Rounded corners and shadows
- Hover effects

---

## 🔄 Hibernate ORM Details

### Hibernate Configuration

#### Session Factory Setup (`hibernate.cfg.xml`)

```xml
<hibernate-configuration>
    <session-factory>
        <!-- Connection Settings -->
        <property name="connection.driver_class">
            oracle.jdbc.driver.OracleDriver
        </property>
        <property name="connection.url">
            jdbc:oracle:thin:@localhost:1521:xe
        </property>
        
        <!-- Hibernate Settings -->
        <property name="dialect">
            org.hibernate.dialect.Oracle12cDialect
        </property>
        <property name="show_sql">true</property>
        <property name="format_sql">true</property>
        <property name="hbm2ddl.auto">update</property>
        
        <!-- Connection Pooling (C3P0) -->
        <property name="hibernate.c3p0.min_size">5</property>
        <property name="hibernate.c3p0.max_size">20</property>
        
        <!-- Entity Mappings -->
        <mapping class="org.example.eventregapp.model.Participant"/>
        <mapping class="org.example.eventregapp.model.Event"/>
        <mapping class="org.example.eventregapp.model.Registration"/>
    </session-factory>
</hibernate-configuration>
```

### JPA Annotations Used

#### 1. **Entity Mapping**

```java
@Entity                                    // Marks class as entity
@Table(name = "PARTICIPANTS")              // Maps to database table
public class Participant {
    @Id                                    // Primary key
    @GeneratedValue(                       // Auto-generation strategy
        strategy = GenerationType.SEQUENCE,
        generator = "participants_seq"
    )
    @SequenceGenerator(                    // Sequence configuration
        name = "participants_seq",
        sequenceName = "participants_seq",
        allocationSize = 1
    )
    @Column(name = "participant_id")       // Column mapping
    private Long participantId;
}
```

#### 2. **Column Mapping**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Column` | Map field to column | `@Column(name = "full_name", nullable = false)` |
| `@Id` | Primary key | `@Id` on Long id field |
| `@GeneratedValue` | Auto-generation | `strategy = GenerationType.SEQUENCE` |
| `@SequenceGenerator` | Sequence config | Oracle sequence mapping |

#### 3. **Relationship Mapping**

**One-to-Many** (Participant → Registrations):
```java
@OneToMany(
    mappedBy = "participant",              // Owning side field
    cascade = CascadeType.ALL,             // Cascade operations
    fetch = FetchType.LAZY                 // Lazy loading
)
private Set<Registration> registrations = new HashSet<>();
```

**Many-to-One** (Registration → Participant):
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(
    name = "participant_id",               // Foreign key column
    nullable = false                       // NOT NULL constraint
)
private Participant participant;
```

#### 4. **Cascade Types Used**

| Cascade Type | Description |
|--------------|-------------|
| `CascadeType.ALL` | All operations cascade |
| `CascadeType.PERSIST` | Persist operations cascade |
| `CascadeType.MERGE` | Merge operations cascade |
| `CascadeType.REMOVE` | Delete operations cascade |

#### 5. **Fetch Strategies**

| Strategy | Description | Usage |
|----------|-------------|-------|
| `FetchType.LAZY` | Load on access | Large collections |
| `FetchType.EAGER` | Load immediately | Small collections |

### Hibernate Query Language (HQL)

#### Basic Queries

```java
// Simple query
session.createQuery("FROM Participant WHERE role = :role", Participant.class)
       .setParameter("role", "admin")
       .list();

// Join query
session.createQuery(
    "SELECT r FROM Registration r " +
    "JOIN FETCH r.participant p " +
    "JOIN FETCH r.event e " +
    "WHERE r.event = :event",
    Registration.class
).setParameter("event", event).list();
```

#### Named Parameters

```java
.setParameter("email", email)              // Prevents SQL injection
.setParameter("password", password)        // Type-safe binding
```

### Session Management

```java
public static Session getSession() {
    return sessionFactory.openSession();
}

// Try-with-resources for auto-close
try (Session session = DatabaseUtil.getSession()) {
    Transaction transaction = session.beginTransaction();
    // Operations here
    transaction.commit();
} catch (Exception e) {
    transaction.rollback();
}
```

### Transaction Management

```java
Transaction transaction = session.beginTransaction();
try {
    session.save(entity);                  // Insert
    session.update(entity);                // Update
    session.delete(entity);                // Delete
    transaction.commit();                  // Commit changes
} catch (Exception e) {
    transaction.rollback();                // Rollback on error
    throw e;
}
```

---

## 📦 Maven Configuration

### Complete `pom.xml` Structure

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Project Coordinates -->
    <groupId>org.example</groupId>
    <artifactId>EventRegApp</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>EventRegApp</name>
    
    <!-- Properties -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <junit.version>5.10.2</junit.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
    
    <!-- Dependencies -->
    <dependencies>
        <!-- JavaFX Dependencies -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>17.0.2</version>
        </dependency>
        
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>17.0.2</version>
        </dependency>
        
        <!-- Hibernate ORM -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.6.15.Final</version>
        </dependency>
        
        <!-- Oracle JDBC Driver -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc8</artifactId>
            <version>19.3.0.0</version>
        </dependency>
        
        <!-- JUnit Testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <!-- Build Configuration -->
    <build>
        <plugins>
            <!-- Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
            
            <!-- JavaFX Plugin -->
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>org.example.eventregapp.HelloApplication</mainClass>
                </configuration>
            </plugin>
            
            <!-- Exec Plugin -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
            </plugin>
        </plugins>
    </build>
</project>
```

### Maven Commands

| Command | Purpose |
|---------|---------|
| `mvn clean` | Delete target directory |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR file |
| `mvn install` | Install to local repository |
| `mvn javafx:run` | Run JavaFX application |
| `mvn dependency:tree` | Show dependency tree |

---

## 🗃️ Database Schema

### Entity Relationship Diagram

```
┌─────────────────────┐
│   PARTICIPANTS      │
├─────────────────────┤
│ PK participant_id   │
│    full_name        │
│    email (UNIQUE)   │
│    password         │
│    role             │
└──────────┬──────────┘
           │ 1
           │
           │ N
┌──────────▼──────────┐
│   REGISTRATIONS     │
├─────────────────────┤
│ PK registration_id  │
│ FK participant_id   │
│ FK event_id         │
│    registration_date│
└──────────┬──────────┘
           │ N
           │
           │ 1
┌──────────▼──────────┐
│      EVENTS         │
├─────────────────────┤
│ PK event_id         │
│    event_name       │
│    event_date       │
│    total_seats      │
│    registration_count│
└─────────────────────┘
```

### Table Constraints

#### PARTICIPANTS
- **Primary Key**: participant_id
- **Unique**: email
- **Check**: full_name length (2-50)
- **Check**: email format (regex)
- **Check**: password length (≥6)
- **Check**: role IN ('admin', 'user')

#### EVENTS
- **Primary Key**: event_id
- **Check**: event_name length (2-255)
- **Check**: total_seats > 0
- **Check**: registration_count ≥ 0
- **Trigger**: event_date must be future

#### REGISTRATIONS
- **Primary Key**: registration_id
- **Foreign Key**: participant_id → PARTICIPANTS
- **Foreign Key**: event_id → EVENTS
- **Unique**: (participant_id, event_id)
- **Cascade**: DELETE on both FKs

### Database Triggers

#### 1. Event Date Validation
```sql
CREATE OR REPLACE TRIGGER trg_check_event_date
BEFORE INSERT OR UPDATE ON EVENTS
FOR EACH ROW
BEGIN
    IF :NEW.event_date <= TRUNC(SYSDATE) THEN
        RAISE_APPLICATION_ERROR(-20001, 
            'Error: Event date must be in the future.');
    END IF;
END;
```

#### 2. Auto-Increment Registration Count
```sql
CREATE OR REPLACE TRIGGER trg_inc_reg_count
AFTER INSERT ON REGISTRATIONS
FOR EACH ROW
BEGIN
    UPDATE EVENTS 
    SET registration_count = registration_count + 1 
    WHERE event_id = :NEW.event_id;
END;
```

#### 3. Auto-Decrement Registration Count
```sql
CREATE OR REPLACE TRIGGER trg_dec_reg_count
AFTER DELETE ON REGISTRATIONS
FOR EACH ROW
BEGIN
    UPDATE EVENTS 
    SET registration_count = registration_count - 1 
    WHERE event_id = :OLD.event_id;
END;
```

#### 4. Event Capacity Check
```sql
CREATE OR REPLACE TRIGGER trg_check_event_capacity
BEFORE INSERT ON REGISTRATIONS
FOR EACH ROW
DECLARE
    v_total_seats NUMBER;
    v_registration_count NUMBER;
BEGIN
    SELECT total_seats, registration_count 
    INTO v_total_seats, v_registration_count
    FROM EVENTS 
    WHERE event_id = :NEW.event_id;
    
    IF v_registration_count >= v_total_seats THEN
        RAISE_APPLICATION_ERROR(-20002, 
            'Error: Event is full. Cannot register more participants.');
    END IF;
END;
```

---

## 📚 API Documentation

### Service Layer Methods

#### AuthenticationService

```java
/**
 * Authenticate user with email and password
 * @param email User email
 * @param password User password
 * @return Participant object if successful, null otherwise
 */
public static Participant authenticate(String email, String password)

/**
 * Check if participant has admin role
 * @param participant Participant to check
 * @return true if admin, false otherwise
 */
public static boolean isAdmin(Participant participant)

/**
 * Check if participant has user role
 * @param participant Participant to check
 * @return true if user, false otherwise
 */
public static boolean isUser(Participant participant)

/**
 * Get user role
 * @param participant Participant
 * @return Role string (admin/user)
 */
public static String getUserRole(Participant participant)
```

#### RegistrationService

```java
/**
 * Register participant for event with validation
 * @param participant Participant to register
 * @param event Event to register for
 * @return Success/error message
 */
public static String registerForEvent(Participant participant, Event event)

/**
 * Check if user is registered for event
 * @param participant Participant to check
 * @param event Event to check
 * @return true if registered, false otherwise
 */
public static boolean isUserRegisteredForEvent(Participant participant, Event event)

/**
 * Remove registration (unregister)
 * @param participant Participant
 * @param event Event
 * @return Success/error message
 */
public static String removeRegistration(Participant participant, Event event)

/**
 * Get all registrations for an event
 * @param event Event
 * @return List of registrations
 */
public static List<Registration> getEventRegistrations(Event event)

/**
 * Get all registrations for a participant
 * @param participant Participant
 * @return List of registrations
 */
public static List<Registration> getParticipantRegistrations(Participant participant)
```

### Validation Methods

```java
// Email validation
public static boolean isValidEmail(String email)

// Name validation (2-50 chars, letters/spaces/hyphens)
public static boolean isValidName(String name)

// Password validation (min 6 chars, letter + number)
public static boolean isValidPassword(String password)

// Future date validation
public static boolean isValidFutureDate(String dateStr)

// Positive number validation
public static boolean isValidPositiveNumber(String numberStr)
```

---

## 📸 Screenshots

### Login Screen
- Modern gradient background
- Email and password fields with validation
- Login and Sign Up buttons
- Error/success message display

### User Dashboard
- Welcome message with user name
- TableView of available events
- Register/Unregister buttons
- Real-time seat availability
- Logout option

### Admin Dashboard
- Event management table
- User management table
- Create/Update/Delete event forms
- User creation forms
- Registration viewing

---

## 🎲 Sample Data

### Default Accounts

| Email | Password | Role | Name |
|-------|----------|------|------|
| admin@test.com | admin123 | admin | Administrator |
| bala@test.com | bala123 | user | Bala |

### Sample Events

| Event Name | Date | Total Seats |
|------------|------|-------------|
| JavaFX Workshop | 2025-12-15 | 50 |
| Hibernate for Beginners | 2025-12-20 | 30 |
| Oracle SQL Masterclass | 2026-01-05 | 10 |
| Spring Boot Advanced | 2026-01-10 | 25 |
| Microservices Architecture | 2026-01-15 | 40 |

---

## 🔧 Troubleshooting

### Common Issues

#### 1. "Could not find or load main class"
```bash
# Solution: Clean and recompile
mvn clean compile
mvn javafx:run
```

#### 2. "ORA-12154: TNS:could not resolve the connect identifier"
```bash
# Solution: Check Oracle service is running
net start OracleServiceXE

# Verify connection string in hibernate.cfg.xml
jdbc:oracle:thin:@localhost:1521:xe
```

#### 3. "java.lang.ClassNotFoundException: oracle.jdbc.driver.OracleDriver"
```bash
# Solution: Reinstall dependencies
mvn clean install -U
```

#### 4. "Module org.example.eventregapp does not declare 'uses' for service"
```java
// Solution: Check module-info.java includes:
opens org.example.eventregapp to javafx.fxml;
opens org.example.eventregapp.model to org.hibernate.orm.core;
```

#### 5. Database Connection Failed
```bash
# Check Oracle listener status
lsnrctl status

# Test connection
sqlplus system/password@localhost:1521/xe
```

---

## 🚀 Future Enhancements

### Planned Features

- [ ] **Email Notifications** - Send confirmation emails for registrations
- [ ] **Password Encryption** - BCrypt/Argon2 password hashing
- [ ] **Export Functionality** - Export registration lists to PDF/Excel
- [ ] **Search & Filter** - Advanced event search and filtering
- [ ] **Event Categories** - Categorize events (Workshop, Seminar, etc.)
- [ ] **Waiting List** - Queue system for full events
- [ ] **Profile Pictures** - User avatar uploads
- [ ] **Event Images** - Add event banners/posters
- [ ] **Calendar View** - Calendar-based event browsing
- [ ] **Reports & Analytics** - Registration statistics and charts
- [ ] **Multi-language Support** - Internationalization (i18n)
- [ ] **Dark Mode** - Theme switching capability
- [ ] **Audit Logging** - Track all user actions
- [ ] **REST API** - Web service endpoints
- [ ] **Mobile App** - Android/iOS companion app

---

## 👥 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Coding Standards
- Follow Java naming conventions
- Add JavaDoc comments for public methods
- Write unit tests for new features
- Ensure code passes all existing tests

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Authors

- **Your Name** - *Initial work* - [@yourusername](https://github.com/yourusername)

---

## 🙏 Acknowledgments

- **SSN College of Engineering** - For INVENTE'25 event
- **Oracle Corporation** - Oracle Database
- **Hibernate Team** - Hibernate ORM framework
- **OpenJFX Community** - JavaFX framework
- **Apache Maven** - Build automation

---

## 📞 Support

For support, email your.email@example.com or create an issue in this repository.

---

## 🔗 Links

- [JavaFX Documentation](https://openjfx.io/)
- [Hibernate Documentation](https://hibernate.org/orm/documentation/)
- [Oracle Database Docs](https://docs.oracle.com/en/database/)
- [Maven Documentation](https://maven.apache.org/guides/)

---

<div align="center">

**⭐ Star this repo if you find it helpful!**

Made with ❤️ for INVENTE'25

</div>
