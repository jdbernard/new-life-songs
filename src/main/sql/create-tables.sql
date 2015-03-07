-- # New Life Songs DB
-- @author Jonathan Bernard <jdb@jdb-labs.com>
--
-- PostgreSQL database creation sript.

-- Services table
DROP TABLE IF EXISTS services;
CREATE TABLE IF NOT EXISTS services (
    id SERIAL,
    date DATE NOT NULL,
    service_type VARCHAR(16) DEFAULT NULL,
    CONSTRAINT uc_serviceTypeAndDate UNIQUE (date, service_type),
    PRIMARY KEY (id));


-- Songs table
DROP TABLE IF EXISTS songs;
CREATE TABLE IF NOT EXISTS songs (
    id SERIAL,
    name VARCHAR(128) NOT NULL,
    artists VARCHAR(256) DEFAULT NULL,
    CONSTRAINT uc_songNameAndArtist UNIQUE (name, artists),
    PRIMARY KEY (id));


-- performances table
DROP TABLE IF EXISTS performances;
CREATE TABLE IF NOT EXISTS performances (
    service_id INTEGER NOT NULL,
    song_id INTEGER NOT NULL,
    pianist VARCHAR(64) DEFAULT NULL,
    organist VARCHAR(64) DEFAULT NULL,
    bassist VARCHAR(64) DEFAULT NULL,
    drummer VARCHAR(64) DEFAULT NULL,
    guitarist VARCHAR(64) DEFAULT NULL,
    leader VARCHAR(64) DEFAULT NULL,
    PRIMARY KEY (service_id, song_id),
    FOREIGN KEY (service_id) REFERENCES services (id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES songs (id) ON DELETE CASCADE);


DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    username VARCHAR(64) UNIQUE NOT NULL,
    pwd VARCHAR(80),
    role VARCHAR(16) NOT NULL,
    PRIMARY KEY (id));

DROP TABLE IF EXISTS tokens;
CREATE TABLE IF NOT EXISTS tokens (
    token VARCHAR(64),
    user_id INTEGER NOT NULL,
    expires TIMESTAMP NOT NULL,
    PRIMARY KEY (token),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE);
