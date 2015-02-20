-- # New Life Songs DB
-- @author Jonathan Bernard <jdb@jdb-labs.com>
--
-- PostgreSQL database creation sript.

-- DROP DATABASE IF EXISTS nlsongs;
CREATE DATABASE IF NOT EXISTS nlsongs
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    CONNECTION LIMIT = 1;


-- Services table
DROP TABLE IF EXISTS services;
CREATE TABLE IF NOT EXISTS services (
    id SERIAL,
    date DATE NOT NULL,
    service_type VARCHAR(16) DEFAULT NULL,
    PRIMARY KEY (id));


-- Songs table
DROP TABLE IF EXISTS songs;
CREATE TABLE IF NOT EXISTS songs (
    id SERIAL,
    name VARCHAR(128) NOT NULL,
    artists VARCHAR(256) DEFAULT NULL,
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
    FOREIGN KEY (service_id) REFERENCES services (id),
    FOREIGN KEY (song_id) REFERENCES songs (id));


DROP TABLE IF EXISTS api_keys;
CREATE TABLE IF NOT EXISTS api_keys (
    key VARCHAR(32) NOT NULL,
    description VARCHAR(256) NOT NULL,
    PRIMARY KEY (key));


DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    username VARCHAR(64),
    pwd VARCHAR(80));
