DROP SCHEMA IF EXISTS diving_log;
CREATE SCHEMA diving_log;
USE diving_log;

DROP TABLE IF EXISTS dive;
DROP TABLE IF EXISTS dive_book;

CREATE TABLE dive_book(
	id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE dive(
	id BIGINT NOT NULL AUTO_INCREMENT,
    date DATE,
    location VARCHAR(255),
    max_depth DOUBLE,
    minutes_in INT,
    air_entering INT,
    air_outgoing INT,
    temperature DOUBLE,
    visibility DOUBLE,
    bottle_capacity VARCHAR(255),
    air_type VARCHAR(255),
    oxygen_proportion INT,
    partner_name VARCHAR(255),
    partner_titulation VARCHAR(255),
    rating INT,
    club_id BIGINT,
    observations VARCHAR(255),
    picture VARCHAR(255),
    dive_book_id BIGINT,
    club_validation BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (dive_book_id) REFERENCES dive_book(id)
);

SELECT * FROM dive_book;
SELECT * FROM dive;