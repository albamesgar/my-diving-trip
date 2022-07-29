DROP SCHEMA IF EXISTS clubs;
CREATE SCHEMA clubs;
USE clubs;

DROP TABLE IF EXISTS club;

CREATE TABLE club(
	id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    street VARCHAR(255),
    home_number INT,
    city VARCHAR(255),
    postal_code INT,
    country VARCHAR(255),
    contact_phone BIGINT,
    email VARCHAR(255),
    rating DOUBLE,
    PRIMARY KEY (id)
);

INSERT INTO club (name, street, home_number, city, postal_code, country, contact_phone,
email, rating) VALUES 
("Cabo Tiñoso", "street1", 1, "city1", 3030, "Spain", 606060, "club1@gmail.com", 4.5),
("BlauMar", "street2", 1, "city2", 3030, "Spain", 606060, "club2@gmail.com",3),
("Hesperides", "street3", 1, "city3", 3030, "Spain", 606060, "club3@gmail.com",2.2);