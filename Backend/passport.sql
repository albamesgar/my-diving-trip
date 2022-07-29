DROP SCHEMA IF EXISTS passport;
CREATE SCHEMA passport;
USE passport;

DROP TABLE IF EXISTS titulation;
DROP TABLE IF EXISTS passport;

CREATE TABLE passport(
	id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE titulation(
	id BIGINT NOT NULL AUTO_INCREMENT,
    organization VARCHAR(255),
    title_name VARCHAR(255),
    date_obtained DATE,
    instructor_name VARCHAR(255),
    club_id BIGINT,
    passport_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (passport_id) REFERENCES passport(id)
);

SELECT * FROM passport;
SELECT * FROM titulation;