USE PujankeAir;

CREATE TABLE City (
    zipcode INT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(20) NOT NULL,
    country_name VARCHAR(20) NOT NULL
);

ALTER TABLE Airport
DROP COLUMN city,
DROP COLUMN country,
ADD COLUMN city_zipcode INT NOT NULL,
ADD CONSTRAINT fk_airport_city FOREIGN KEY(city_zipcode)
REFERENCES City(zipcode);


INSERT INTO City
(city_name, country_name)
VALUES
('Split', 'Croatia'),
('Zagreb', 'Croatia'),
('Berlin', 'Germany'),
('Madrid','Spain')