-- Museum Ticketing System - MySQL database setup
CREATE DATABASE IF NOT EXISTS museum_db;
USE museum_db;

-- TABLE CREATION
CREATE TABLE IF NOT EXISTS users (
    email VARCHAR(255) PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) NOT NULL,
    registration_date DATETIME NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    phone VARCHAR(20),
    address VARCHAR(255),
    language VARCHAR(30),
    admin_id VARCHAR(50),
    department VARCHAR(100),
    permission_level VARCHAR(50),
    CONSTRAINT chk_user_type CHECK (user_type IN ('VISITOR', 'ADMIN'))
);

CREATE TABLE IF NOT EXISTS exhibits (
    id VARCHAR(30) PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    category VARCHAR(100) NOT NULL,
    fee DECIMAL(10,2) NOT NULL,
    location VARCHAR(100),
    is_open BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS time_slots (
    id VARCHAR(30) PRIMARY KEY,
    exhibit_id VARCHAR(30) NOT NULL,
    slot_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT NOT NULL,
    booked_tickets INT NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL,
    CONSTRAINT fk_time_slot_exhibit
        FOREIGN KEY (exhibit_id) REFERENCES exhibits(id),
    CONSTRAINT chk_slot_capacity CHECK (capacity > 0),
    CONSTRAINT chk_slot_booked CHECK (booked_tickets >= 0 AND booked_tickets <= capacity)
);

CREATE TABLE IF NOT EXISTS bookings (
    id VARCHAR(30) PRIMARY KEY,
    visitor_email VARCHAR(255) NOT NULL,
    exhibit_id VARCHAR(30) NOT NULL,
    exhibit_name VARCHAR(150) NOT NULL,
    visit_date DATE NOT NULL,
    time_slot VARCHAR(60) NOT NULL,
    ticket_quantity INT NOT NULL,
    price_per_ticket DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    booking_date DATETIME NOT NULL,
    CONSTRAINT fk_booking_visitor
        FOREIGN KEY (visitor_email) REFERENCES users(email),
    CONSTRAINT fk_booking_exhibit
        FOREIGN KEY (exhibit_id) REFERENCES exhibits(id),
    CONSTRAINT chk_booking_quantity CHECK (ticket_quantity > 0),
    CONSTRAINT chk_booking_status CHECK (status IN ('PENDING','CONFIRMED','CANCELLED','COMPLETED'))
);

CREATE TABLE IF NOT EXISTS payments (
    transaction_id VARCHAR(30) PRIMARY KEY,
    booking_id VARCHAR(30) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    payment_time DATETIME NOT NULL,
    payer_email VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_payment_booking
        FOREIGN KEY (booking_id) REFERENCES bookings(id),
    CONSTRAINT fk_payment_payer
        FOREIGN KEY (payer_email) REFERENCES users(email)
);

CREATE TABLE IF NOT EXISTS receipts (
    receipt_id VARCHAR(30) PRIMARY KEY,
    booking_id VARCHAR(30) NOT NULL,
    transaction_id VARCHAR(30) NOT NULL,
    visitor_email VARCHAR(255) NOT NULL,
    exhibit_name VARCHAR(150) NOT NULL,
    visit_date VARCHAR(30) NOT NULL,
    time_slot VARCHAR(60) NOT NULL,
    ticket_quantity INT NOT NULL,
    price_per_ticket DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    booking_status VARCHAR(30) NOT NULL,
    CONSTRAINT fk_receipt_booking FOREIGN KEY (booking_id) REFERENCES bookings(id),
    CONSTRAINT fk_receipt_payment FOREIGN KEY (transaction_id) REFERENCES payments(transaction_id),
    CONSTRAINT fk_receipt_visitor FOREIGN KEY (visitor_email) REFERENCES users(email)
);

-- SAMPLE DATA
INSERT INTO users
(email,name,password,user_type,registration_date,is_active,phone,address,language,admin_id,department,permission_level)
VALUES
('alice@museum.com','Alice Johnson','password123','VISITOR','2026-10-01 10:00:00',TRUE,
 '9876543210','123 Main Street','English',NULL,NULL,NULL),
('bob@museum.com','Bob Smith','admin123','ADMIN','2026-10-01 10:05:00',TRUE,
 NULL,NULL,'English','ADM1001','Operations','ADMIN')
ON DUPLICATE KEY UPDATE email=email;

INSERT INTO exhibits (id,name,description,category,fee,location,is_open) VALUES
('EXH1001','Ancient India','Historical artifacts and sculptures from ancient India','History',150.00,'Hall 1',TRUE),
('EXH1002','Modern Art','Contemporary art and installations','Art',200.00,'Hall 2',TRUE),
('EXH1003','Natural History','Fossils, minerals, and natural wonders','Science',100.00,'Hall 3',TRUE)
ON DUPLICATE KEY UPDATE id=id;

INSERT INTO time_slots
(id,exhibit_id,slot_date,start_time,end_time,capacity,booked_tickets,status) VALUES
('TS1001','EXH1001','2026-10-05','09:00:00','10:00:00',30,5,'PARTIALLY_BOOKED'),
('TS1002','EXH1001','2026-10-05','10:00:00','11:00:00',30,30,'FULL'),
('TS1003','EXH1002','2026-10-05','10:00:00','11:00:00',25,0,'AVAILABLE'),
('TS1004','EXH1003','2026-10-05','11:00:00','12:00:00',40,0,'AVAILABLE')
ON DUPLICATE KEY UPDATE id=id;

INSERT INTO bookings
(id,visitor_email,exhibit_id,exhibit_name,visit_date,time_slot,ticket_quantity,
 price_per_ticket,total_amount,status,booking_date) VALUES
('BK10001','alice@museum.com','EXH1001','Ancient India','2026-10-05',
 '09:00 AM - 10:00 AM',5,150.00,750.00,'CONFIRMED','2026-10-01 11:00:00')
ON DUPLICATE KEY UPDATE id=id;

INSERT INTO payments
(transaction_id,booking_id,amount,payment_method,status,payment_time,payer_email,description) VALUES
('TXN20001','BK10001',750.00,'UPI','SUCCESS','2026-10-01 11:01:00',
 'alice@museum.com','Sample successful payment')
ON DUPLICATE KEY UPDATE transaction_id=transaction_id;

INSERT INTO receipts
(receipt_id,booking_id,transaction_id,visitor_email,exhibit_name,visit_date,time_slot,
 ticket_quantity,price_per_ticket,total_amount,payment_method,payment_status,booking_status) VALUES
('REC30001','BK10001','TXN20001','alice@museum.com','Ancient India','05-10-2026',
 '09:00 AM - 10:00 AM',5,150.00,750.00,'UPI','SUCCESS','CONFIRMED')
ON DUPLICATE KEY UPDATE receipt_id=receipt_id;
