package com.musicplayer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column
    private String genre;

    @Column
    private int duration; // in seconds

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    // Constructors
    public Song() {}

    public Song(String title, String artist, String genre, int duration, Album album) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.album = album;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }

    @Override
    public String toString() {
        return title + " - " + artist;
    }
}
