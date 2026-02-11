-- Order as it was, didn't serve a purpose, so I replaced it with Receipt, which is generated one per ticket purchase
-- and supposed to be immutable, therefore, each ticket purchase must be finalized individually.
-- Additionally, separated seat_number into seat_row and seat_column for easier integration with JPA.

USE PujankeAir;

ALTER TABLE Ticket
CHANGE COLUMN seat_number seat_row INT,
ADD COLUMN seat_column CHAR(1) CHECK (REGEXP_LIKE(seat_column, '[A-Z]')),
DROP INDEX seat_pick_unique,
DROP FOREIGN KEY fk_ticket_order,
ADD CONSTRAINT seat_pick_unique UNIQUE(flight_id, seat_row, seat_column),
ADD COLUMN `user_id` INT,
ADD CONSTRAINT fk_ticket_user
FOREIGN KEY (`user_id`)
REFERENCES `User`(`user_id`);

RENAME TABLE `Order` TO Receipt;
ALTER TABLE Receipt
RENAME COLUMN order_id TO receipt_id,
RENAME COLUMN order_uuid TO receipt_uuid,
DROP COLUMN `user_id`,
DROP COLUMN finalization_timestamp,
DROP COLUMN order_finalized,
DROP FOREIGN KEY fk_order_user,
ADD COLUMN ticket_id INT NOT NULL UNIQUE,
ADD CONSTRAINT fk_receipt_ticket
FOREIGN KEY (ticket_id)
REFERENCES Ticket(ticket_id);

