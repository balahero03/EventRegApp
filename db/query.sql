DROP TABLE REGISTRATIONS;
DROP TABLE EVENTS;
DROP TABLE PARTICIPANTS;

DROP SEQUENCE registrations_seq;
DROP SEQUENCE events_seq;
DROP SEQUENCE participants_seq;

CREATE TABLE PARTICIPANTS (
    participant_id NUMBER PRIMARY KEY,
    full_name VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL,
    role VARCHAR2(50) NOT NULL CHECK (role IN ('admin', 'user')),
    CONSTRAINT chk_full_name CHECK (LENGTH(full_name) >= 2 AND LENGTH(full_name) <= 50),
    CONSTRAINT chk_email_format CHECK (REGEXP_LIKE(email, '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$')),
    CONSTRAINT chk_password_length CHECK (LENGTH(password) >= 6)
);

CREATE TABLE EVENTS (
    event_id NUMBER PRIMARY KEY,
    event_name VARCHAR2(255) NOT NULL,
    event_date DATE NOT NULL,
    total_seats NUMBER NOT NULL,
    registration_count NUMBER DEFAULT 0,
    CONSTRAINT chk_event_name CHECK (LENGTH(event_name) >= 2 AND LENGTH(event_name) <= 255),
    CONSTRAINT chk_total_seats CHECK (total_seats > 0),
    CONSTRAINT chk_registration_count CHECK (registration_count >= 0)
);

CREATE TABLE REGISTRATIONS (
    registration_id NUMBER PRIMARY KEY,
    event_id NUMBER,
    participant_id NUMBER,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_event
        FOREIGN KEY (event_id)
        REFERENCES EVENTS(event_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_participant
        FOREIGN KEY (participant_id)
        REFERENCES PARTICIPANTS(participant_id)
        ON DELETE CASCADE,
    CONSTRAINT uk_participant_event UNIQUE (participant_id, event_id)
);

CREATE SEQUENCE participants_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE events_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE registrations_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_check_event_date
BEFORE INSERT OR UPDATE ON EVENTS
FOR EACH ROW
BEGIN
    IF :NEW.event_date <= TRUNC(SYSDATE) THEN
        RAISE_APPLICATION_ERROR(-20001, 'Error: Event date must be in the future.');
    END IF;
END;
/

-- Trigger to increment registration count when a new registration is added
CREATE OR REPLACE TRIGGER trg_inc_reg_count
AFTER INSERT ON REGISTRATIONS
FOR EACH ROW
BEGIN
    UPDATE EVENTS 
    SET registration_count = registration_count + 1 
    WHERE event_id = :NEW.event_id;
END;
/

-- Trigger to decrement registration  a registration is deleted
CREATE OR REPLACE TRIGGER trg_dec_reg_count
AFTER DELETE ON REGISTRATIONS
FOR EACH ROW
BEGIN
    UPDATE EVENTS 
    SET registration_count = registration_count - 1 
    WHERE event_id = :OLD.event_id;
END;
/

-- Trigger to prevent registration if event is full
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
        RAISE_APPLICATION_ERROR(-20002, 'Error: Event is full. Cannot register more participants.');
    END IF;
END;
/

INSERT INTO PARTICIPANTS (participant_id, full_name, email, password, role)
VALUES (participants_seq.nextval, 'Administrator', 'admin@test.com', 'admin123', 'admin');

INSERT INTO PARTICIPANTS (participant_id, full_name, email, password, role)
VALUES (participants_seq.nextval, 'Bala', 'bala@test.com', 'bala123', 'user');

INSERT INTO EVENTS (event_id, event_name, event_date, total_seats)
VALUES (events_seq.nextval, 'JavaFX Workshop', TO_DATE('2025-12-15', 'YYYY-MM-DD'), 50);

INSERT INTO EVENTS (event_id, event_name, event_date, total_seats)
VALUES (events_seq.nextval, 'Hibernate for Beginners', TO_DATE('2025-12-20', 'YYYY-MM-DD'), 30);

INSERT INTO EVENTS (event_id, event_name, event_date, total_seats)
VALUES (events_seq.nextval, 'Oracle SQL Masterclass', TO_DATE('2026-01-05', 'YYYY-MM-DD'), 10);

INSERT INTO EVENTS (event_id, event_name, event_date, total_seats)
VALUES (events_seq.nextval, 'Spring Boot Advanced', TO_DATE('2026-01-10', 'YYYY-MM-DD'), 25);

INSERT INTO EVENTS (event_id, event_name, event_date, total_seats)
VALUES (events_seq.nextval, 'Microservices Architecture', TO_DATE('2026-01-15', 'YYYY-MM-DD'), 40);

INSERT INTO REGISTRATIONS (registration_id, event_id, participant_id)
VALUES (registrations_seq.nextval, 1, 2);

INSERT INTO REGISTRATIONS (registration_id, event_id, participant_id)
VALUES (registrations_seq.nextval, 2, 2);

COMMIT;