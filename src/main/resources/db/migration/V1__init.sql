-- V1
-- Created initial DB schema, it may still be subject to changes in the future but it represents a stable starting point.

USE PujankeAir;

CREATE TABLE IF NOT EXISTS `User` (
	`user_id` INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(20) UNIQUE NOT NULL,
	password_hash VARCHAR(60) NOT NULL,
	admin_privileges BOOL DEFAULT FALSE NOT NULL,
	enabled BOOL DEFAULT FALSE,
	registration_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	funds DECIMAL(10,2) NOT NULL DEFAULT 0.0
);

CREATE TABLE IF NOT EXISTS Aircraft (
	aircraft_id INT AUTO_INCREMENT PRIMARY KEY,
	manufacturer VARCHAR(20) DEFAULT 'undefined',
	model VARCHAR(10) DEFAULT 'undefined',
	serial_number VARCHAR(10) UNIQUE NOT NULL,
	seat_rows INT NOT NULL,
	seat_columns INT NOT NULL,
	
	CONSTRAINT chk_pos_counts
	CHECK(seat_rows > 0 AND seat_columns > 0)
);

CREATE TABLE IF NOT EXISTS Service (
	service_id INT AUTO_INCREMENT PRIMARY KEY,
	service_name VARCHAR(30) NOT NULL,
	service_fee DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS Pilot (
	pilot_id INT AUTO_INCREMENT PRIMARY KEY,
	full_name VARCHAR(40) NOT NULL,
	pin VARCHAR(11) UNIQUE NOT NULL,
	flight_hours INT DEFAULT 0,
	`rank` ENUM('CAPTAIN', 'FIRST_OFFICER', 'SECOND_OFFICER'),
	date_employed DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Airport (
	airport_id INT AUTO_INCREMENT PRIMARY KEY,
	airport_name VARCHAR(40) NOT NULL,	
	icao_code CHAR(4) UNIQUE NOT NULL,
	country VARCHAR(20) NOT NULL,
	city VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Notification (
	notification_id INT AUTO_INCREMENT PRIMARY KEY,
	`user_id` INT NOT NULL,
	message VARCHAR(256),
	was_seen BOOL DEFAULT FALSE,
	context VARCHAR(20) NOT NULL,
	generation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	CONSTRAINT fk_notification_user 
	FOREIGN KEY(`user_id`) REFERENCES `User`(`user_id`)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `Order` (
	order_id INT AUTO_INCREMENT PRIMARY KEY,
	order_uuid BINARY(16) DEFAULT (UUID()),
	`user_id` INT,
	total_price DECIMAL(10,2) DEFAULT 0.0,
	order_finalized BOOL DEFAULT FALSE,
	generation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	finalization_timestamp TIMESTAMP,

	CONSTRAINT fk_order_user
	FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`)
	ON DELETE SET NULL
	ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Flight (
	flight_id INT AUTO_INCREMENT PRIMARY KEY,
	aircraft_id INT,
	estimated_duration_min INT,
	takeoff_date DATE NOT NULL,
	takeoff_time TIME,
	takeoff_airport_id INT,
	destination_airport_id INT,
	pilot_id INT,
	base_fare DECIMAL(10,2),

	CONSTRAINT fk_flight_aircraft
	FOREIGN KEY (aircraft_id) REFERENCES Aircraft(aircraft_id)
	ON DELETE SET NULL
	ON UPDATE CASCADE,

	CONSTRAINT fk_flight_pilot
	FOREIGN KEY (pilot_id) REFERENCES Pilot(pilot_id)
	ON DELETE SET NULL
	ON UPDATE CASCADE,

	CONSTRAINT fk_flight_airport_tkoff
	FOREIGN KEY (takeoff_airport_id) REFERENCES Airport(airport_id)
	ON DELETE SET NULL
	ON UPDATE CASCADE,

	CONSTRAINT fk_flight_airport_dest
	FOREIGN KEY (destination_airport_id) REFERENCES Airport(airport_id)
	ON DELETE SET NULL
	ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Ticket (
	ticket_id INT AUTO_INCREMENT PRIMARY KEY,
	flight_id INT NOT NULL,
	seat_number VARCHAR(5) NOT NULL,
	ticket_holder_fullname VARCHAR(40) NOT NULL,
	ticket_holder_pin VARCHAR(11) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	reservation_complete BOOL DEFAULT FALSE,
	generation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	order_id INT NOT NULL,

	CONSTRAINT fk_ticket_flight
	FOREIGN KEY (flight_id) REFERENCES Flight(flight_id)
	ON DELETE RESTRICT
	ON UPDATE CASCADE,

	CONSTRAINT fk_ticket_order
	FOREIGN KEY (order_id) REFERENCES `Order`(order_id)
	ON DELETE RESTRICT
	ON UPDATE CASCADE,

	CONSTRAINT seat_pick_unique
	UNIQUE(flight_id, seat_number)
);

CREATE TABLE IF NOT EXISTS Amenities (
	ticket_id INT NOT NULL,
	service_id INT NOT NULL,
	quantity INT DEFAULT 1 NOT NULL,

	CONSTRAINT fk_amenities_ticket
	FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,

	CONSTRAINT fk_amenities_service
	FOREIGN KEY (service_id) REFERENCES Service(service_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,

	CONSTRAINT pk_amenities
	PRIMARY KEY(ticket_id, service_id)
);