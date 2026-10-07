-- Sample database data only.
-- Run database/museum_db.sql for the complete schema plus these records.
USE museum_db;

INSERT INTO users
(email,name,password,user_type,registration_date,is_active,phone,address,language,admin_id,department,permission_level)
VALUES
('alice@museum.com','Alice Johnson','password123','VISITOR','2026-10-01 10:00:00',TRUE,'9876543210','123 Main Street','English',NULL,NULL,NULL),
('bob@museum.com','Bob Smith','admin123','ADMIN','2026-10-01 10:05:00',TRUE,NULL,NULL,'English','ADM1001','Operations','ADMIN');

INSERT INTO exhibits (id,name,description,category,fee,location,is_open) VALUES
('EXH1001','Ancient India','Historical artifacts and sculptures from ancient India','History',150.00,'Hall 1',TRUE),
('EXH1002','Modern Art','Contemporary art and installations','Art',200.00,'Hall 2',TRUE),
('EXH1003','Natural History','Fossils, minerals, and natural wonders','Science',100.00,'Hall 3',TRUE);

INSERT INTO time_slots
(id,exhibit_id,slot_date,start_time,end_time,capacity,booked_tickets,status) VALUES
('TS1001','EXH1001','2026-10-05','09:00:00','10:00:00',30,5,'PARTIALLY_BOOKED'),
('TS1002','EXH1001','2026-10-05','10:00:00','11:00:00',30,30,'FULL'),
('TS1003','EXH1002','2026-10-05','10:00:00','11:00:00',25,0,'AVAILABLE'),
('TS1004','EXH1003','2026-10-05','11:00:00','12:00:00',40,0,'AVAILABLE');

INSERT INTO bookings
(id,visitor_email,exhibit_id,exhibit_name,visit_date,time_slot,ticket_quantity,
 price_per_ticket,total_amount,status,booking_date)
VALUES
('BK10001','alice@museum.com','EXH1001','Ancient India','2026-10-05',
 '09:00 AM - 10:00 AM',5,150.00,750.00,'CONFIRMED','2026-10-01 11:00:00');

INSERT INTO payments
(transaction_id,booking_id,amount,payment_method,status,payment_time,payer_email,description)
VALUES
('TXN20001','BK10001',750.00,'UPI','SUCCESS','2026-10-01 11:01:00',
 'alice@museum.com','Sample successful payment');

INSERT INTO receipts
(receipt_id,booking_id,transaction_id,visitor_email,exhibit_name,visit_date,time_slot,
 ticket_quantity,price_per_ticket,total_amount,payment_method,payment_status,booking_status)
VALUES
('REC30001','BK10001','TXN20001','alice@museum.com','Ancient India','05-10-2026',
 '09:00 AM - 10:00 AM',5,150.00,750.00,'UPI','SUCCESS','CONFIRMED');
