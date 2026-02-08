USE PujankeAir;

INSERT INTO `Aircraft`
(manufacturer, model, serial_number, seat_rows, seat_columns)
VALUES
('Boeing', '747', 'BA25S6', 12, 4),
('Airbus',  'A320', 'AAA2567', 20, 6),
('Lockheed Martin', 'LM5654', 'AAAAAA', 10, 10),
('Antonov', 'AN225', 'BCDTSSZT', 5, 8);


INSERT INTO `Pilot`
(full_name, pin, flight_hours, `rank`)
VALUES
('Ante Matic', '11111111111', 5000, 'CAPTAIN'),
('Mate Sakic', '22222222222', 2000, 'CAPTAIN'),
('Jozo Simic', '33333333333', 6000, 'CAPTAIN');