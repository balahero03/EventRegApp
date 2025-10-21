set echo on;

DROP TABLE REGISTRATIONS;
DROP TABLE EVENTS;
DROP TABLE PARTICIPANTS;
DROP SEQUENCE events_seq;
DROP SEQUENCE participants_seq;
DROP SEQUENCE registrations_seq;


CREATE TABLE PARTICIPANTS (
    participant_id NUMBER PRIMARY KEY,
    full_name VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL,
    role VARCHAR2(50) NOT NULL
);

-- Create the EVENTS table
CREATE TABLE EVENTS (
    event_id NUMBER PRIMARY KEY,
    event_name VARCHAR2(255) NOT NULL,
    event_date DATE NOT NULL,
    total_seats NUMBER NOT NULL
);

-- Create the REGISTRATIONS junction table
CREATE TABLE REGISTRATIONS (
    registration_id NUMBER PRIMARY KEY,
    event_id NUMBER,
    participant_id NUMBER,
    CONSTRAINT fk_event
        FOREIGN KEY (event_id)
        REFERENCES EVENTS(event_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_participant
        FOREIGN KEY (participant_id)
        REFERENCES PARTICIPANTS(participant_id)
        ON DELETE CASCADE
);

-- Create sequences for auto-incrementing primary keys
CREATE SEQUENCE participants_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE events_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE registrations_seq START WITH 1 INCREMENT BY 1;

-- Insert some sample data to get started
-- An ADMIN user (password: adminpass)
INSERT INTO PARTICIPANTS (participant_id, full_name, email, password, role)
VALUES (participants_seq.nextval, 'Administrator', 'admin@test.com', 'adminpass', 'ADMIN');

-- A regular USER (password: userpass)
INSERT INTO PARTICIPANTS (participant_id, full_name, email, password, role)
VALUES (participants_seq.nextval, 'John Doe', 'john.doe@test.com', 'userpass', 'USER');

-- Some sample events
INSERT INTO EVENTS (event_id, event_name, event_date, total_seats)
VALUES (events_seq.nextval, 'JavaFX Workshop', TO_DATE('2025-11-15', 'YYYY-MM-DD'), 50);

INSERT INTO EVENTS (event_id, event_name, event_date, total_seats)
VALUES (events_seq.nextval, 'Hibernate for Beginners', TO_DATE('2025-11-20', 'YYYY-MM-DD'), 30);

INSERT INTO EVENTS (event_id, event_name, event_date, total_seats)
VALUES (events_seq.nextval, 'Oracle SQL Masterclass', TO_DATE('2025-12-05', 'YYYY-MM-DD'), 1); -- An event with low capacity for testing

-- Commit the changes to the database
COMMIT;
