-- ============================================
-- Music Player Database Schema
-- Run this in MySQL Workbench or CLI
-- ============================================

CREATE DATABASE IF NOT EXISTS musicplayer_db;
USE musicplayer_db;

-- Albums Table
CREATE TABLE IF NOT EXISTS albums (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    artist VARCHAR(200),
    release_year INT
);

-- Songs Table
CREATE TABLE IF NOT EXISTS songs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    artist VARCHAR(200) NOT NULL,
    genre VARCHAR(100),
    duration INT DEFAULT 0,
    album_id BIGINT,
    FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE SET NULL
);

-- Playlist Table
CREATE TABLE IF NOT EXISTS playlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

-- Playlist Songs (Many-to-Many)
CREATE TABLE IF NOT EXISTS playlist_songs (
    playlist_id BIGINT,
    song_id BIGINT,
    PRIMARY KEY (playlist_id, song_id),
    FOREIGN KEY (playlist_id) REFERENCES playlist(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE
);

-- ============================================
-- Sample Data
-- ============================================

INSERT INTO albums (name, artist, release_year) VALUES
('Divide', 'Ed Sheeran', 2017),
('25', 'Adele', 2015),
('aur', 'Arijit Singh', 2023);

INSERT INTO songs (title, artist, genre, duration, album_id) VALUES
('Shape of You', 'Ed Sheeran', 'Pop', 234, 1),
('Perfect', 'Ed Sheeran', 'Pop', 263, 1),
('Hello', 'Adele', 'Soul', 295, 2),
('Someone Like You', 'Adele', 'Soul', 285, 2),
('Tum Hi Ho', 'Arijit Singh', 'Bollywood', 261, 3),
('Kesariya', 'Arijit Singh', 'Bollywood', 270, 3);

INSERT INTO playlist (name) VALUES ('My Playlist');
